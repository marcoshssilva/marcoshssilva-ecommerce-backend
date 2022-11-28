package br.com.marcoshssilva.ecommerce.domain.services.auth;

import br.com.marcoshssilva.ecommerce.domain.entities.Cliente;
import br.com.marcoshssilva.ecommerce.domain.repositories.ClienteRepository;
import br.com.marcoshssilva.ecommerce.domain.services.email.EmailService;
import br.com.marcoshssilva.ecommerce.domain.exceptions.throwables.ObjectNotFoundException;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private EmailService emailService;
    
    private final Random random = new Random();
    
    public void sendNewPassword(String email) {
        Cliente c = clienteRepository.findByEmail(email);
        if (c == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }
        
        String newPass = newPassword();
        c.setSenha(encoder.encode(newPass));
        
        clienteRepository.save(c);
        emailService.sendNewPasswordEmail(c, newPass);
    }

    private String newPassword() {
        char[] ver = new char[20];
        for (int i = 0; i < ver.length; i++) {
            ver[i] = randomChar();
        }
        return new String(ver);
    }
    
    private char randomChar() {
        int opt = random.nextInt(3);
        switch (opt) {
            case 0:
                // Gera um dígito numerico
                return (char) (random.nextInt(10) + 48);
            case 1:
                // Gera letra minuscula
                return (char) (random.nextInt(26) + 65);
            default:
                // Gera letra maiuscula
                return (char) (random.nextInt(26) + 97);
        }
    }
}
