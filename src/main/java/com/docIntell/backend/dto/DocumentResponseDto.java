package com.docIntell.backend.dto;

import com.docIntell.backend.DocumentStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentResponseDto {

    private UUID id;
    private String fileName;
    private Long fileSize;
    private DocumentStatus status;
    private Integer chunksCreated;
    private String message;
}
