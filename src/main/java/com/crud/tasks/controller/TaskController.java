package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @RequestMapping(method = RequestMethod.GET, value = "getTasks")
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getTask/{taskId}")
    public TaskDto getTask(@PathVariable Long taskId) {
        return new TaskDto(1L, "test title", "test_content");
    }

    @DeleteMapping(value = "deleteTask/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        //TO DO
    }

    @PutMapping(value = "updateTask")
    public TaskDto updateTask(@RequestParam("taskDto") TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "test_content");
    }

    @PostMapping(value = "createTask")
    public void createTask(@RequestParam("taskDto") TaskDto taskDto) {
        //TO DO
    }

}
