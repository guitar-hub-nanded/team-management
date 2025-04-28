package com.team.AssignTaskScheduleTests;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.team.assignTaskSchedule.AssignTaskScheduleEntity;
import com.team.assignTaskSchedule.AssignTaskScheduleRepository;
import com.team.assignTaskSchedule.AssignTaskScheduleService;
import com.team.assignTaskSchedule.SearchDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for AssignTaskScheduleService
 * Tests the findByDateBetween functionality for task scheduling
 */


public class AssignTaskScheduleServiceTest {

    
    @Mock
    private AssignTaskScheduleRepository assignTaskScheduleRepository;

    @InjectMocks
    private AssignTaskScheduleService assignTaskScheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    

    /**
     * Test case: Positive scenario where task schedules are retrieved successfully.
     * Expected: The method should return a list of task schedules.
     */
    @Test
    void getTaskSchedules_ShouldReturnListOfTaskSchedules() {
        // Arrange
        List<AssignTaskScheduleEntity> taskSchedules = new ArrayList<>();
        taskSchedules.add(new AssignTaskScheduleEntity()); // Add a sample task schedule
        when(assignTaskScheduleRepository.findAll()).thenReturn(taskSchedules);

        // Act
        List<AssignTaskScheduleEntity> result = assignTaskScheduleService.getTaskSchedules();

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(1, result.size(), "The result size should match the expected size");
        verify(assignTaskScheduleRepository, times(1)).findAll();
    }
    
   /**
     * Test case: Negative scenario where no task schedules are available.
     * Expected: The method should return an empty list.
     */
    @Test
    void getTaskSchedules_NoTaskSchedules_ShouldReturnEmptyList() {
        // Arrange
        List<AssignTaskScheduleEntity> emptyTaskSchedules = new ArrayList<>();
        when(assignTaskScheduleRepository.findAll()).thenReturn(emptyTaskSchedules);

        // Act
        List<AssignTaskScheduleEntity> result = assignTaskScheduleService.getTaskSchedules();

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(0, result.size(), "The result size should be zero");
        verify(assignTaskScheduleRepository, times(1)).findAll();
    }
/**
     * Test case: Negative scenario where the repository throws an exception.
     * Expected: The method should propagate the exception.
     */
    @Test
    void getTaskSchedules_RepositoryThrowsException_ShouldPropagateException() {
        // Arrange
        when(assignTaskScheduleRepository.findAll()).thenThrow(new RuntimeException("Repository error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleService.getTaskSchedules()
        );
        assertEquals("Repository error", exception.getMessage(), "The exception message should match");
        verify(assignTaskScheduleRepository, times(1)).findAll();
    }




     /**
     * Test case: Positive scenario where a task schedule is retrieved successfully by ID.
     * Expected: The method should return the task schedule entity.
     */
    @Test
    void getById_ShouldReturnTaskSchedule_WhenIdExists() {
        // Arrange
        Long id = 1L;
        AssignTaskScheduleEntity taskSchedule = new AssignTaskScheduleEntity();
        taskSchedule.setId(id);
        when(assignTaskScheduleRepository.findById(id)).thenReturn(Optional.of(taskSchedule));

        // Act
        AssignTaskScheduleEntity result = assignTaskScheduleService.getById(id);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(id, result.getId(), "The ID of the result should match the expected ID");
        verify(assignTaskScheduleRepository, times(1)).findById(id);
    }

    /**
     * Test case: Negative scenario where no task schedule exists for the given ID.
     * Expected: The method should throw a NoSuchElementException.
     */
    @Test
    void getById_ShouldThrowException_WhenIdDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(assignTaskScheduleRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(java.util.NoSuchElementException.class, () -> assignTaskScheduleService.getById(id),
                "The method should throw a NoSuchElementException when the ID does not exist");
        verify(assignTaskScheduleRepository, times(1)).findById(id);
    }

    /**
     * Test case: Negative scenario where the repository throws an exception.
     * Expected: The method should propagate the exception.
     */
    @Test
    void getById_RepositoryThrowsException_ShouldPropagateException() {
        // Arrange
        Long id = 1L;
        when(assignTaskScheduleRepository.findById(id)).thenThrow(new RuntimeException("Repository error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> assignTaskScheduleService.getById(id));
        assertEquals("Repository error", exception.getMessage(), "The exception message should match");
        verify(assignTaskScheduleRepository, times(1)).findById(id);
    }




    /**
     * Test case: Positive scenario where a task schedule is created successfully.
     * Expected: The method should return the saved task schedule entity.
     */
   /*@Test
    void create_ShouldSaveAndReturnTaskSchedule() {
        // Arrange
        AssignTaskScheduleEntity assignTaskSchedule = new AssignTaskScheduleEntity();
        assignTaskSchedule.setTaskName("Sample Task");
        when(assignTaskScheduleRepository.save(assignTaskSchedule)).thenReturn(assignTaskSchedule);

        // Act
        AssignTaskScheduleEntity result = assignTaskScheduleService.create(assignTaskSchedule);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals("Sample Task", result.getTaskName(), "The task name should match the expected value");
        verify(assignTaskScheduleRepository, times(1)).save(assignTaskSchedule);
    } */ 

    /**
     * Test case: Negative scenario where the repository throws an exception during save.
     * Expected: The method should propagate the exception.
     */
    @Test
    void create_RepositoryThrowsException_ShouldPropagateException() {
        // Arrange
        AssignTaskScheduleEntity assignTaskSchedule = new AssignTaskScheduleEntity();
        when(assignTaskScheduleRepository.save(assignTaskSchedule)).thenThrow(new RuntimeException("Repository error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleService.create(assignTaskSchedule)
        );
        assertEquals("Repository error", exception.getMessage(), "The exception message should match");
        verify(assignTaskScheduleRepository, times(1)).save(assignTaskSchedule);
    }

    /**
     * Test case: Negative scenario where the input task schedule is null.
     * Expected: The method should throw a NullPointerException.
     */
  /*  @Test
    void create_NullTaskSchedule_ShouldThrowNullPointerException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> 
            assignTaskScheduleService.create(null),
            "The method should throw a NullPointerException when the input is null"
        );
        verifyNoInteractions(assignTaskScheduleRepository);
    } */




     /**
     * Test case: Positive scenario where task schedules are retrieved successfully within the date range.
     * Expected: The method should return a list of task schedules within the specified date range.
     */
    @Test
    void findByDateBetween_ShouldReturnTaskSchedulesWithinDateRange() {
        // Arrange
        SearchDto searchDto = new SearchDto();
        searchDto.setStartDateStr("2025-04-01");
        searchDto.setEndDateStr("2025-04-30");

        LocalDate startDate = LocalDate.parse("2025-04-01");
        LocalDate endDate = LocalDate.parse("2025-04-30");

        List<AssignTaskScheduleEntity> taskSchedules = new ArrayList<>();
        taskSchedules.add(new AssignTaskScheduleEntity()); // Add a sample task schedule

        when(assignTaskScheduleRepository.findByStartDateBetween(startDate, endDate)).thenReturn(taskSchedules);
// Act
List<AssignTaskScheduleEntity> result = assignTaskScheduleService.findByDateBetween(searchDto);

// Assert
assertNotNull(result, "The result should not be null");
assertEquals(1, result.size(), "The result size should match the expected size");
verify(assignTaskScheduleRepository, times(1)).findByStartDateBetween(startDate, endDate);
}
    

 /**
     * Test case: Negative scenario where no task schedules match the date range.
     * Expected: The method should return an empty list.
     */
    @Test
    void findByDateBetween_NoMatchingTaskSchedules_ShouldReturnEmptyList() {
        // Arrange
        SearchDto searchDto = new SearchDto();
        searchDto.setStartDateStr("2025-04-01");
        searchDto.setEndDateStr("2025-04-30");

        LocalDate startDate = LocalDate.parse("2025-04-01");
        LocalDate endDate = LocalDate.parse("2025-04-30");

        List<AssignTaskScheduleEntity> emptyTaskSchedules = new ArrayList<>();

        when(assignTaskScheduleRepository.findByStartDateBetween(startDate, endDate)).thenReturn(emptyTaskSchedules);
  // Act
  List<AssignTaskScheduleEntity> result = assignTaskScheduleService.findByDateBetween(searchDto);

  // Assert
  assertNotNull(result, "The result should not be null");
  assertEquals(0, result.size(), "The result size should be zero");
  verify(assignTaskScheduleRepository, times(1)).findByStartDateBetween(startDate, endDate);
}
/**
     * Test case: Negative scenario where the search DTO contains invalid date strings.
     * Expected: The method should throw a DateTimeParseException.
     */
    @Test
    void findByDateBetween_InvalidDateStrings_ShouldThrowDateTimeParseException() {
        // Arrange
        SearchDto searchDto = new SearchDto();
        searchDto.setStartDateStr("invalid-date");
        searchDto.setEndDateStr("invalid-date");

        // Act & Assert
        assertThrows(java.time.format.DateTimeParseException.class, () -> 
            assignTaskScheduleService.findByDateBetween(searchDto),
            "The method should throw a DateTimeParseException for invalid date strings"
        );
        verifyNoInteractions(assignTaskScheduleRepository);
    }

     /**
     * Test case: Negative scenario where the start date or end date is null.
     * Expected: The method should return all task schedules.
     */
   /*  @Test
    void findByDateBetween_NullDates_ShouldReturnAllTaskSchedules() {
        // Arrange
        SearchDto searchDto = new SearchDto();
        searchDto.setStartDateStr(null);
        searchDto.setEndDateStr(null);

        List<AssignTaskScheduleEntity> allTaskSchedules = new ArrayList<>();
        allTaskSchedules.add(new AssignTaskScheduleEntity()); // Add a sample task schedule

        when(assignTaskScheduleRepository.findAll()).thenReturn(allTaskSchedules);
// Act
List<AssignTaskScheduleEntity> result = assignTaskScheduleService.findByDateBetween(searchDto);

// Assert
assertNotNull(result, "The result should not be null");
assertEquals(1, result.size(), "The result size should match the expected size");
verify(assignTaskScheduleRepository, times(1)).findAll();
} */

  /**
     * Test case: Negative scenario where the repository throws an exception.
     * Expected: The method should propagate the exception.
     */
    @Test
    void findByDateBetween_RepositoryThrowsException_ShouldPropagateException() {
        // Arrange
        SearchDto searchDto = new SearchDto();
        searchDto.setStartDateStr("2025-04-01");
        searchDto.setEndDateStr("2025-04-30");

        LocalDate startDate = LocalDate.parse("2025-04-01");
        LocalDate endDate = LocalDate.parse("2025-04-30");

        when(assignTaskScheduleRepository.findByStartDateBetween(startDate, endDate))
            .thenThrow(new RuntimeException("Repository error"));
  // Act & Assert
  RuntimeException exception = assertThrows(RuntimeException.class, () -> 
  assignTaskScheduleService.findByDateBetween(searchDto)
);
assertEquals("Repository error", exception.getMessage(), "The exception message should match");
verify(assignTaskScheduleRepository, times(1)).findByStartDateBetween(startDate, endDate);
}


 /**
     * Test case: Positive scenario where a task schedule is created successfully.
     * Expected: The method should return the saved task schedule entity.
     */
    @Test
    void create_ShouldSaveAndReturnTaskSchedule() {
        // Arrange
        AssignTaskScheduleEntity assignTaskSchedule = new AssignTaskScheduleEntity();
       // assignTaskSchedule.setTaskName("Sample Task");
        assignTaskSchedule.setStartDate(LocalDate.of(2025, 4, 1));
        assignTaskSchedule.setEndDate(LocalDate.of(2025, 4, 30));

        when(assignTaskScheduleRepository.save(assignTaskSchedule)).thenReturn(assignTaskSchedule);

        // Act
        AssignTaskScheduleEntity result = assignTaskScheduleService.create(assignTaskSchedule);

        // Assert
        assertNotNull(result, "The result should not be null");
        //assertEquals("Sample Task", result.getTaskName(), "The task name should match the expected value");
        verify(assignTaskScheduleRepository, times(1)).save(assignTaskSchedule);
    }

    
    
}
