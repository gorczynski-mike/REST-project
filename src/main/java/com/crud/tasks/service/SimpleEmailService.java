package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void sendTrelloMail(Mail mail) {
        LOGGER.info("Starting: sending new email.");
        try {
            javaMailSender.send(createMimeMessageTrello(mail));
            LOGGER.info("Mail sent successfully.");
        } catch (MailException e) {
            LOGGER.error("Failed to process sending new email: ", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessageTrello(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    public void sendSchedulerMail(Mail mail) {
        LOGGER.info("Starting: sending new email.");
        try {
            javaMailSender.send(createMimeMessageScheduler(mail));
            LOGGER.info("Mail sent successfully.");
        } catch (MailException e) {
            LOGGER.error("Failed to process sending new email: ", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessageScheduler(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildSchedulerEmail(mail.getMessage()), true);
        };
    }

}
