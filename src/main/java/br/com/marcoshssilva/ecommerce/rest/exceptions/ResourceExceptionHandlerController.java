package br.com.marcoshssilva.ecommerce.rest.exceptions;

import br.com.marcoshssilva.ecommerce.domain.services.exceptions.AuthorizationException;
import br.com.marcoshssilva.ecommerce.domain.services.exceptions.DataIntegrityException;
import br.com.marcoshssilva.ecommerce.domain.services.exceptions.FileException;
import br.com.marcoshssilva.ecommerce.domain.services.exceptions.ObjectNotFoundException;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandlerController {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardError(HttpStatus.NOT_FOUND.value(), "404 - Objeto Não Encontrado", e.getMessage(), System.currentTimeMillis(), r.getRequestURI()));
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest r) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new StandardError(HttpStatus.BAD_GATEWAY.value(), "501 - Infringido Integridade dos Dados", e.getMessage(), System.currentTimeMillis(), r.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest r) {
        ValidationError err = new ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "422 - Erro de Validação dos Dados", "", System.currentTimeMillis(), r.getRequestURI());
        e.getFieldErrors().forEach(
                x -> err.addError(new FieldError(x.getField(), x.getDefaultMessage(), x.getRejectedValue()))
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> authorizationExceptionHandler(AuthorizationException err, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new StandardError(HttpStatus.FORBIDDEN.value(), "403 - Erro de Autorização", err.getMessage(), System.currentTimeMillis(), req.getServletPath()));
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<StandardError> fileException(FileException e, HttpServletRequest req) {
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), "400 - Erro de Arquivo", e.getMessage(), System.currentTimeMillis(), req.getServletPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandardError> amazonServiceException(AmazonServiceException e, HttpServletRequest req) {
        HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
        StandardError error = new StandardError(code.value(), "400 - Amazon Service", e.getMessage(), System.currentTimeMillis(), req.getServletPath());
        return ResponseEntity.status(code).body(error);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandardError> amazonClientException(AmazonClientException e, HttpServletRequest req) {
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), "400 - Amazon Client", e.getMessage(), System.currentTimeMillis(), req.getServletPath() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandardError> amazonClientException(AmazonS3Exception e, HttpServletRequest req) {
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), "400 - Amazon S3", e.getMessage(), System.currentTimeMillis(), req.getServletPath());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error); 
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<StandardError> usernameNotFoundException(UsernameNotFoundException e, HttpServletRequest req) {
        StandardError error = new StandardError(HttpStatus.UNAUTHORIZED.value(), "401 - Email e/ou Senha inválidos", e.getMessage(), System.currentTimeMillis(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
