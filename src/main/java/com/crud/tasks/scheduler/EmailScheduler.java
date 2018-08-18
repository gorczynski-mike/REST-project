package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class EmailScheduler {

    private static final String MAIL_SUBJECT = "Tasks: once a day email";

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String mailMessage = MessageFormat.format("Currently in the database you got: {0,number,integer} task{0, choice, 0#s|1#|1<s}",size);
        simpleEmailService.sendSchedulerMail(new Mail(
                adminConfig.getAdminMail(),
                MAIL_SUBJECT,
                mailMessage,
                null
        ));
    }
}
