package com.bridgelabz.book.exception;

import com.bridgelabz.book.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BookStoreExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e){
        BookStoreException he = new BookStoreException(100, e.getMessage());
        return new ResponseEntity<>(he.getErrorResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
