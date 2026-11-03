package com.docIntell.backend.service.intefaces;

import com.docIntell.backend.dto.DocumentResponseDto;
import com.docIntell.backend.repository.DocumentMetaDataRespository;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentServiceInterface {

    DocumentResponseDto uploadDocument(MultipartFile file);
}
