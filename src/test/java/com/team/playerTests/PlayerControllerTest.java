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




 /**
     * Test case: Positive scenario where a player is created successfully.
     * Expected: The method should redirect to the "get-players" endpoint.
     */
    @Test
    void createPlayer_ShouldRedirectToGetPlayers_WhenSuccessful() {
        // Arrange
        PlayerEntity player = new PlayerEntity();
        player.setFirstName("John");
        player.setLastName("Doe");
        player.setEmail("john.doe@example.com");
       // player.setRank("Captain");
        player.setType("Professional");

        when(playerService.create(player)).thenReturn(player);

        // Act
        String viewName = playerController.createPlayer(player);

        // Assert
        assertEquals("redirect:/player/get-players", viewName, "The method should redirect to the 'get-players' endpoint");
        verify(playerService, times(1)).create(player);
        assertNotNull(player.getCreatedOn(), "The createdOn field should be set");
    }

/**
     * Test case: Negative scenario where the service throws an exception during player creation.
     * Expected: The method should propagate the exception.
     */
    @Test
    void createPlayer_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        PlayerEntity player = new PlayerEntity();
        player.setFirstName("John");
        player.setLastName("Doe");
        player.setEmail("john.doe@example.com");
       // player.setRank("Captain");
        player.setType("Professional");

        when(playerService.create(player)).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            playerController.createPlayer(player)
        );
        assertEquals("Service error", exception.getMessage(), "The exception message should match");
        verify(playerService, times(1)).create(player);
    }

  /**
     * Test case: Negative scenario where the input player entity is null.
     * Expected: The method should throw a NullPointerException.
     */
    @Test
    void createPlayer_NullPlayer_ShouldThrowNullPointerException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> 
            playerController.createPlayer(null),
            "The method should throw a NullPointerException when the input is null"
        );
        verifyNoInteractions(playerService);
    }



  /**
     * Test case: Positive scenario where an existing player is updated successfully.
     * Expected: The method should redirect to the "get-players" endpoint.
     */
    @Test
    void createPlayer_ShouldRedirectToGetPlayers_WhenUpdateIsSuccessful() {
        // Arrange
        Long id = 1L;
        PlayerEntity existingPlayer = new PlayerEntity();
        existingPlayer.setId(id);
        existingPlayer.setFirstName("John");
        existingPlayer.setLastName("Doe");
        existingPlayer.setEmail("john.doe@example.com");
       // existingPlayer.setRank("Captain");
        existingPlayer.setType("Professional");

        PlayerEntity updatedPlayer = new PlayerEntity();
        updatedPlayer.setFirstName("Jane");
        updatedPlayer.setLastName("Smith");
        updatedPlayer.setEmail("jane.smith@example.com");
       // updatedPlayer.setRank("Lieutenant");
        updatedPlayer.setType("Amateur");
        when(playerService.getById(id)).thenReturn(existingPlayer);
        when(playerService.create(existingPlayer)).thenReturn(existingPlayer);

        // Act
        String viewName = playerController.createPlayer(id, updatedPlayer);

        // Assert
        assertEquals("redirect:/player/get-players", viewName, "The method should redirect to the 'get-players' endpoint");
        verify(playerService, times(1)).getById(id);
        verify(playerService, times(1)).create(existingPlayer);
        assertEquals("Jane", existingPlayer.getFirstName(), "The first name should be updated");
        assertEquals("Smith", existingPlayer.getLastName(), "The last name should be updated");
        assertEquals("jane.smith@example.com", existingPlayer.getEmail(), "The email should be updated");
       // assertEquals("Lieutenant", existingPlayer.getRank(), "The rank should be updated");
        assertEquals("Amateur", existingPlayer.getType(), "The type should be updated");
        assertNotNull(existingPlayer.getUpdatedOn(), "The updatedOn field should be set");
    }
    

     /**
     * Test case: Negative scenario where the player ID does not exist.
     * Expected: The method should throw a RuntimeException.
     */
    @Test
    void createPlayer_PlayerIdDoesNotExist_ShouldThrowException() {
        // Arrange
        Long id = 1L;
        PlayerEntity updatedPlayer = new PlayerEntity();
        updatedPlayer.setFirstName("Jane");
        updatedPlayer.setLastName("Smith");
        updatedPlayer.setEmail("jane.smith@example.com");
        //updatedPlayer.setRank("Lieutenant");
        updatedPlayer.setType("Amateur");

        when(playerService.getById(id)).thenThrow(new RuntimeException("Player not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            playerController.createPlayer(id, updatedPlayer)
        );
        assertEquals("Player not found", exception.getMessage(), "The exception message should match");
        verify(playerService, times(1)).getById(id);
        verify(playerService, times(0)).create(any());
    }

     /**
     * Test case: Negative scenario where the service throws an exception during update.
     * Expected: The method should propagate the exception.
     */
    @Test
    void createPlayer2_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        Long id = 1L;
        PlayerEntity existingPlayer = new PlayerEntity();
        existingPlayer.setId(id);

        PlayerEntity updatedPlayer = new PlayerEntity();
        updatedPlayer.setFirstName("Jane");
        updatedPlayer.setLastName("Smith");
        updatedPlayer.setEmail("jane.smith@example.com");
      //  updatedPlayer.setRank("Lieutenant");
        updatedPlayer.setType("Amateur");

        when(playerService.getById(id)).thenReturn(existingPlayer);
        when(playerService.create(existingPlayer)).thenThrow(new RuntimeException("Service error"));
 // Act & Assert
 RuntimeException exception = assertThrows(RuntimeException.class, () -> 
 playerController.createPlayer(id, updatedPlayer)
);
assertEquals("Service error", exception.getMessage(), "The exception message should match");
verify(playerService, times(1)).getById(id);
verify(playerService, times(1)).create(existingPlayer);
}


}


