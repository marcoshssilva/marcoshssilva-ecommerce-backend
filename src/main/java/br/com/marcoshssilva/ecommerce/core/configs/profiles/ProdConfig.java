package br.com.marcoshssilva.ecommerce.core.configs.profiles;

import br.com.marcoshssilva.ecommerce.domain.services.email.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.marcoshssilva.ecommerce.domain.services.email.SMTPEmaiSenderServiceImpl;

@Configuration
@Profile(value = "prod")
public class ProdConfig {

    @Bean
    public EmailService emailService() {
        return new SMTPEmaiSenderServiceImpl();
    }
    
}
