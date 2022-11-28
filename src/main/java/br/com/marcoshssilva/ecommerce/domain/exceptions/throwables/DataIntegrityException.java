package br.com.marcoshssilva.ecommerce.domain.exceptions.throwables;

public class DataIntegrityException extends RuntimeException {

        public DataIntegrityException(String message) {
            super(message);
        }

        public DataIntegrityException(String message, Throwable cause) {
            super(message, cause);
        }
    
}
