package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private Environment env;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with trello account");
        functionality.add("Application allows sending tasks to trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("application_functionality", functionality);
        context.setVariable("goodbye_message", "Feel free to search on Google in case of any problems, bye.");
        context.setVariable("company_name", env.getProperty("info.company.name"));
        context.setVariable("company_email", env.getProperty("info.company.email"));
        context.setVariable("company_phone", env.getProperty("info.company.phone"));
        return templateEngine.process("created-trello-card-mail", context);
    }

}
