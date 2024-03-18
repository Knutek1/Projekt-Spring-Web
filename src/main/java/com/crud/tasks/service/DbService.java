package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DbService {

    private final TaskRepository repository;
    public List<Task> getAllTasks() {
        return repository.findAll();
    }
    public Task getTask(Long taskId){
        return repository.getById(taskId);
    }
    public Task saveTask(final Task task) {
        return repository.save(task);
    }
    public Task getTaskWithFindById(Long taskId) throws TaskNotFoundException {
        return repository.findById(taskId).orElseThrow(TaskNotFoundException::new);
    }
    public void deleteTask(Long taskId) throws TaskNotFoundException{
        try {
            repository.deleteById(taskId);
        }catch (Exception e){
            throw new TaskNotFoundException();
        }
    }


}