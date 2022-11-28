package br.com.marcoshssilva.ecommerce.core.configs;

import br.com.marcoshssilva.ecommerce.domain.services.EmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.marcoshssilva.ecommerce.domain.services.SMTPEmaiSenderService;

@Configuration
@Profile(value = "prod")
public class ProdConfig {

    @Bean
    public EmailService emailService() {
        return new SMTPEmaiSenderService();
    }
    
}
