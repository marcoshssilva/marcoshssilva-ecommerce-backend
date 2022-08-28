package br.com.marcoshssilva.ecommerce.config;

import br.com.marcoshssilva.ecommerce.services.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.marcoshssilva.ecommerce.services.SMTPEmaiSenderService;

@Configuration
@Profile("dev")
public class DevConfig {

    @Bean
    public EmailService emailService() {
        return new SMTPEmaiSenderService();
    }
    
}
