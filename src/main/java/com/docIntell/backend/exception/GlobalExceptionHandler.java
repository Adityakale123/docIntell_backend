package com.docIntell.backend.exception;

import com.docIntell.backend.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        LOGGER.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .data(null)
                                .timestamp(java.time.LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler(DocumentProcessException.class)
    public ResponseEntity<ApiResponse<?>> handleProcessingError(DocumentProcessException ex){
      LOGGER.error("Document processing error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(ApiResponse.builder()
                        .success(false)
                        .message(ex.getMessage())
                        .timestamp(java.time.LocalDateTime.now())
                        .build());

    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<?>> handleMaxSizeException(MaxUploadSizeExceededException ex){
        LOGGER.error("Max upload size exceeded: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(ApiResponse.builder()
                        .success(false)
                        .message("File size exceeds the maximum limit.(25MB)")
                        .timestamp(java.time.LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String,String>>> handleValidation(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.put(error.getField(),error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.<Map<String,String>>builder()
                        .success(false)
                        .message("Validation failed")
                        .data(errors)
                        .timestamp(java.time.LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        LOGGER.error("Illegal argument: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.builder()
                                .success(false)
                                .message(ex.getMessage())
                                .data(null)
                                .timestamp(java.time.LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        LOGGER.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.<Void>builder()
                        .success(false)
                        .message("An unexpected error occurred. Please try again later.")
                        .timestamp(java.time.LocalDateTime.now())
                        .build());
    }

}
