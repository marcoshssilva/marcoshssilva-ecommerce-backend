package br.com.marcoshssilva.ecommerce.core.configs;

import java.text.ParseException;

import br.com.marcoshssilva.ecommerce.domain.services.DBService;
import br.com.marcoshssilva.ecommerce.domain.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.marcoshssilva.ecommerce.domain.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public Boolean instantiateDatabase() throws ParseException {
        dbService.instanciateDatabase();
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }
}
