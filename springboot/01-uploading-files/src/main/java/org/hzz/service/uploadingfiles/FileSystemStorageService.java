package org.hzz.service.uploadingfiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService{

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties storageProperties){
        this.rootLocation = Paths.get(storageProperties.getLocation());
        System.out.println(rootLocation.toAbsolutePath());
    }



    @Override
    public void init() {
        try {
            Files.createDirectory(this.rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage",e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        try{
            if(file.isEmpty()){
                throw new StorageException("Failed to store empty file");
            }

            Path destinationFile = rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();

            if(!destinationFile.getParent().equals(rootLocation.toAbsolutePath())){
                // this is a security check
                throw new StorageException("Cannot store file outside current directory");
            }

            try(InputStream in = file.getInputStream())
            {
                Files.copy(in,destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (IOException e){
            throw new StorageException("Failed to store file",e);
        }

    }

    @Override
    public Stream<Path> loadAll() {
        try{
           return Files.walk(this.rootLocation,1).filter(path -> !path.equals(rootLocation))
                    .map(rootLocation::relativize);
        }catch (IOException e){
            throw new StorageException("Failed to read file");
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {

        try {
            Path path = load(filename);
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists() && resource.isReadable()){
                return resource;
            }else{
                throw new StorageFileNotFoundException("Could not read file "+ filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file "+filename,e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
