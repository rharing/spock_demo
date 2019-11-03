package com.roha.example.spock.demo.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

import javax.annotation.PostConstruct
import javax.mail.internet.MimeMessage

@Service
class CommunicationService {
    @Value("\${mail.from:admin\\@localhost}")
    String from
    @Value("\${mail.password:password}")
    String password
    @Value("\${mail.host:}")
    String host
    @Value("\${mail.port:25}")
    int port
    JavaMailSender mailSender
    public void sendInvite(String to, String subject, String body){
        mailSender.send(new SimpleMailMessage(from:from,to:to,subject:subject,text: body))
    }
    @PostConstruct
    public void afterPropertiesSet(){
         if (StringUtils.isEmpty(host)){
mailSender = new JavaMailSender(){
    @Override
    MimeMessage createMimeMessage() {
        return null
    }

    @Override
    MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        return null
    }

    @Override
    void send(MimeMessage mimeMessage) throws MailException {

    }

    @Override
    void send(MimeMessage... mimeMessages) throws MailException {

    }

    @Override
    void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {

    }

    @Override
    void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {

    }

    @Override
    void send(SimpleMailMessage simpleMessage) throws MailException {

    }

    @Override
    void send(SimpleMailMessage... simpleMessages) throws MailException {

    }
}
         }
        else {
             mailSender = new JavaMailSenderImpl();
             mailSender.setHost(host);
             mailSender.setPort(port);

             mailSender.setUsername(from);
             mailSender.setPassword(password);

             Properties props = mailSender.getJavaMailProperties();
             props.put("mail.transport.protocol", "smtp");
             props.put("mail.smtp.auth", "true");
             props.put("mail.smtp.starttls.enable", "true");
             props.put("mail.debug", "true");
         }
    }
}
