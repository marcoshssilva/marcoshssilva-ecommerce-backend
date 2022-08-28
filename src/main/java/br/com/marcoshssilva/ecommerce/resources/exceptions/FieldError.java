package br.com.marcoshssilva.ecommerce.resources.exceptions;

import java.io.Serializable;

public class FieldError implements Serializable {
    
    private String field;
    private String message;
    private Object capturedData;

    public FieldError(String field, String message, Object capturedData) {
        this.field = field;
        this.message = message;
        this.capturedData = capturedData;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getCapturedData() {
        return capturedData;
    }

    public void setCapturedData(Object capturedData) {
        this.capturedData = capturedData;
    }
    
}
