package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private CompanyConfig companyConfig;

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8080/v1/trello/boards/1");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name",adminConfig.getAdminName());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("goodbye_message",adminConfig.getAppName());
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        context.setVariable("company_details",companyConfig.getName() + ", email: " + companyConfig.getEmail()+ ", tel: "+companyConfig.getPhone());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildAmountOfTasksMail(String message){
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("goodbye_message",adminConfig.getAppName());
        context.setVariable("company_details",companyConfig.getName() + ", email: " + companyConfig.getEmail()+ ", tel: "+companyConfig.getPhone());
        return templateEngine.process("mail/amount_of_tasks", context);

    }

}