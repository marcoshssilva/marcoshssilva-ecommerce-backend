package br.com.marcoshssilva.ecommerce.rest.controllers;

import jakarta.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.com.marcoshssilva.ecommerce.domain.services.auth.AuthService;
import br.com.marcoshssilva.ecommerce.domain.services.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcoshssilva.ecommerce.rest.dto.EmailDTO;
import br.com.marcoshssilva.ecommerce.rest.utils.JwtUtils;
import br.com.marcoshssilva.ecommerce.core.configs.security.UserDetailsImpl;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private AuthService authService;
    
    @RequestMapping(value= "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse res) {
        
        UserDetailsImpl auth = UserService.authenticated();
        String token = jwtUtils.generateToken(auth.getUsername());
        
        res.addHeader("Authorization", "Bearer " + token);
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
        
        return ResponseEntity.noContent().build();
    }
    
    @RequestMapping(value= "/forgot", method = RequestMethod.POST)
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDto) {
        authService.sendNewPassword(emailDto.getEmail());
        return ResponseEntity.noContent().build();
    }
}
