package br.com.marcoshssilva.ecommerce.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.com.marcoshssilva.ecommerce.services.AuthService;
import br.com.marcoshssilva.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcoshssilva.ecommerce.dto.EmailDTO;
import br.com.marcoshssilva.ecommerce.security.JWTUtil;
import br.com.marcoshssilva.ecommerce.security.UserSS;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {
    
    @Autowired
    private JWTUtil jwtUtil;
    
    @Autowired
    private AuthService authService;
    
    @RequestMapping(value= "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse res) {
        
        UserSS auth = UserService.authenticated();
        String token = jwtUtil.generateToken(auth.getUsername());
        
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
