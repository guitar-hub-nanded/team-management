package com.team.ScheduleControllerTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.team.schedule.ScheduleController;
import com.team.schedule.ScheduleEntity;
import com.team.schedule.ScheduleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ScheduleControllerTest {
    @Mock
    private ScheduleService scheduleService;

    @Mock
    private Model model;

    @InjectMocks
    private ScheduleController scheduleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

     /**
     * Test case: Positive scenario where schedules are retrieved successfully.
     * Expected: The method should return the "Schedules" view and populate the model with schedules.
     */
    @Test
    void getSchedule_ShouldReturnSchedulesView_WithSchedules() {
        // Arrange
        List<ScheduleEntity> schedules = new ArrayList<>();
        schedules.add(new ScheduleEntity()); // Add a sample schedule
        when(scheduleService.getSchedules()).thenReturn(schedules);

        // Act
        String viewName = scheduleController.getSchedule(model);

        // Assert
        assertEquals("Schedules", viewName, "The view name should be 'Schedules'");
        verify(model).addAttribute("schedules", schedules);
        verify(scheduleService, times(1)).getSchedules();
    }

    /**
     * Test case: Negative scenario where no schedules are available.
     * Expected: The method should return the "Schedules" view with an empty list of schedules.
     */
    @Test
    void getSchedule_NoSchedules_ShouldReturnEmptyList() {
        // Arrange
        List<ScheduleEntity> emptySchedules = new ArrayList<>();
        when(scheduleService.getSchedules()).thenReturn(emptySchedules);

        // Act
        String viewName = scheduleController.getSchedule(model);

        // Assert
        assertEquals("Schedules", viewName, "The view name should be 'Schedules'");
        verify(model).addAttribute("schedules", emptySchedules);
        verify(scheduleService, times(1)).getSchedules();
    }

    /**
     * Test case: Negative scenario where the service throws an exception.
     * Expected: The method should propagate the exception.
     */
    @Test
    void getSchedule_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        when(scheduleService.getSchedules()).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            scheduleController.getSchedule(model)
        );
        assertEquals("Service error", exception.getMessage(), "The exception message should match");
        verify(scheduleService, times(1)).getSchedules();
    }


    /**
     * Test case: Positive scenario where the schedule form is created successfully.
     * Expected: The method should return the "AddSchedule" view and populate the model with a new ScheduleEntity.
     */
    @Test
    void createScheduleForm_ShouldReturnAddScheduleView_WithNewScheduleEntity() {
        // Arrange
        ScheduleEntity schedule = new ScheduleEntity();

        // Act
        String viewName = scheduleController.createScheduleForm(model);

        // Assert
        assertEquals("AddSchedule", viewName, "The view name should be 'AddSchedule'");
        verify(model).addAttribute("schedule", schedule);
    }


    /**
     * Test case: Positive scenario where the schedule is retrieved successfully by ID.
     * Expected: The method should return the "EditSchedule" view and populate the model with the schedule.
     */
    @Test
    void editScheduleForm_ShouldReturnEditScheduleView_WithSchedule() {
        // Arrange
        Long id = 1L;
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setId(id);
        schedule.setName("Sample Schedule");
        when(scheduleService.getById(id)).thenReturn(schedule);

        // Act
        String viewName = scheduleController.editScheduleForm(id, model);

        // Assert
        assertEquals("EditSchedule", viewName, "The view name should be 'EditSchedule'");
        verify(model).addAttribute("schedule", schedule);
        verify(scheduleService, times(1)).getById(id);
    }

     /**
     * Test case: Negative scenario where the schedule ID does not exist.
     * Expected: The method should throw a RuntimeException.
     */
    @Test
    void editScheduleForm_ScheduleIdDoesNotExist_ShouldThrowException() {
        // Arrange
        Long id = 1L;
        when(scheduleService.getById(id)).thenThrow(new RuntimeException("Schedule not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            scheduleController.editScheduleForm(id, model)
        );
        assertEquals("Schedule not found", exception.getMessage(), "The exception message should match");
        verify(scheduleService, times(1)).getById(id);
        verifyNoInteractions(model);
    }

     /**
     * Test case: Negative scenario where the model is null.
     * Expected: The method should throw a NullPointerException.
     */
    @Test
    void editScheduleForm_NullModel_ShouldThrowNullPointerException() {
        // Arrange
        Long id = 1L;
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setId(id);
        when(scheduleService.getById(id)).thenReturn(schedule);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> 
            scheduleController.editScheduleForm(id, null),
            "The method should throw a NullPointerException when the model is null"
        );
        verify(scheduleService, times(1)).getById(id);
    }



     /**
     * Test case: Positive scenario where a schedule is created successfully.
     * Expected: The method should redirect to the "get-schedules" endpoint.
     */
    @Test
    void createSchedule_ShouldRedirectToGetSchedules_WhenSuccessful() {
        // Arrange
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setName("Sample Schedule");
        schedule.setDescription("This is a sample schedule.");
        schedule.setStartDateStr("2025-04-01");
        schedule.setEndDateStr("2025-04-30");

        when(scheduleService.create(schedule)).thenReturn(schedule);

        // Act
        String viewName = scheduleController.createSchedule(schedule);
 // Assert
        assertEquals("redirect:/schedule/get-schedules", viewName, "The method should redirect to the 'get-schedules' endpoint");
        verify(scheduleService, times(1)).create(schedule);
        assertEquals(LocalDate.parse("2025-04-01"), schedule.getStartDate(), "The start date should be set correctly");
        assertEquals(LocalDate.parse("2025-04-30"), schedule.getEndDate(), "The end date should be set correctly");
        assertNotNull(schedule.getCreatedOn(), "The createdOn field should be set");
    }

     /**
     * Test case: Negative scenario where the service throws an exception during schedule creation.
     * Expected: The method should propagate the exception.
     */
    @Test
    void createSchedule_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setName("Sample Schedule");
        schedule.setDescription("This is a sample schedule.");
        schedule.setStartDateStr("2025-04-01");
        schedule.setEndDateStr("2025-04-30");

        when(scheduleService.create(schedule)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            scheduleController.createSchedule(schedule)
        );
        assertEquals("Service error", exception.getMessage(), "The exception message should match");
        verify(scheduleService, times(1)).create(schedule);
    }

    /**
     * Test case: Negative scenario where the input schedule entity is null.
     * Expected: The method should throw a NullPointerException.
     */
    @Test
    void createSchedule_NullSchedule_ShouldThrowNullPointerException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> 
            scheduleController.createSchedule(null),
            "The method should throw a NullPointerException when the input is null"
        );
        verifyNoInteractions(scheduleService);
    }

    /**
     * Test case: Negative scenario where the start date or end date is invalid.
     * Expected: The method should throw a DateTimeParseException.
     */
    @Test
    void createSchedule_InvalidDate_ShouldThrowDateTimeParseException() {
        // Arrange
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setName("Sample Schedule");
        schedule.setDescription("This is a sample schedule.");
        schedule.setStartDateStr("invalid-date");
        schedule.setEndDateStr("invalid-date");

        // Act & Assert
        assertThrows(java.time.format.DateTimeParseException.class, () -> 
            scheduleController.createSchedule(schedule),
            "The method should throw a DateTimeParseException for invalid date strings"
        );
        verifyNoInteractions(scheduleService);
    }




  @Test
    @DisplayName("Should update schedule successfully when valid data is provided")
    void updateSchedule_WhenValidData_ShouldUpdateSuccessfully() {
        // Arrange
        Long scheduleId = 1L;
        ScheduleEntity existingSchedule = new ScheduleEntity();
        existingSchedule.setId(scheduleId);
        existingSchedule.setName("Old Name");
        existingSchedule.setDescription("Old Description");
        existingSchedule.setStartDate(LocalDate.of(2023, 1, 1));
        existingSchedule.setEndDate(LocalDate.of(2023, 1, 31));

        ScheduleEntity updatedSchedule = new ScheduleEntity();
        updatedSchedule.setName("New Name");
        updatedSchedule.setDescription("New Description");
        updatedSchedule.setStartDateStr("2023-02-01");
        updatedSchedule.setEndDateStr("2023-02-28");

        when(scheduleService.getById(scheduleId)).thenReturn(existingSchedule);
        when(scheduleService.create(any(ScheduleEntity.class))).thenReturn(existingSchedule);

    // Act
    String viewName = scheduleController.updateSchedule(scheduleId, updatedSchedule);

    // Assert
    assertEquals("redirect:/schedule/get-schedules", viewName, "Should redirect to the schedules list");
    verify(scheduleService, times(1)).getById(scheduleId);
   // verify(scheduleService, times(1)).create(existingSchedule);

    assertEquals("New Name", existingSchedule.getName());
    assertEquals("New Description", existingSchedule.getDescription());
    assertEquals(LocalDate.of(2023, 2, 1), existingSchedule.getStartDate());
    assertEquals(LocalDate.of(2023, 2, 28), existingSchedule.getEndDate());
}

@Test
@DisplayName("Should handle exception when schedule ID is invalid")
void updateSchedule_WhenInvalidId_ShouldHandleException() {
    // Arrange
    Long invalidScheduleId = 999L;
    ScheduleEntity updatedSchedule = new ScheduleEntity();
    updatedSchedule.setName("New Name");
    updatedSchedule.setDescription("New Description");
    updatedSchedule.setStartDateStr("2023-02-01");
    updatedSchedule.setEndDateStr("2023-02-28");

    when(scheduleService.getById(invalidScheduleId)).thenThrow(new RuntimeException("Schedule not found"));

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        scheduleController.updateSchedule(invalidScheduleId, updatedSchedule);
    });

    assertEquals("Schedule not found", exception.getMessage());
    verify(scheduleService, times(1)).getById(invalidScheduleId);
    verify(scheduleService, never()).create(any(ScheduleEntity.class));
}

/*@Test
    @DisplayName("Should handle null schedule object gracefully")
    void updateSchedule_WhenScheduleIsNull_ShouldHandleGracefully() {
        // Arrange
        Long scheduleId = 1L;

        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            scheduleController.updateSchedule(scheduleId, null);
        });

        assertEquals("Cannot invoke \"ScheduleEntity.getName()\" because \"schedule\" is null", exception.getMessage());
        verify(scheduleService, never()).getById(scheduleId);
        verify(scheduleService, never()).create(any(ScheduleEntity.class));
    }*/
}
