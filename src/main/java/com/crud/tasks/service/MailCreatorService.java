package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8080/v1/trello/boards/1");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name",adminConfig.getAdminName());
        context.setVariable("goodbye_message",adminConfig.getAppName());
        context.setVariable("company_details",companyConfig.getName() + ", email: " + companyConfig.getEmail()+ ", tel: "+companyConfig.getPhone());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

}