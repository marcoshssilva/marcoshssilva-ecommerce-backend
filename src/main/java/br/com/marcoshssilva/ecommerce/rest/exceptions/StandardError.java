package br.com.marcoshssilva.ecommerce.rest.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable {

        private Integer status;
        private String error;
        private String message;
        private Long timestamp;
        private String path;


        public StandardError(Integer status, String error, String message, Long timestamp, String path) {
                this.status = status;
                this.error = error;
                this.message = message;
                this.timestamp = timestamp;
                this.path = path;
        }

        public Integer getStatus() {
                return status;
        }

        public void setStatus(Integer status) {
                this.status = status;
        }

        public String getError() {
                return error;
        }

        public void setError(String error) {
                this.error = error;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }

        public Long getTimestamp() {
                return timestamp;
        }

        public void setTimestamp(Long timestamp) {
                this.timestamp = timestamp;
        }

        public String getPath() {
                return path;
        }

        public void setPath(String path) {
                this.path = path;
        }
    
}
