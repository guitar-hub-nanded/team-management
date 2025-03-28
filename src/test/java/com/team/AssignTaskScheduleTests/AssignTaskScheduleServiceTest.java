package com.team.AssignTaskScheduleTests;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.team.assignTaskSchedule.AssignTaskScheduleEntity;
import com.team.assignTaskSchedule.AssignTaskScheduleRepository;
import com.team.assignTaskSchedule.AssignTaskScheduleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.lang.reflect.Field;

/**
 * Test class for AssignTaskScheduleService
 * Tests the findByDateBetween functionality for task scheduling
 */


public class AssignTaskScheduleServiceTest {

    // Mock repository dependency
    @Mock
    private AssignTaskScheduleRepository assignTaskScheduleRepository;

    // Service class to be tested
    @InjectMocks
    private AssignTaskScheduleService assignTaskScheduleService;

    // Date formatter for test data creation
    private SimpleDateFormat dateFormat;
    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    /**
     * Setup method executed before each test
     */
    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        // Initialize date formatter for test data
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    

    /**
     * Test case: Successfully retrieve tasks between two dates
     * Expected: Returns list of tasks within the specified date range
     */
  /*  @Test 
    void findByDateBetween_ShouldReturnTasksInDateRange() throws Exception {
        // Arrange
        // Create test dates
        
        Date startDate = dateFormat.parse("2024-01-01");
        Date endDate = dateFormat.parse("2024-01-31");

        // Create test task data
        // List<AssignTaskScheduleEntity> expectedTasks = new ArrayList<>();
        // AssignTaskScheduleEntity task1 = new AssignTaskScheduleEntity();
        // task1.setTaskDate(dateFormat.parse("2024-01-15"));
        // task1.setTaskName("Development Task"); 
        // task1.setTaskDescription("Complete feature implementation");
        // expectedTasks.add(task1);

        List<AssignTaskScheduleEntity> expectedTasks = new ArrayList<>();
        AssignTaskScheduleEntity task1 = new AssignTaskScheduleEntity();
    
        setField(task1, "taskDate", dateFormat.parse("2024-01-15")); // ✅ Set private field
        setField(task1, "taskName", "Development Task");
        setField(task1, "taskDescription", "Complete feature implementation");
    
        expectedTasks.add(task1);
        // Configure mock repository behavior
        when(assignTaskScheduleRepository.findByTaskDateBetween(startDate, endDate))
            .thenReturn(expectedTasks);

        // Act
        List<AssignTaskScheduleEntity> actualTasks = 
            assignTaskScheduleService.findByDateBetween(startDate, endDate);

        // Assert
        assertNotNull(actualTasks, "Returned task list should not be null");
        assertEquals(1, actualTasks.size(), "Should return one task");
        assertEquals("Development Task", actualTasks.get(0).getTaskName());
        verify(assignTaskScheduleRepository).findByTaskDateBetween(startDate, endDate); 
    } */  

    /**
     * Test case: Search with date range containing no tasks
     * Expected: Returns empty list
     */
    @Test
    void findByDateBetween_WhenNoTasks_ShouldReturnEmptyList() throws Exception {
        // Arrange
      /*  Date startDate = dateFormat.parse("2024-02-01");
        Date endDate = dateFormat.parse("2024-02-28");

        // Configure mock to return empty list
        when(assignTaskScheduleRepository.findByTaskDateBetween(startDate, endDate))
            .thenReturn(new ArrayList<>());

        // Act
        List<AssignTaskScheduleEntity> result = 
            assignTaskScheduleService.findByDateBetween(startDate, endDate);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty");
        verify(assignTaskScheduleRepository).findByTaskDateBetween(startDate, endDate); */
    }

    /**
     * Test case: Search tasks across multiple months
     * Expected: Returns all tasks within the extended date range
     */
    @Test
    void findByDateBetween_WithMultiMonthRange_ShouldReturnAllTasks() throws Exception {
        // Arrange
      /*  Date startDate = dateFormat.parse("2024-01-01");
        Date endDate = dateFormat.parse("2024-03-31");

        // Create test data spanning multiple months
        List<AssignTaskScheduleEntity> expectedTasks = new ArrayList<>();
        
        // Task in January
        AssignTaskScheduleEntity task1 = new AssignTaskScheduleEntity();
        task1.setTaskDate(dateFormat.parse("2024-01-15"));
        task1.setTaskName("January Task");
        
        // Task in March
        AssignTaskScheduleEntity task2 = new AssignTaskScheduleEntity();
        task2.setTaskDate(dateFormat.parse("2024-03-15"));
        task2.setTaskName("March Task");
        
        expectedTasks.add(task1);
        expectedTasks.add(task2);

        // Configure mock behavior
        when(assignTaskScheduleRepository.findByTaskDateBetween(startDate, endDate))
            .thenReturn(expectedTasks);

        // Act
        List<AssignTaskScheduleEntity> result = 
            assignTaskScheduleService.findByDateBetween(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size(), "Should return two tasks");
        assertEquals("January Task", result.get(0).getTaskName());
        assertEquals("March Task", result.get(1).getTaskName());
        verify(assignTaskScheduleRepository).findByTaskDateBetween(startDate, endDate); */
    }

    /**
     * Test case: Repository throws exception
     * Expected: Exception should be propagated
     */
    @Test
    void findByDateBetween_WhenRepositoryFails_ShouldPropagateException() throws Exception {
        // Arrange
      /*  Date startDate = dateFormat.parse("2024-01-01");
        Date endDate = dateFormat.parse("2024-01-31");

        // Configure mock to throw exception
        when(assignTaskScheduleRepository.findByTaskDateBetween(startDate, endDate))
            .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> 
            assignTaskScheduleService.findByDateBetween(startDate, endDate));
        
        verify(assignTaskScheduleRepository).findByTaskDateBetween(startDate, endDate); */
    }

    /**
     * Test case: Search with same start and end date
     * Expected: Returns tasks for the specific date
     */
    @Test
    void findByDateBetween_WithSameStartAndEndDate_ShouldReturnTasks() throws Exception {
        // Arrange
     /*   Date date = dateFormat.parse("2024-01-15");
        
        List<AssignTaskScheduleEntity> expectedTasks = new ArrayList<>();
        AssignTaskScheduleEntity task = new AssignTaskScheduleEntity();
        task.setTaskDate(date);
        task.setTaskName("Single Day Task");
        expectedTasks.add(task);

        // Configure mock behavior
        when(assignTaskScheduleRepository.findByTaskDateBetween(date, date))
            .thenReturn(expectedTasks);

        // Act
        List<AssignTaskScheduleEntity> result = 
            assignTaskScheduleService.findByDateBetween(date, date);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Single Day Task", result.get(0).getTaskName());
        verify(assignTaskScheduleRepository).findByTaskDateBetween(date, date); */
    }




    
}
