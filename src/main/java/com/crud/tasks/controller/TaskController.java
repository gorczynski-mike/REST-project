package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/task")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask")
    public TaskDto getTask(@RequestParam Long taskId) throws TaskNotFoundException{
        return taskMapper.mapToTaskDto(service.findTaskById(taskId).orElseThrow(TaskNotFoundException::new));
    }

//    @RequestMapping(method = RequestMethod.GET, value = "getTask/{taskId}")
//    public TaskDto getTask(@PathVariable Long taskId) {
//        return taskMapper.mapToTaskDto(service.findTaskById(taskId));
//    }

    @DeleteMapping(value = "deleteTask")
    public void deleteTask(@RequestParam Long taskId) {
        service.deleteTaskById(taskId);
    }

    @PutMapping(value = "updateTask")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
//        return new TaskDto(1L, "Edited test title", "test_content");
        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
    }

//    @PostMapping(value = "createTask", consumes = APPLICATION_JSON_VALUE)
//    public void createTask(@RequestBody TaskDto taskDto) {
//        service.saveTask(taskMapper.mapToTask(taskDto));
//    }

    @PostMapping(value = "createTask", consumes = {APPLICATION_JSON_VALUE, APPLICATION_FORM_URLENCODED_VALUE})
    public void createTask(@RequestBody TaskDto taskDto) {
        service.saveTask(taskMapper.mapToTask(taskDto));
    }

}
