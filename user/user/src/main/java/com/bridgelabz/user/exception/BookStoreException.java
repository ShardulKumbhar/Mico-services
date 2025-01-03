package com.bridgelabz.user.exception;

import java.util.Locale;

import com.bridgelabz.user.util.ErrorResponse;
import com.bridgelabz.user.util.Response;
import org.springframework.web.bind.annotation.ResponseStatus;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@ResponseStatus
@Data
@Slf4j
public class BookStoreException extends RuntimeException {


    public BookStoreException(int statusCode, String statusmessage) {
        super(statusmessage);
        StatusCode = statusCode;
        Statusmessage = statusmessage;
    }
    private static final long serialVersionUID = 1L;


    private int StatusCode;
    private String Statusmessage;

    public Response getErrorResponse() {
        log.error("Error msg:" + Statusmessage);
        return getErrorResponse(Locale.getDefault());
    }

    public Response getErrorResponse(Locale locale) {
        log.error("Error msg status:" + getStatusmessage());
        ErrorResponse err = new ErrorResponse(StatusCode, Statusmessage);
        err.setStatusCode(getStatusCode());
        err.setStatusmessage(getStatusmessage());
        return err;

    }
}
