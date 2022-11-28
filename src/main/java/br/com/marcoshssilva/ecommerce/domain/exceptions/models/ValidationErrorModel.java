package br.com.marcoshssilva.ecommerce.domain.exceptions.models;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorModel extends StandardErrorModel {
    
        private final List<FieldErrorModel> errors = new ArrayList<>();
    
        public ValidationErrorModel(Integer status, String error, String message, Long timestamp, String path) {
                super(status, error, message, timestamp, path);
        }

        public List<FieldErrorModel> getErrors() {
                return errors;
        }

        public void addError(FieldErrorModel e) {
                this.errors.add(e);
        }
        
}
