package com.team.TaskTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.team.task.TaskController;
import com.team.task.TaskEntity;
import com.team.task.TaskService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskControllerTests extends TaskController {
    
    @Mock
    private TaskService taskService;

    @Mock
    private Model model;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

     /**
     * Test case: Positive scenario where tasks are retrieved successfully.
     * Expected: The method should return the "Tasks" view and populate the model with tasks.
     */
  /*  @Test
    void getTasks_ShouldReturnTasksView_WithTasks() {
        // Arrange
        List<TaskEntity> tasks = new ArrayList<>();
        tasks.add(new TaskEntity()); // Add a sample task
        when(taskService.getTasks()).thenReturn(tasks);

        // Act
        String viewName = taskController.getTasks(model);

        // Assert
        assertEquals("Tasks", viewName, "The view name should be 'Tasks'");
        verify(model).addAttribute("tasks", tasks);
        verify(taskService, times(1)).getTasks();
    } */

    /**
     * Test case: Negative scenario where no tasks are available.
     * Expected: The method should return the "Tasks" view with an empty list of tasks.
     */
  /*  @Test
    void getTasks_NoTasks_ShouldReturnEmptyList() {
        // Arrange
        List<TaskEntity> emptyTasks = new ArrayList<>();
        when(taskService.getTasks()).thenReturn(emptyTasks);

        // Act
        String viewName = taskController.getTasks(model);

        // Assert
        assertEquals("Tasks", viewName, "The view name should be 'Tasks'");
        verify(model).addAttribute("tasks", emptyTasks);
        verify(taskService, times(1)).getTasks();
    } */
    /**
     * Test case: Negative scenario where the service throws an exception.
     * Expected: The method should propagate the exception.
     */
  /*  @Test
    void getTasks_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        when(taskService.getTasks()).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            taskController.getTasks(model)
        );
        assertEquals("Service error", exception.getMessage(), "The exception message should match");
        verify(taskService, times(1)).getTasks();
    } */



 /**
     * Test case: Positive scenario where the task form is created successfully.
     * Expected: The method should return the "AddTask" view and populate the model with a new TaskEntity.
     */
    @Test
    void createTaskForm_ShouldReturnAddTaskView_WithNewTaskEntity() {
        // Arrange
        TaskEntity task = new TaskEntity();

        // Act
        String viewName = taskController.createTaskForm(model);

        // Assert
        assertEquals("AddTask", viewName, "The view name should be 'AddTask'");
        verify(model).addAttribute("task", task);
    }
 /**
     * Test case: Negative scenario where the model is null.
     * Expected: The method should throw a NullPointerException.
     */
    @Test
    void createTaskForm_NullModel_ShouldThrowNullPointerException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> 
            taskController.createTaskForm(null),
            "The method should throw a NullPointerException when the model is null"
        );
    }



      /**
     * Test case: Positive scenario where the task is retrieved successfully by ID.
     * Expected: The method should return the "EditTask" view and populate the model with the task.
     */
    @Test
    void editTaskForm_ShouldReturnEditTaskView_WithTask() {
        // Arrange
        Long id = 1L;
        TaskEntity task = new TaskEntity();
        task.setId(id);
        task.setName("Sample Task");
        task.setDescription("This is a sample task.");
        when(taskService.getById(id)).thenReturn(task);

        // Act
        String viewName = taskController.editTaskForm(id, model);

        // Assert
        assertEquals("EditTask", viewName, "The view name should be 'EditTask'");
        verify(model).addAttribute("task", task);
        verify(taskService, times(1)).getById(id);
    }

    /**
     * Test case: Negative scenario where the task ID does not exist.
     * Expected: The method should throw a RuntimeException.
     */
    @Test
    void editTaskForm_TaskIdDoesNotExist_ShouldThrowException() {
        // Arrange
        Long id = 1L;
        when(taskService.getById(id)).thenThrow(new RuntimeException("Task not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            taskController.editTaskForm(id, model)
        );
        assertEquals("Task not found", exception.getMessage(), "The exception message should match");
        verify(taskService, times(1)).getById(id);
        verifyNoInteractions(model);
    }

     /**
     * Test case: Negative scenario where the model is null.
     * Expected: The method should throw a NullPointerException.
     */
    @Test
    void editTaskForm_NullModel_ShouldThrowNullPointerException() {
        // Arrange
        Long id = 1L;
        TaskEntity task = new TaskEntity();
        task.setId(id);
        when(taskService.getById(id)).thenReturn(task);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> 
            taskController.editTaskForm(id, null),
            "The method should throw a NullPointerException when the model is null"
        );
        verify(taskService, times(1)).getById(id);
    }


    /**
     * Test case: Positive scenario where a task is created successfully.
     * Expected: The method should redirect to the "get-tasks" endpoint.
     */
    @Test
    void createTask_ShouldRedirectToGetTasks_WhenSuccessful() {
        // Arrange
        TaskEntity task = new TaskEntity();
        task.setName("Sample Task");
        task.setDescription("This is a sample task.");

        when(taskService.create(task)).thenReturn(task);

        // Act
        String viewName = taskController.createTask(task);

        // Assert
        assertEquals("redirect:/task/get-tasks", viewName, "The method should redirect to the 'get-tasks' endpoint");
        verify(taskService, times(1)).create(task);
        assertNotNull(task.getCreatedOn(), "The createdOn field should be set");
    }

     /**
     * Test case: Negative scenario where the service throws an exception during task creation.
     * Expected: The method should propagate the exception.
     */
    @Test
    void createTask_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        TaskEntity task = new TaskEntity();
        task.setName("Sample Task");
        task.setDescription("This is a sample task.");

        when(taskService.create(task)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            taskController.createTask(task)
        );
        assertEquals("Service error", exception.getMessage(), "The exception message should match");
        verify(taskService, times(1)).create(task);
    }

     /**
     * Test case: Negative scenario where the input task entity is null.
     * Expected: The method should throw a NullPointerException.
     */
    @Test
    void createTask_NullTask_ShouldThrowNullPointerException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> 
            taskController.createTask(null),
            "The method should throw a NullPointerException when the input is null"
        );
        verifyNoInteractions(taskService);
    }


      /**
     * Test case: Positive scenario where an existing task is updated successfully.
     * Expected: The method should redirect to the "get-tasks" endpoint.
     */
    @Test
    void createTask_ShouldRedirectToGetTasks_WhenUpdateIsSuccessful() {
        // Arrange
        Long id = 1L;
        TaskEntity existingTask = new TaskEntity();
        existingTask.setId(id);
        existingTask.setName("Old Task");
        existingTask.setDescription("Old Description");

        TaskEntity updatedTask = new TaskEntity();
        updatedTask.setName("Updated Task");
        updatedTask.setDescription("Updated Description");

        when(taskService.getById(id)).thenReturn(existingTask);
        when(taskService.create(existingTask)).thenReturn(existingTask);
// Act
String viewName = taskController.createTask(id, updatedTask);

// Assert
assertEquals("redirect:/task/get-tasks", viewName, "The method should redirect to the 'get-tasks' endpoint");
verify(taskService, times(1)).getById(id);
//verify(taskService, times(1)).create(existingTask);

assertEquals("Updated Task", existingTask.getName(), "The task name should be updated");
assertEquals("Updated Description", existingTask.getDescription(), "The task description should be updated");
assertNotNull(existingTask.getUpdatedOn(), "The updatedOn field should be set");
}

 /**
     * Test case: Negative scenario where the task ID does not exist.
     * Expected: The method should throw a RuntimeException.
     */
    @Test
    void createTask_TaskIdDoesNotExist_ShouldThrowException() {
        // Arrange
        Long id = 1L;
        TaskEntity updatedTask = new TaskEntity();
        updatedTask.setName("Updated Task");
        updatedTask.setDescription("Updated Description");

        when(taskService.getById(id)).thenThrow(new RuntimeException("Task not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            taskController.createTask(id, updatedTask)
        );
        assertEquals("Task not found", exception.getMessage(), "The exception message should match");
        verify(taskService, times(1)).getById(id);
        verify(taskService, never()).create(any());
    }

    /**
     * Test case: Negative scenario where the input task entity is null.
     * Expected: The method should throw a NullPointerException.
     */
    @Test
    void createTask_NullTask_ShouldThrowNullPointerException2() {
        // Arrange
        Long id = 1L;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> 
            taskController.createTask(id, null),
            "The method should throw a NullPointerException when the input task is null"
        );
      //  verify(taskService, never()).getById(id);
        verify(taskService, never()).create(any());
    }
}
