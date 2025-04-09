package com.team.AssignTaskScheduleTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.team.assignTaskSchedule.AssignTaskScheduleController;
import com.team.assignTaskSchedule.AssignTaskScheduleEntity;
import com.team.assignTaskSchedule.AssignTaskScheduleService;
import com.team.assignTaskSchedule.SearchDto;
import com.team.player.PlayerEntity;
import com.team.player.PlayerService;
import com.team.schedule.ScheduleEntity;
import com.team.schedule.ScheduleService;
import com.team.task.TaskEntity;
import com.team.task.TaskService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


    
    class AssignTaskScheduleControllerTest {
   
    

   
     @Mock
    private AssignTaskScheduleService assignTaskScheduleService;

    @Mock
    private PlayerService playerService;

    @Mock
    private ScheduleService scheduleService;

    @Mock
    private TaskService taskService;

    @Mock
    private Model model;

    @InjectMocks
    private AssignTaskScheduleController assignTaskScheduleController;


 

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
    }

    /**
     * Test case: Positive scenario where task schedules are retrieved successfully.
     * Expected: The method should return the "AssignTaskSchedule" view and populate the model with task schedules.
     */
      @Test
    void getAssignTaskSchedule_ShouldReturnAssignTaskScheduleView_WithTaskSchedules() {
        // Arrange
        List<AssignTaskScheduleEntity> taskSchedules = new ArrayList<>();
        taskSchedules.add(new AssignTaskScheduleEntity()); // Add a sample task schedule
        when(assignTaskScheduleService.getTaskSchedules()).thenReturn(taskSchedules);

        // Act
        String viewName = assignTaskScheduleController.getAssignTaskSchedule(model);

        // Assert
        assertEquals("AssignTaskSchedule", viewName, "The view name should be 'AssignTaskSchedule'");
        verify(model).addAttribute("assignTaskSchedules", taskSchedules);
        verify(model).addAttribute(eq("searchDto"), any(SearchDto.class));
        verify(assignTaskScheduleService, times(1)).getTaskSchedules();
    }

     /**
     * Test case: Negative scenario where no task schedules are available.
     * Expected: The method should return the "AssignTaskSchedule" view with an empty list of task schedules.
     */
    @Test
    void getAssignTaskSchedule_NoTaskSchedules_ShouldReturnEmptyList() {
        // Arrange
        List<AssignTaskScheduleEntity> emptyTaskSchedules = new ArrayList<>();
        when(assignTaskScheduleService.getTaskSchedules()).thenReturn(emptyTaskSchedules);

        // Act
        String viewName = assignTaskScheduleController.getAssignTaskSchedule(model);

        // Assert
        assertEquals("AssignTaskSchedule", viewName, "The view name should be 'AssignTaskSchedule'");
        verify(model).addAttribute("assignTaskSchedules", emptyTaskSchedules);
        verify(model).addAttribute(eq("searchDto"), any(SearchDto.class));
        verify(assignTaskScheduleService, times(1)).getTaskSchedules();
    }

    /**
     * Test case: Negative scenario where the service throws an exception.
     * Expected: The method should propagate the exception.
     */

        @Test
    void getAssignTaskSchedule_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        when(assignTaskScheduleService.getTaskSchedules()).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        RuntimeException exception = 
            assertThrows(RuntimeException.class, () -> assignTaskScheduleController.getAssignTaskSchedule(model));
        assertEquals("Service error", exception.getMessage(), "The exception message should match");
        verify(assignTaskScheduleService, times(1)).getTaskSchedules();
    }



      /**
     * Test case: Positive scenario where all required data is retrieved successfully.
     * Expected: The method should return the "AddAssignTaskSchedule" view and populate the model with players, schedules, tasks, and a new AssignTaskScheduleEntity.
     */
    @Test
    void createAssignTaskScheduleForm_ShouldReturnAddAssignTaskScheduleView_WithRequiredData() {
        // Arrange
        List<PlayerEntity> players = new ArrayList<>();
        players.add(new PlayerEntity()); // Add a sample player
        List<ScheduleEntity> schedules = new ArrayList<>();
        schedules.add(new ScheduleEntity()); // Add a sample schedule
        List<TaskEntity> tasks = new ArrayList<>();
        tasks.add(new TaskEntity()); // Add a sample task

        when(playerService.getPlayers()).thenReturn(players);
        when(scheduleService.getSchedules()).thenReturn(schedules);
        when(taskService.getTasks()).thenReturn(tasks);

        // Act
        String viewName = assignTaskScheduleController.createAssignTaskScheduleForm(model);

        // Assert
        assertEquals("AddAssignTaskSchedule", viewName, "The view name should be 'AddAssignTaskSchedule'");
        verify(model).addAttribute("players", players);
        verify(model).addAttribute("schedules", schedules);
        verify(model).addAttribute("tasks", tasks);
        verify(model).addAttribute(eq("assignTaskSchedule"), any(AssignTaskScheduleEntity.class));
        verify(playerService, times(1)).getPlayers();
        verify(scheduleService, times(1)).getSchedules();
        verify(taskService, times(1)).getTasks();
    }

     /**
     * Test case: Negative scenario where the player service throws an exception.
     * Expected: The method should propagate the exception.
     */
    @Test
    void createAssignTaskScheduleForm_PlayerServiceThrowsException_ShouldPropagateException() {
        // Arrange
        when(playerService.getPlayers()).thenThrow(new RuntimeException("Player service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleController.createAssignTaskScheduleForm(model)
        );
        assertEquals("Player service error", exception.getMessage(), "The exception message should match");
        verify(playerService, times(1)).getPlayers();
        verifyNoInteractions(scheduleService, taskService);
    }
      /**
     * Test case: Negative scenario where the schedule service throws an exception.
     * Expected: The method should propagate the exception.
     */
    @Test
    void createAssignTaskScheduleForm_ScheduleServiceThrowsException_ShouldPropagateException() {
        // Arrange
        when(playerService.getPlayers()).thenReturn(new ArrayList<>());
        when(scheduleService.getSchedules()).thenThrow(new RuntimeException("Schedule service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleController.createAssignTaskScheduleForm(model)
        );
        assertEquals("Schedule service error", exception.getMessage(), "The exception message should match");
        verify(playerService, times(1)).getPlayers();
        verify(scheduleService, times(1)).getSchedules();
        verifyNoInteractions(taskService);
    }
     /**
     * Test case: Negative scenario where the task service throws an exception.
     * Expected: The method should propagate the exception.
     */
    @Test
    void createAssignTaskScheduleForm_TaskServiceThrowsException_ShouldPropagateException() {
        // Arrange
        when(playerService.getPlayers()).thenReturn(new ArrayList<>());
        when(scheduleService.getSchedules()).thenReturn(new ArrayList<>());
        when(taskService.getTasks()).thenThrow(new RuntimeException("Task service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleController.createAssignTaskScheduleForm(model)
        );
        assertEquals("Task service error", exception.getMessage(), "The exception message should match");
        verify(playerService, times(1)).getPlayers();
        verify(scheduleService, times(1)).getSchedules();
        verify(taskService, times(1)).getTasks();
    }


 /**
     * Test case: Positive scenario where all required data is retrieved successfully.
     * Expected: The method should return the "EditAssignTaskSchedule" view and populate the model with players, schedules, tasks, and the existing AssignTaskScheduleEntity.
     */
    @Test
    void editAssignTaskScheduleForm_ShouldReturnEditAssignTaskScheduleView_WithRequiredData() {
        // Arrange
        Long id = 1L;
        List<PlayerEntity> players = new ArrayList<>();
        players.add(new PlayerEntity()); // Add a sample player
        List<ScheduleEntity> schedules = new ArrayList<>();
        schedules.add(new ScheduleEntity()); // Add a sample schedule
        List<TaskEntity> tasks = new ArrayList<>();
        tasks.add(new TaskEntity()); // Add a sample task
        AssignTaskScheduleEntity existingTaskSchedule = new AssignTaskScheduleEntity();

        when(playerService.getPlayers()).thenReturn(players);
        when(scheduleService.getSchedules()).thenReturn(schedules);
        when(taskService.getTasks()).thenReturn(tasks);
        when(assignTaskScheduleService.getById(id)).thenReturn(existingTaskSchedule);
 // Act
 String viewName = assignTaskScheduleController.editAssignTaskScheduleForm(id, model);

 // Assert
 assertEquals("EditAssignTaskSchedule", viewName, "The view name should be 'EditAssignTaskSchedule'");
 verify(model).addAttribute("players", players);
 verify(model).addAttribute("schedules", schedules);
 verify(model).addAttribute("tasks", tasks);
 verify(model).addAttribute("assignTaskSchedule", existingTaskSchedule);
 verify(playerService, times(1)).getPlayers();
 verify(scheduleService, times(1)).getSchedules();
 verify(taskService, times(1)).getTasks();
 verify(assignTaskScheduleService, times(1)).getById(id);
}

 /**
     * Test case: Negative scenario where the AssignTaskScheduleService throws an exception.
     * Expected: The method should propagate the exception.
     */
    @Test
    void editAssignTaskScheduleForm_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        Long id = 1L;
        when(assignTaskScheduleService.getById(id)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleController.editAssignTaskScheduleForm(id, model)
        );
        assertEquals("Service error", exception.getMessage(), "The exception message should match");
        verify(assignTaskScheduleService, times(1)).getById(id);
       // verifyNoInteractions(playerService, scheduleService, taskService);
    }

     /**
     * Test case: Negative scenario where the PlayerService throws an exception.
     * Expected: The method should propagate the exception.
     */
    @Test
    void editAssignTaskScheduleForm_PlayerServiceThrowsException_ShouldPropagateException() {
        // Arrange
        Long id = 1L;
        when(assignTaskScheduleService.getById(id)).thenReturn(new AssignTaskScheduleEntity());
        when(playerService.getPlayers()).thenThrow(new RuntimeException("Player service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleController.editAssignTaskScheduleForm(id, model)
        );
        assertEquals("Player service error", exception.getMessage(), "The exception message should match");
      //  verify(assignTaskScheduleService, times(1)).getById(id);
        verify(playerService, times(1)).getPlayers();
        verifyNoInteractions(scheduleService, taskService);
    }
    /**
     * Test case: Negative scenario where the ScheduleService throws an exception.
     * Expected: The method should propagate the exception.
     */
    @Test
    void editAssignTaskScheduleForm_ScheduleServiceThrowsException_ShouldPropagateException() {
        // Arrange
        Long id = 1L;
        when(assignTaskScheduleService.getById(id)).thenReturn(new AssignTaskScheduleEntity());
        when(playerService.getPlayers()).thenReturn(new ArrayList<>());
        when(scheduleService.getSchedules()).thenThrow(new RuntimeException("Schedule service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleController.editAssignTaskScheduleForm(id, model)
        );
        assertEquals("Schedule service error", exception.getMessage(), "The exception message should match");
       // verify(assignTaskScheduleService, times(1)).getById(id);
        verify(playerService, times(1)).getPlayers();
        verify(scheduleService, times(1)).getSchedules();
        verifyNoInteractions(taskService);
    }
      /**
     * Test case: Negative scenario where the TaskService throws an exception.
     * Expected: The method should propagate the exception.
     */
    @Test
    void editAssignTaskScheduleForm_TaskServiceThrowsException_ShouldPropagateException() {
        // Arrange
        Long id = 1L;
        when(assignTaskScheduleService.getById(id)).thenReturn(new AssignTaskScheduleEntity());
        when(playerService.getPlayers()).thenReturn(new ArrayList<>());
        when(scheduleService.getSchedules()).thenReturn(new ArrayList<>());
        when(taskService.getTasks()).thenThrow(new RuntimeException("Task service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleController.editAssignTaskScheduleForm(id, model)
        );
        assertEquals("Task service error", exception.getMessage(), "The exception message should match");
      //  verify(assignTaskScheduleService, times(1)).getById(id);
        verify(playerService, times(1)).getPlayers();
        verify(scheduleService, times(1)).getSchedules();
        verify(taskService, times(1)).getTasks();
    }



     /**
     * Test case: Positive scenario where a new task schedule is created successfully.
     * Expected: The method should redirect to the root URL ("/") after successful creation.
     */
    @Test
    void createAssignTaskSchedule_ShouldRedirectToRoot_WhenSuccessful() {
        // Arrange
        AssignTaskScheduleEntity assignTaskSchedule = new AssignTaskScheduleEntity();
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setStartDate(LocalDate.of(2025, 4, 1));
        schedule.setEndDate(LocalDate.of(2025, 4, 30));
        assignTaskSchedule.setSchedule(schedule);

        when(assignTaskScheduleService.create(assignTaskSchedule)).thenReturn(assignTaskSchedule);
 // Act
 String viewName = assignTaskScheduleController.createAssignTaskSchedule(assignTaskSchedule);

 // Assert
 assertEquals("redirect:/", viewName, "The method should redirect to the root URL");
 verify(assignTaskScheduleService, times(1)).create(assignTaskSchedule);
 assertEquals(LocalDate.of(2025, 4, 1), assignTaskSchedule.getStartDate(), "Start date should be set correctly");
 assertEquals(LocalDate.of(2025, 4, 30), assignTaskSchedule.getEndDate(), "End date should be set correctly");
 assertNotNull(assignTaskSchedule.getCreatedOn(), "CreatedOn should be set");
}

  /**
     * Test case: Negative scenario where the service throws an exception during task schedule creation.
     * Expected: The method should propagate the exception.
     */
    @Test
    void createAssignTaskSchedule_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        AssignTaskScheduleEntity assignTaskSchedule = new AssignTaskScheduleEntity();
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setStartDate(LocalDate.of(2025, 4, 1));
        schedule.setEndDate(LocalDate.of(2025, 4, 30));
        assignTaskSchedule.setSchedule(schedule);

        when(assignTaskScheduleService.create(assignTaskSchedule)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleController.createAssignTaskSchedule(assignTaskSchedule)
        );
        assertEquals("Service error", exception.getMessage(), "The exception message should match");
        verify(assignTaskScheduleService, times(1)).create(assignTaskSchedule);
    }

  /**
     * Test case: Negative scenario where the schedule is null.
     * Expected: The method should handle the null schedule gracefully.
     */
  /*  @Test
    void createAssignTaskSchedule_NullSchedule_ShouldHandleGracefully() {
        // Arrange
        AssignTaskScheduleEntity assignTaskSchedule = new AssignTaskScheduleEntity();
        assignTaskSchedule.setSchedule(null);

        // Act
        String viewName = assignTaskScheduleController.createAssignTaskSchedule(assignTaskSchedule);

        // Assert
        assertEquals("redirect:/", viewName, "The method should still redirect to the root URL");
        verify(assignTaskScheduleService, times(1)).create(assignTaskSchedule);
        assertNull(assignTaskSchedule.getStartDate(), "Start date should remain null");
        assertNull(assignTaskSchedule.getEndDate(), "End date should remain null");
    } */




     /**
     * Test case: Positive scenario where an existing assign task schedule is updated successfully.
     * Expected: The method should redirect to the root URL ("/").
     */
    @Test
    void createAssignTaskSchedule_ShouldRedirectToRoot_WhenUpdateIsSuccessful() {
        // Arrange
        Long id = 1L;
        AssignTaskScheduleEntity existingAssignTaskSchedule = new AssignTaskScheduleEntity();
        existingAssignTaskSchedule.setId(id);
        existingAssignTaskSchedule.setTask(new TaskEntity());
        existingAssignTaskSchedule.setPlayer(new PlayerEntity());
        existingAssignTaskSchedule.setSchedule(new ScheduleEntity());
        existingAssignTaskSchedule.setStartDate(LocalDate.of(2025, 4, 1));
        existingAssignTaskSchedule.setEndDate(LocalDate.of(2025, 4, 30));

        AssignTaskScheduleEntity updatedAssignTaskSchedule = new AssignTaskScheduleEntity();
        updatedAssignTaskSchedule.setTask(new TaskEntity());
        updatedAssignTaskSchedule.setPlayer(new PlayerEntity());
        updatedAssignTaskSchedule.setSchedule(new ScheduleEntity());
        updatedAssignTaskSchedule.getSchedule().setStartDate(LocalDate.of(2025, 5, 1));
        updatedAssignTaskSchedule.getSchedule().setEndDate(LocalDate.of(2025, 5, 31));
        when(assignTaskScheduleService.getById(id)).thenReturn(existingAssignTaskSchedule);
        when(assignTaskScheduleService.create(existingAssignTaskSchedule)).thenReturn(existingAssignTaskSchedule);

        // Act
        String viewName = assignTaskScheduleController.createAssignTaskSchedule(id, updatedAssignTaskSchedule);

        // Assert
        assertEquals("redirect:/", viewName, "The method should redirect to the root URL");
        verify(assignTaskScheduleService, times(1)).getById(id);
        verify(assignTaskScheduleService, times(1)).create(existingAssignTaskSchedule);

        assertEquals(LocalDate.of(2025, 5, 1), existingAssignTaskSchedule.getStartDate(), "The start date should be updated");
        assertEquals(LocalDate.of(2025, 5, 31), existingAssignTaskSchedule.getEndDate(), "The end date should be updated");
        assertNotNull(existingAssignTaskSchedule.getUpdatedOn(), "The updatedOn field should be set");
    }

  /**
     * Test case: Negative scenario where the assign task schedule ID does not exist.
     * Expected: The method should throw a RuntimeException.
     */
    @Test
    void createAssignTaskSchedule_IdDoesNotExist_ShouldThrowException() {
        // Arrange
        Long id = 1L;
        AssignTaskScheduleEntity updatedAssignTaskSchedule = new AssignTaskScheduleEntity();
        updatedAssignTaskSchedule.setTask(new TaskEntity());
        updatedAssignTaskSchedule.setPlayer(new PlayerEntity());
        updatedAssignTaskSchedule.setSchedule(new ScheduleEntity());

        when(assignTaskScheduleService.getById(id)).thenThrow(new RuntimeException("AssignTaskSchedule not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleController.createAssignTaskSchedule(id, updatedAssignTaskSchedule)
        );
        assertEquals("AssignTaskSchedule not found", exception.getMessage(), "The exception message should match");
        verify(assignTaskScheduleService, times(1)).getById(id);
        verify(assignTaskScheduleService, never()).create(any());
    }

/**
     * Test case: Negative scenario where the input assign task schedule entity is null.
     * Expected: The method should throw a NullPointerException.
     */
    @Test
    void createAssignTaskSchedule_NullAssignTaskSchedule_ShouldThrowNullPointerException() {
        // Arrange
        Long id = 1L;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> 
            assignTaskScheduleController.createAssignTaskSchedule(id, null),
            "The method should throw a NullPointerException when the input assign task schedule is null"
        );
       // verify(assignTaskScheduleService, never()).getById(id);
        verify(assignTaskScheduleService, never()).create(any());
    }





    /**
     * Test case: Positive scenario where task schedules are retrieved successfully based on the search criteria.
     * Expected: The method should return the "AssignTaskSchedule" view and populate the model with filtered task schedules.
     */
    @Test
    void searchSchedule_ShouldReturnAssignTaskScheduleView_WithFilteredTaskSchedules() {
        // Arrange
        SearchDto searchDto = new SearchDto();
        searchDto.setStartDateStr("2025-04-01");
        searchDto.setEndDateStr("2025-04-30");

        List<AssignTaskScheduleEntity> filteredTaskSchedules = new ArrayList<>();
        filteredTaskSchedules.add(new AssignTaskScheduleEntity()); // Add a sample task schedule

        when(assignTaskScheduleService.findByDateBetween(searchDto)).thenReturn(filteredTaskSchedules);
        // Act
        String viewName = assignTaskScheduleController.searchSchedule(searchDto, model);

        // Assert
        assertEquals("AssignTaskSchedule", viewName, "The view name should be 'AssignTaskSchedule'");
        verify(model).addAttribute("assignTaskSchedules", filteredTaskSchedules);
        verify(model).addAttribute("searchDto", searchDto);
        verify(assignTaskScheduleService, times(1)).findByDateBetween(searchDto);
    }

  /**
     * Test case: Negative scenario where no task schedules match the search criteria.
     * Expected: The method should return the "AssignTaskSchedule" view with an empty list of task schedules.
     */
    @Test
    void searchSchedule_NoMatchingTaskSchedules_ShouldReturnEmptyList() {
        // Arrange
        SearchDto searchDto = new SearchDto();
        searchDto.setStartDateStr("2025-04-01");
        searchDto.setEndDateStr("2025-04-30");

        List<AssignTaskScheduleEntity> emptyTaskSchedules = new ArrayList<>();

        when(assignTaskScheduleService.findByDateBetween(searchDto)).thenReturn(emptyTaskSchedules);
  // Act
  String viewName = assignTaskScheduleController.searchSchedule(searchDto, model);

  // Assert
  assertEquals("AssignTaskSchedule", viewName, "The view name should be 'AssignTaskSchedule'");
  verify(model).addAttribute("assignTaskSchedules", emptyTaskSchedules);
  verify(model).addAttribute("searchDto", searchDto);
  verify(assignTaskScheduleService, times(1)).findByDateBetween(searchDto);
}
 /**
     * Test case: Negative scenario where the service throws an exception during the search.
     * Expected: The method should propagate the exception.
     */
    @Test
    void searchSchedule_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        SearchDto searchDto = new SearchDto();
        searchDto.setStartDateStr("2025-04-01");
        searchDto.setEndDateStr("2025-04-30");

        when(assignTaskScheduleService.findByDateBetween(searchDto)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleController.searchSchedule(searchDto, model)
        );
        assertEquals("Service error", exception.getMessage(), "The exception message should match");
        verify(assignTaskScheduleService, times(1)).findByDateBetween(searchDto);
        verifyNoInteractions(model);
    }

     /**
     * Test case: Negative scenario where the search criteria are invalid (e.g., null dates).
     * Expected: The method should handle invalid search criteria gracefully.
     */
    @Test
    void searchSchedule_InvalidSearchCriteria_ShouldHandleGracefully() {
        // Arrange
        SearchDto searchDto = new SearchDto();
        searchDto.setStartDateStr(null);
        searchDto.setEndDateStr(null);

        List<AssignTaskScheduleEntity> allTaskSchedules = new ArrayList<>();
        allTaskSchedules.add(new AssignTaskScheduleEntity()); // Add a sample task schedule

        when(assignTaskScheduleService.findByDateBetween(searchDto)).thenReturn(allTaskSchedules);

        // Act
        String viewName = assignTaskScheduleController.searchSchedule(searchDto, model);
  // Assert
  assertEquals("AssignTaskSchedule", viewName, "The view name should be 'AssignTaskSchedule'");
  verify(model).addAttribute("assignTaskSchedules", allTaskSchedules);
  verify(model).addAttribute("searchDto", searchDto);
  verify(assignTaskScheduleService, times(1)).findByDateBetween(searchDto);
}
    }
    

