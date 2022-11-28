package br.com.marcoshssilva.ecommerce.core.configs.profiles;

import java.text.ParseException;

import br.com.marcoshssilva.ecommerce.domain.services.etc.DBService;
import br.com.marcoshssilva.ecommerce.domain.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.marcoshssilva.ecommerce.domain.services.email.MockEmailServiceImpl;

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
        return new MockEmailServiceImpl();
    }
}
