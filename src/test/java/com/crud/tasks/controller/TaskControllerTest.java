package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @MockBean
    private DbService service;
    @MockBean
    private TaskMapper taskMapper;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void getTasksTest() throws Exception {
        //given
        List<Task> tasks = List.of(new Task(1L, "Task 1", "Content 1"));
        List<TaskDto> taskDtos = List.of(new TaskDto(1L, "Task 1", "Content 1"));
        when(service.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);
        //when & then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Task 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Content 1")));
    }

    @Test
    void getTaskTest() throws Exception {
        //given
        TaskDto taskDto = new TaskDto(2L, "Task 2", "Content 2");
        Task task = new Task(2L, "Task 2", "Content 2");
        when(service.getTaskWithFindById(2L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        //when & then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task 2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content 2")));
    }

    @Test
    void deleteTaskTest() throws Exception {
        //given
        Task task = new Task(3L, "Task 3", "Content 3");
        service.deleteTask(95L);
        //when & then

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void postTaskTest() throws Exception {
        //given
        TaskDto taskDto = new TaskDto(4L, "Task 4", "Content 4");
        Task task = new Task(4L, "Task 4", "Content 4");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Task 4")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Content 4")));


    }


}
