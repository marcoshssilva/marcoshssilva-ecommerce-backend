package br.com.marcoshssilva.ecommerce.services.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.marcoshssilva.ecommerce.domain.Cliente;
import br.com.marcoshssilva.ecommerce.dto.ClienteNewDTO;
import br.com.marcoshssilva.ecommerce.repositories.ClienteRepository;
import br.com.marcoshssilva.ecommerce.resources.exceptions.FieldError;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>, Serializable {

    @Autowired
    ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldError> list = new ArrayList<>();

        Cliente aux = repo.findByEmail(objDto.getEmail());

        if (aux != null) {
            list.add(new FieldError("email", "Este email já possui uma conta associada a ele", objDto.getEmail()));
        }

        list.forEach((FieldError e) -> {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getField()).addConstraintViolation();
        });

        return list.isEmpty();
    }
}
