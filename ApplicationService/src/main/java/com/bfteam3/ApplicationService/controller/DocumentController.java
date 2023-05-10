package com.bfteam3.ApplicationService.controller;
import com.bfteam3.ApplicationService.domain.response.ResponseStatus;
import com.bfteam3.ApplicationService.service.ApplicationWorkflowService;
import com.bfteam3.ApplicationService.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/docu")
public class DocumentController {
    private final FileService fileService;
    private final ApplicationWorkflowService applicationService;

    @Autowired
    public DocumentController(FileService fileService, ApplicationWorkflowService applicationService) {
        this.fileService = fileService;
        this.applicationService = applicationService;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = fileService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @PostMapping("/upload")
    public ResponseStatus uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return ResponseStatus.builder()
                .success(true)
                .message("Upload successfully\n" + fileService.uploadFile(file))
                .build();
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseStatus deleteFile(@PathVariable String fileName) {
        fileService.deleteFile(fileName);
        return ResponseStatus.builder()
                .success(true)
                .message("Delete successfully")
                .build();
    }

    @PostMapping("/update/{fileName}")
    public ResponseStatus updateFile(@PathVariable String fileName,
                           @RequestParam(value = "file") MultipartFile file) {
        fileService.deleteFile(fileName);
        return ResponseStatus.builder()
                .success(true)
                .message(fileService.uploadFile(file))
                .build();
    }

}
