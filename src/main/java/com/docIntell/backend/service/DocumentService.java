package com.docIntell.backend.service;

import com.docIntell.backend.DocumentStatus;
import com.docIntell.backend.dto.DocumentResponseDto;
import com.docIntell.backend.entity.DocumentMetaData;
import com.docIntell.backend.repository.DocumentMetaDataRespository;
import com.docIntell.backend.service.intefaces.DocumentServiceInterface;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService implements DocumentServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentMetaDataRespository documentMetaDataRespository ;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public DocumentResponseDto uploadDocument(MultipartFile file) {

        String FileName=file.getName()!=null ? file.getOriginalFilename() : "Document";

        String ContentType = file.getContentType() != null ? file.getContentType() : "application/octet-stream";

        DocumentMetaData documentMetaData = DocumentMetaData
                .builder()
                .fileName(FileName)
                .contentType(ContentType)
                .status(DocumentStatus.UPLOADING)
                .fileSize(file.getSize())
                .build();

        DocumentMetaData saved=documentMetaDataRespository.save(documentMetaData);

        //parse the document
        List<Document> parsedDocuments = parserService.parse(file);
        //chunks created
        int chunksCreated = ingestionService.ingest(documentMetaData, parsedDocuments);

        return DocumentResponseDto.builder()
                .id(saved.getId())
                .fileName(saved.getFileName())
                .fileSize(saved.getFileSize())
                .status(saved.getStatus())
                .chunksCreated(chunksCreated)
                .message("Document uploaded successfully.")
                .build();

        return null;
    }
}
