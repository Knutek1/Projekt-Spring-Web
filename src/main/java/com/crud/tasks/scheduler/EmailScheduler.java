package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;
    private static final String SUBJECT = "Tasks: Once a day email";
    @Scheduled(cron = "0/30 * * * * *")
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String oneOrManyTasks;
        if(size==1) {
            oneOrManyTasks = "task";
        }
        else {
            oneOrManyTasks = "tasks";
        }

        simpleEmailService.send(
                new Mail(
                        adminConfig.getAdminMail(),
                        "szpula@szpula.com",
                        SUBJECT,
                        "Currently in database you got: " + size + " " + oneOrManyTasks
                )
        );
    }
}