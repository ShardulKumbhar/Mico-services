package com.bridgelabz.user.util;
import lombok.Data;

@Data
public class Response {

    private Integer StatusCode;
    private String Statusmessage;
    private Object data;
    public Response(Integer statusCode, String statusmessage, Object data) {
        super();
        StatusCode = statusCode;
        Statusmessage = statusmessage;
        this.data = data;
    }

    public Response(Integer statusCode, String statusmessage) {
        super();
        StatusCode = statusCode;
        Statusmessage = statusmessage;
    }

    public Response(String statusmessage, Object data) {
        super();
        Statusmessage = statusmessage;
        this.data = data;
    }


}