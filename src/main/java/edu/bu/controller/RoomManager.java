package edu.bu.controller;


import edu.bu.model.FacadeModel;
import edu.bu.model.Room;
import edu.bu.model.items.*;


/**
 * RoomManager is responsible for initializing and managing the rooms in the game.
 * It sets up the initial room configurations and links rooms together.
 */
public class RoomManager {

    private Room courtyard;
    private final FacadeItems facadeItems = FacadeItems.getTheInstance();
    private final FacadeModel facadeModel = FacadeModel.getTheInstance();

    public RoomManager() {
        initializeRooms();
    }

    /**
     * INTENT: Initializes the rooms and their respective inventories, linking them together.
     * PRECONDITION: None.
     * POSTCONDITION: Rooms are created, items are added to their inventories, and rooms are linked.
     */
    private void initializeRooms() {
        // Create items using FacadeItems
        Weapon dagger = facadeItems.createWeapon("dagger", "a small dagger", 1.2, 4, 10.0);
        Container chest = facadeItems.createContainer("wooden chest", "an old wooden chest", 5.0, false, facadeItems.createInventory(100));

        // Create an inventory for the starting room and add items to it
        Inventory<Item> startingRoomInventory = facadeItems.createInventory(1000);
        facadeItems.addItem(startingRoomInventory, dagger);
        facadeItems.addItem(startingRoomInventory, chest);

        // Create the starting room using FacadeModel
        courtyard = facadeModel.createRoom("vast courtyard",
                "To the west you see the winding road that" +
                        " led you here. To the north and south, impenetrable forest stretches into the distance.\n" +
                        "To the east lies the entrance to the labyrinth known as the Desolate Depths.",
                startingRoomInventory);

        // Create another room with its own inventory using FacadeModel
        Inventory<Item> anotherRoomInventory = facadeItems.createInventory(1000);
        Room smallStoneRoom = facadeModel.createRoom("small stone room", "The walls are moss-covered and slick with " +
                "moisture. To the west is the door you came through. You can see no other openings.", anotherRoomInventory);

        courtyard.setConnection("east", smallStoneRoom);
        smallStoneRoom.setConnection("west", courtyard);
    }

    /**
     * INTENT: Returns the starting room of the game.
     * PRECONDITION: The starting room must have been initialized.
     * POSTCONDITION: The starting room is returned.
     *
     * @return The starting room.
     */
    public Room getStartingRoom() {
        return courtyard;
    }
}



