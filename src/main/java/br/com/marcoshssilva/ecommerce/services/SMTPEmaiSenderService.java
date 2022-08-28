package br.com.marcoshssilva.ecommerce.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SMTPEmaiSenderService extends AbstractMailMessage {

    @Autowired
    private MailSender mailSender;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    private static final Logger LOG = LoggerFactory.getLogger(SMTPEmaiSenderService.class);
    
    @Override
    public void sendEmail(SimpleMailMessage smm) {
        mailSender.send(smm);
        LOG.info("----------- Detalhes da Mensagem enviada ----------");
        LOG.info(smm.toString());
        LOG.info("----------- Email enviado com sucesso! ----------");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        javaMailSender.send(msg);
        LOG.info("----------- Detalhes da Mensagem enviada ----------");
        LOG.info(msg.toString());
        LOG.info("----------- Email enviado com sucesso! ----------");
    }
    
}
