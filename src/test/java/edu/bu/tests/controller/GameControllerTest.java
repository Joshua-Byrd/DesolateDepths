package edu.bu.tests.controller;

import edu.bu.controller.GameController;
import edu.bu.database.DatabaseManager;
import edu.bu.database.FacadeDatabase;
import edu.bu.model.Room;
import edu.bu.model.entitities.Player;
import edu.bu.model.persistence.GameLogger;
import edu.bu.model.persistence.PlayerSaveService;
import edu.bu.view.TextView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GameControllerTest {
    private GameController gameController;
    private TextView viewMock;
    private Player playerMock;
    private Room roomMock;
    private PlayerSaveService pssMock;
    private GameLogger logMock;
    private FacadeDatabase dbMock;

//    @BeforeEach
//    void setUp() {
//        viewMock = Mockito.mock(TextView.class);
//        playerMock = Mockito.mock(Player.class);
//        roomMock = Mockito.mock(Room.class);
//        pssMock = Mockito.mock(PlayerSaveService.class);
//        logMock = Mockito.mock(GameLogger.class);
//        dbMock = FacadeDatabase.getTheInstance();
//        gameController = new GameController(viewMock, playerMock, roomMock, pssMock, logMock, dbMock);
//    }
//
////    @Test
////    void testDisplayMessage() {
////        String testMessage = "Test message";
////        gameController.displayMessage(testMessage);
////        Mockito.verify(viewMock).displayMessage(testMessage);
////    }
//
}
