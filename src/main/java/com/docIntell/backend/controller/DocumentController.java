package com.docIntell.backend.controller;

import com.docIntell.backend.dto.ApiResponse;
import com.docIntell.backend.dto.DocumentResponseDto;
import com.docIntell.backend.service.DocumentService;
import com.docIntell.backend.service.intefaces.DocumentServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentServiceInterface documentService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<>> uploadDocument(@RequestParam("file") MultipartFile file) {

        DocumentResponseDto documentResponseDto = this.documentService.uploadDocument(file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<DocumentResponseDto>builder()
                        .success(true)
                        .message("Document uploaded successfully.")
                        .data(documentResponseDto)
                        .timestamp(java.time.LocalDateTime.now())
                        .build());
    }

}
