package org.hzz.controller;

import org.hzz.entity.RespResult;
import org.hzz.service.uploadingfiles.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FileUploadControllerApi {

    @Autowired
    private StorageService storageService;

    @GetMapping("/all")
    public RespResult getAll(){
        List<String> uris = storageService.loadAll().map(path -> MvcUriComponentsBuilder.fromMethodName(
                FileUploadControllerApi.class,
                "serveFile",
                path.getFileName().toString()
        ).build().toUri().toString()).collect(Collectors.toList());

        return RespResult.<List<String>>success(uris);
    }

    @GetMapping("/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        System.out.println("获取图片 "+filename);
        Resource resource = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE,
                "image/jpeg").body(resource);
    }

    @PostMapping("/upload")
    public RespResult<String> upload(@RequestParam("file")MultipartFile file){
        storageService.store(file);
        String uri = MvcUriComponentsBuilder.fromMethodName(
                FileUploadControllerApi.class,
                "serveFile",
                file.getOriginalFilename()
        ).build().toUri().toString();
        return RespResult.success(uri);
    }
}
