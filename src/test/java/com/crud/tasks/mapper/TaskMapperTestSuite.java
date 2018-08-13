package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskMapperTestSuite {

    @Autowired
    TaskMapper taskMapper;

    @Test
    public void testMapTaskToTaskDto() {
        //Given
        Task task = new Task(1L, "Test task", "Test content");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertNotNull(taskDto);
        assertEquals(Long.valueOf(1), Long.valueOf(taskDto.getId()));
        assertEquals("Test task", taskDto.getTitle());
        assertEquals("Test content", taskDto.getContent());
    }

    @Test
    public void testMapTaskDtoToTask() {
        //Given
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setTitle("Test task");
        taskDto.setContent("Test content");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertNotNull(taskDto);
        assertEquals(Long.valueOf(1), Long.valueOf(task.getId()));
        assertEquals("Test task", task.getTitle());
        assertEquals("Test content", task.getContent());
    }

    @Test
    public void testTaskListToTaskDtoList() {
        //Given
        List<Task> inputList = new ArrayList<>();
        inputList.add(new Task(1L, "Test task", "Test content"));
        //When
        List<TaskDto> resultList = taskMapper.mapToTaskDtoList(inputList);
        //Then
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        assertEquals(Long.valueOf(1), Long.valueOf(resultList.get(0).getId()));
        assertEquals("Test task", resultList.get(0).getTitle());
        assertEquals("Test content", resultList.get(0).getContent());
    }
}
