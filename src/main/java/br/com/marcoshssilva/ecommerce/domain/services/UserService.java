package br.com.marcoshssilva.ecommerce.domain.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.marcoshssilva.ecommerce.core.security.UserDetailsImpl;

@Service
public class UserService {
    public static UserDetailsImpl authenticated() {
        try {
            return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch(Exception e ) {
            return null;
        }
            
    }
}
