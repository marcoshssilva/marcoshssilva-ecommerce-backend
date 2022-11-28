package br.com.marcoshssilva.ecommerce.rest.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
    
        private final List<FieldError> errors = new ArrayList<>();
    
        public ValidationError(Integer status, String error, String message, Long timestamp, String path) {
                super(status, error, message, timestamp, path);
        }

        public List<FieldError> getErrors() {
                return errors;
        }

        public void addError(FieldError e) {
                this.errors.add(e);
        }
        
}
