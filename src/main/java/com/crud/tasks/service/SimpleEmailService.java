package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(Mail mail) {
        LOGGER.info("Starting: sending new email.");
        try {
            SimpleMailMessage simpleMailMessage = createMailMessage(mail);
            javaMailSender.send(simpleMailMessage);
            LOGGER.info("Mail sent successfully.");
        } catch (MailException e) {
            LOGGER.error("Failed to process sending new email: ", e.getMessage(), e);
        }
    }

    private SimpleMailMessage createMailMessage(Mail mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail.getMailTo());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getMessage());
        if (mail.getToCc() != null && !mail.getToCc().equals("")) {
            simpleMailMessage.setCc(mail.getToCc());
        }
        return simpleMailMessage;
    }

}
