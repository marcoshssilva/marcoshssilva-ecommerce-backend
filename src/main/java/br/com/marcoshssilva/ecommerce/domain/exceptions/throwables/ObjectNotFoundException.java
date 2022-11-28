package br.com.marcoshssilva.ecommerce.domain.exceptions.throwables;

public class ObjectNotFoundException extends RuntimeException {

        public ObjectNotFoundException(String message) {
            super(message);
        }

        public ObjectNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    
}
