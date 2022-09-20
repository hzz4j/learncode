package org.hzz.controller;

import org.hzz.service.uploadingfiles.StorageFileNotFoundException;
import org.hzz.service.uploadingfiles.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

@Controller
public class FileUploadController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/")
    public String listUploadFiles(Model model){
        model.addAttribute("files",storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile",path.getFileName().toString()).build().toUri().toString()
                ).collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE,"image/jpeg")
                .body(file);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"")
//                .body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file")MultipartFile file,
                                   RedirectAttributes attributes){

        storageService.store(file);
        attributes.addFlashAttribute("messages","You successfully uploaded "
            + file.getOriginalFilename());
        System.out.println(file.getOriginalFilename());
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
