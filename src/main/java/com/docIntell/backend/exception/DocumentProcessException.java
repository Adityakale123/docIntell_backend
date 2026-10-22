package com.docIntell.backend.exception;

public class DocumentProcessException extends RuntimeException {

    public DocumentProcessException(String message) {
        super(message);
    }

    public DocumentProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public  DocumentProcessException() {
        super("Error in processing the document.");
    }
}
