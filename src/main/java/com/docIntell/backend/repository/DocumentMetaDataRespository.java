package com.docIntell.backend.repository;

import com.docIntell.backend.entity.DocumentMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentMetaDataRespository extends JpaRepository<DocumentMetaData, UUID> {
}
