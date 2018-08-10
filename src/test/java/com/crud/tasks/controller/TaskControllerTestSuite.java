package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DbService dbService;

    @MockBean
    TaskMapper taskMapper;

    @Test
    public void shouldFetchEmptyTasksList() throws Exception {
        //Given
        List<Task> tasksList = new ArrayList<>();
        List<TaskDto> tasksDtoList = new ArrayList<>();

        when(dbService.getAllTasks()).thenReturn(tasksList);
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(tasksDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldFetchTasksList() throws Exception {
        //Given
        List<Task> tasksList = new ArrayList<>();
        tasksList.add(new Task(1L, "test_title", "test_content"));

        List<TaskDto> tasksDtoList = new ArrayList<>();
        tasksDtoList.add(new TaskDto(1L, "test_title", "test_content"));

        when(dbService.getAllTasks()).thenReturn(tasksList);
        when(taskMapper.mapToTaskDtoList(anyList())).thenReturn(tasksDtoList);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("test_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("test_content")));
    }

    @Test
    public void shouldFetchASingleTask() throws Exception {
        //Given
        Task theTask = new Task(1L, "test_title", "test_content");
        TaskDto theTaskDto = new TaskDto(1L, "test_title", "test_content");

        when(dbService.findTaskById(1L)).thenReturn(java.util.Optional.ofNullable(theTask));
        when(taskMapper.mapToTaskDto(theTask)).thenReturn(theTaskDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/task/getTask")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "1"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("test_content")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/task/deleteTask")
                .param("taskId", "1"))
                .andExpect(MockMvcResultMatchers.status().is(200));
        verify(dbService, times(1)).deleteTaskById(1L);
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        Task theTask = new Task(1L, "test_title", "test_content");
        TaskDto theTaskDto = new TaskDto(1L, "test_title", "test_content");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(theTaskDto);

        when(taskMapper.mapToTask(theTaskDto)).thenReturn(theTask);
        when(taskMapper.mapToTaskDto(theTask)).thenReturn(theTaskDto);
        when(dbService.saveTask(theTask)).thenReturn(theTask);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test_title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("test_content")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        Task theTask = new Task(1L, "test_title", "test_content");
        TaskDto theTaskDto = new TaskDto(1L, "test_title", "test_content");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(theTaskDto);

        when(taskMapper.mapToTask(theTaskDto)).thenReturn(theTask);
        when(dbService.saveTask(theTask)).thenReturn(theTask);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
        verify(dbService, times(1)).saveTask(theTask);
    }
}