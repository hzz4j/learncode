package org.hzz;

import org.hzz.service.uploadingfiles.StorageProperties;
import org.hzz.service.uploadingfiles.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import sun.tools.jar.CommandLine;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class UploadingFilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadingFilesApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService){
        return args -> {
            //storageService.deleteAll();
            //storageService.init();
        };
    }

}
