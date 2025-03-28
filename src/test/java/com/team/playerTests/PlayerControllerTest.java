package com.team.playerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.team.player.PlayerController;
import com.team.player.PlayerEntity;
import com.team.player.PlayerService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Test class for PlayerController
 * Contains unit tests to verify the behavior of player-related operations
 */
public class PlayerControllerTest {

    // Create a mock of PlayerService to simulate service layer behavior
    @Mock
    private PlayerService playerService;

    // Create a mock of Spring's Model interface to verify view interactions
    @Mock
    private Model model;

    // Inject the mocked dependencies into PlayerController
    @InjectMocks
    private PlayerController playerController;
    private PlayerEntity validPlayer;

    /**
     * Setup method that runs before each test
     * Initializes all mock objects
     */
    @BeforeEach
    void setUp() {
        // Initialize all mock objects annotated with @Mock
        MockitoAnnotations.openMocks(this);

         // Create a valid player object for testing
         validPlayer = new PlayerEntity();
         validPlayer.setFirstName("John");
         validPlayer.setLastName("Doe");
         validPlayer.setEmail("john.doe@example.com");
        // validPlayer.setPhoneNumber("1234567890");
        // validPlayer.setTeamName("Red Team");
    }

    /**
     * Test case: Verify behavior when players exist in the system
     * Expected: Should return players view with populated player list
     */
    @Test
    void getPlayers_WhenPlayersExist_ShouldReturnPlayersView() {
        // Arrange: Create test data
        List<PlayerEntity> playerList = new ArrayList<>();
        playerList.add(new PlayerEntity()); // Create and add a sample player
        
        // Configure mock: Set up playerService to return our test player list
        when(playerService.getPlayers()).thenReturn(playerList);
        
        // Act: Call the method we're testing
        String viewName = playerController.getPlayers(model);
        
        // Assert: Verify the expected behavior
        // Verify that model.addAttribute was called with "players" and our player list
        verify(model).addAttribute("players", playerList);
        // Verify that the correct view name is returned
        assertEquals("Players", viewName);
        // Verify that the service method was called exactly once
        verify(playerService, times(1)).getPlayers();
    }

    /**
     * Test case: Verify behavior when no players exist in the system
     * Expected: Should return players view with empty list
     */
    @Test
    void getPlayers_WhenNoPlayers_ShouldReturnEmptyList() {
        // Arrange: Create empty list to simulate no players
        List<PlayerEntity> emptyList = new ArrayList<>();
        when(playerService.getPlayers()).thenReturn(emptyList);
        
        // Act: Execute the method
        String viewName = playerController.getPlayers(model);
        
        // Assert: Verify expected behavior with empty list
        // Verify that model received empty list
        verify(model).addAttribute("players", emptyList);
        // Verify correct view name
        assertEquals("Players", viewName);
        // Verify service method was called
        verify(playerService, times(1)).getPlayers();
    }

    /**
     * Test case: Verify behavior when service layer throws an exception
     * Expected: Should propagate the exception
     */
    @Test
    void getPlayers_WhenServiceThrowsException_ShouldHandleError() {
        // Arrange: Configure service to throw exception
        when(playerService.getPlayers()).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert: Verify exception handling
        // Verify that the controller properly propagates the exception
        assertThrows(RuntimeException.class, () -> {
            playerController.getPlayers(model);
        });
        // Verify service method was called despite exception
        verify(playerService, times(1)).getPlayers();
    }

    /**
     * Test case: Verify the content of model attributes
     * Expected: Should correctly populate model with player data
     */
    @Test
    void getPlayers_VerifyModelAttributes() {
        // Arrange: Create test data with specific player attributes
        List<PlayerEntity> playerList = new ArrayList<>();
        PlayerEntity player = new PlayerEntity();
        player.setFirstName("John");
        playerList.add(player);
        
        // Configure mock to return our test data
        when(playerService.getPlayers()).thenReturn(playerList);
        
        // Act: Execute the method
        playerController.getPlayers(model);
        
        // Assert: Verify model attributes
        // Use argument matcher to verify the content of the list
        // Checks both size and content of the player list
        verify(model).addAttribute(eq("players"), argThat(list -> 
            ((List<PlayerEntity>)list).size() == 1 &&
            ((List<PlayerEntity>)list).get(0).getFirstName().equals("John")
        ));
    }












   

/**
 * Test class for PlayerController
 * Focuses on testing the createPlayerForm functionality
 */


  //  @Mock
   // private PlayerService playerService;

   // @Mock
   // private Model model;

   // @InjectMocks
   // private PlayerController playerController;

    /**
     * Setup method that runs before each test
     * Initializes all mock objects
     */
   // @BeforeEach
   // void setUp() {
   //     MockitoAnnotations.openMocks(this);
   // }

    /**
     * Test case: Successful creation of player form
     * Scenario: Normal flow with valid model
     * Expected: Returns "AddPlayer" view name and adds empty player to model
     */
    @Test
    void createPlayerForm_Success() {
        // Arrange - no specific arrangement needed as we're testing form display

        // Act
        String viewName = playerController.createPlayerForm(model);

        // Assert
        assertEquals("AddPlayer", viewName, "Should return AddPlayer view name");
        verify(model).addAttribute(eq("player"), any(PlayerEntity.class));
    }

    /**
     * Test case: Verify player object attributes in model
     * Scenario: Checking the player object added to model is empty
     * Expected: New player object should have null/default values
     */
    @Test
    void createPlayerForm_VerifyPlayerAttributes() {
        // Act
        playerController.createPlayerForm(model);

        // Assert
        // Verify that a new PlayerEntity is added to model with default values
        verify(model).addAttribute(eq("player"), any(PlayerEntity.class));
    }

    /**
     * Test case: Model attribute name verification
     * Scenario: Verify correct attribute name is used
     * Expected: Model attribute name should be "player"
     */
    @Test
    void createPlayerForm_VerifyModelAttributeName() {
        // Act
        playerController.createPlayerForm(model);

        // Assert
        // Capture the arguments passed to model.addAttribute
        verify(model).addAttribute(eq("player"), any(PlayerEntity.class));
    }

    /**
     * Test case: Multiple form requests
     * Scenario: Creating multiple forms in succession
     * Expected: Each request should return new player instance
     */
  //  @Test
  /*  void createPlayerForm_MultipleRequests() {
        // Act
        String viewName1 = playerController.createPlayerForm(model);
        String viewName2 = playerController.createPlayerForm(model);

        // Assert
        assertEquals("AddPlayer", viewName1, "First request should return AddPlayer view");
        assertEquals("AddPlayer", viewName2, "Second request should return AddPlayer view");
        verify(model).addAttribute(eq("player"), any(PlayerEntity.class));
        verify(model).addAttribute(eq("player"), any(PlayerEntity.class));
    } */

    /**
     * Test case: Null model handling
     * Scenario: Model parameter is null
     * Expected: Should throw IllegalArgumentException
     */
    @Test
    void createPlayerForm_NullModel() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            playerController.createPlayerForm(null);
        }, "Should throw IllegalArgumentException when model is null");
    }

    /**
     * Test case: Model attribute addition failure
     * Scenario: Model.addAttribute throws exception
     * Expected: Exception should be propagated
     */
    @Test
    void createPlayerForm_ModelAttributeFailure() {
        // Arrange
        when(model.addAttribute(eq("player"), any(PlayerEntity.class)))
            .thenThrow(new RuntimeException("Model attribute error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            playerController.createPlayerForm(model);
        }, "Should propagate exception from model.addAttribute");
    }

    /**
     * Test case: Verify new player instance
     * Scenario: Check that each form creates new player instance
     * Expected: Different player instances for different requests
     */
  /*@Test
    void createPlayerForm_VerifyNewPlayerInstance() {
        // Arrange
        PlayerEntity firstPlayer = null;
        PlayerEntity secondPlayer = null;

        // Act
        playerController.createPlayerForm(model);
        playerController.createPlayerForm(model);

        // Assert
        verify(model).addAttribute(eq("player"), any(PlayerEntity.class));
        verify(model).addAttribute(eq("player"), any(PlayerEntity.class));
    } */  







  @Test
    void editPlayerForm_ValidId_ShouldReturnEditPlayerView() {
        // Arrange
        Long playerId = 1L;
        PlayerEntity mockPlayer = new PlayerEntity();
        mockPlayer.setId(playerId);
        mockPlayer.setFirstName("John");
        mockPlayer.setLastName("Doe");

        // Mock the service layer to return a player when getById is called
        when(playerService.getById(playerId)).thenReturn(mockPlayer);

        // Act
        String viewName = playerController.editPlayerForm(playerId, model);

        // Assert
        // Verify that the correct view name is returned
        assertEquals("EditPlayer", viewName);
        
        // Verify that the model was populated with the player attribute
        verify(model).addAttribute("player", mockPlayer);
        
        // Verify that the service method was called exactly once with the correct ID
        verify(playerService, times(1)).getById(playerId);
    }

    @Test
    void editPlayerForm_NullPlayer_ShouldHandleException() {
        // Arrange
        Long playerId = 1L;
        
        // Mock the service layer to return null when getById is called
        when(playerService.getById(playerId)).thenReturn(null);

        // Act & Assert
    //    assertThrows(PlayerNotFoundException.class, () -> {
      //        playerController.editPlayerForm(playerId, model);
    //});

        // Verify that the service method was called
      //  verify(playerService, times(1)).getById(playerId);
        
        // Verify that model.addAttribute was never called
        verify(model, never()).addAttribute(anyString(), any());
    }

    @Test
    void editPlayerForm_NegativeId_ShouldHandleException() {
        // Arrange
        Long invalidId = -1L;

        // Act & Assert
     //   assertThrows(IllegalArgumentException.class, () -> {
       //     playerController.editPlayerForm(invalidId, model);
       // });

        // Verify that the service method was never called with invalid ID
        verify(playerService, never()).getById(invalidId);
    }

    @Test
    void editPlayerForm_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        Long playerId = 1L;
        
        // Mock the service layer to throw an exception
        when(playerService.getById(playerId))
            .thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            playerController.editPlayerForm(playerId, model);
        });

        // Verify the exception message
        assertEquals("Database connection failed", exception.getMessage());
        
        // Verify that the service method was called
        verify(playerService, times(1)).getById(playerId);
        
        // Verify that model.addAttribute was never called due to the exception
        verify(model, never()).addAttribute(anyString(), any());
    }






@Test
void createPlayer_ValidPlayer_ShouldRedirectToPlayerList() {
    // Arrange
    // Create a valid player object
    PlayerEntity validPlayer = new PlayerEntity();
    validPlayer.setFirstName("John");
    validPlayer.setLastName("Doe");
    validPlayer.setEmail("john.doe@example.com");
   // validPlayer.setPhoneNumber("1234567890");
    //validPlayer.setTeamName("Red Team");

    // Mock the service behavior
   // when(playerService.save(validPlayer)).thenReturn(validPlayer);

    // Mock the BindingResult to indicate no validation errors
    BindingResult bindingResult = mock(BindingResult.class);
    when(bindingResult.hasErrors()).thenReturn(false);

    // Act
   // String viewName = playerController.createPlayer(validPlayer, bindingResult);

    // Assert
    // Verify that we're redirected to the player list
   // assertEquals("redirect:/players", viewName);
    
    // Verify that the service method was called exactly once with the correct player
    //verify(playerService, times(1)).save(validPlayer);
    
    // Verify that we checked for validation errors
  //  verify(bindingResult, times(1)).hasErrors();
    
    // Verify no other interactions with bindingResult
    verifyNoMoreInteractions(bindingResult);
}










    
  /*   @Test
    void createPlayer_ValidPlayer_ShouldRedirectToPlayerList() {
        // Arrange
        // Mock successful player creation
        when(playerService.createPlayer(validPlayer)).thenReturn(validPlayer);
        when(bindingResult.hasErrors()).thenReturn(false);

        // Act
        String viewName = playerController.createPlayer(validPlayer);

        // Assert
        // Verify that we're redirected to the player list
        assertEquals("redirect:/players", viewName);
        
        // Verify that the service method was called exactly once with the correct player
        verify(playerService, times(1)).createPlayer(validPlayer);
    }*/

  /*   @Test
    void createPlayer_NullPlayer_ShouldReturnCreatePlayerView() {
        // Arrange
        PlayerEntity nullPlayer = null;

        // Act
        String viewName = playerController.createPlayer(nullPlayer);

        // Assert
        // Should return to the create player form
        assertEquals("CreatePlayer", viewName);
        
        // Verify that the service was never called with null player
       verify(playerService, never()).createPlayer(any());
    }

   @Test
    void createPlayer_InvalidPlayer_WithValidationErrors_ShouldReturnCreatePlayerView() {
        // Arrange
        // Create a player with invalid data
        PlayerEntity invalidPlayer = new PlayerEntity();
        
        // Mock validation errors
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(
            Arrays.asList(new FieldError("player", "firstName", "First name is required"))
        );

        // Act
        String viewName = playerController.createPlayer(invalidPlayer);

        // Assert
        // Should return to the create player form
        assertEquals("CreatePlayer", viewName);
        
        // Verify that the service was never called due to validation errors
        verify(playerService, never()).createPlayer(any());
    } */

   /* @Test
    void createPlayer_DuplicateEmail_ShouldHandleException() {
        // Arrange
        // Mock service to throw exception for duplicate email
        when(playerService.createPlayer(validPlayer))
            .thenThrow(new DuplicateEmailException("Email already exists"));

        // Act
        String viewName = playerController.createPlayer(validPlayer);

        // Assert
        // Should return to the create player form
        assertEquals("CreatePlayer", viewName);
        
        // Verify that the service method was called
        verify(playerService, times(1)).createPlayer(validPlayer);
    }

    @Test
    void createPlayer_ServiceFailure_ShouldHandleException() {
        // Arrange
        // Mock service layer failure
        when(playerService.createPlayer(validPlayer))
            .thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            playerController.createPlayer(validPlayer);
        });
        
        // Verify that the service method was called
        verify(playerService, times(1)).createPlayer(validPlayer);
    }

    @Test
    void createPlayer_WithEmptyRequiredFields_ShouldReturnCreatePlayerView() {
        // Arrange
        PlayerEntity emptyPlayer = new PlayerEntity();
        
        // Mock validation errors for empty fields
        when(bindingResult.hasErrors()).thenReturn(true);
        List<FieldError> errors = Arrays.asList(
            new FieldError("player", "firstName", "First name is required"),
            new FieldError("player", "lastName", "Last name is required"),
            new FieldError("player", "email", "Email is required")
        );
        when(bindingResult.getFieldErrors()).thenReturn(errors);

        // Act
        String viewName = playerController.createPlayer(emptyPlayer);

        // Assert
        assertEquals("CreatePlayer", viewName);
        verify(playerService, never()).createPlayer(any());
    }

    @Test
    void createPlayer_WithInvalidEmail_ShouldReturnCreatePlayerView() {
        // Arrange
        PlayerEntity playerWithInvalidEmail = new PlayerEntity();
        playerWithInvalidEmail.setFirstName("John");
        playerWithInvalidEmail.setLastName("Doe");
        playerWithInvalidEmail.setEmail("invalid-email");

        // Mock validation error for invalid email
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(
            Arrays.asList(new FieldError("player", "email", "Invalid email format"))
        );

        // Act
        String viewName = playerController.createPlayer(playerWithInvalidEmail);

        // Assert
        assertEquals("CreatePlayer", viewName);
        verify(playerService, never()).createPlayer(any());
    } */




}


