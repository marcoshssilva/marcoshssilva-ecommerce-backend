package br.com.marcoshssilva.ecommerce.domain.exceptions.throwables;

public class FileException extends RuntimeException {
    
    public FileException(String msg) {
        super(msg);
    }
    
    public FileException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}
