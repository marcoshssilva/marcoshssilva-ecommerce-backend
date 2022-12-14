package br.com.marcoshssilva.ecommerce.domain.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.marcoshssilva.ecommerce.domain.entities.Cliente;
import br.com.marcoshssilva.ecommerce.rest.dto.ClienteDTO;
import br.com.marcoshssilva.ecommerce.domain.repositories.ClienteRepository;
import br.com.marcoshssilva.ecommerce.domain.exceptions.models.FieldErrorModel;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
    
    @Autowired
    HttpServletRequest re;
    
    @Autowired
    ClienteRepository repo;
    
    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValid(ClienteDTO t, ConstraintValidatorContext cvc) {
        Map<String, String> map = (Map<String, String>) re.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));
        Cliente aux = repo.findByEmail(t.getEmail());
        
        List<FieldErrorModel> list = new ArrayList<>();
        
        if (!aux.equals(repo.findById(uriId))) {
            list.add(new FieldErrorModel("email", "Email já está em uso.", t.getEmail()));
        }
        
        list.forEach((FieldErrorModel e) -> {
            cvc.disableDefaultConstraintViolation();
            cvc.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getField()).addConstraintViolation();
        });
        
        return list.isEmpty();
    }
    
}
