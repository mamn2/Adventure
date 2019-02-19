import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RoomTest {

    private static Adventure siebelAdventureGame;

    @BeforeClass
    public static void setAdventureGame() throws IOException {

        siebelAdventureGame = Adventure.initialize("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");

    }

    @Test
    public void getRoomEqualityTest() throws AssertionError {

        Room siebel1112Room = siebelAdventureGame.getGameLayout().createNewRoom();

        siebel1112Room.setName("Siebel1112");
        siebel1112Room.setDescription("You are in Siebel 1112.  There is space for two code reviews in this room.");

        Direction siebel1112directions = siebel1112Room.createNewDirection();
        siebel1112directions.setDirectionName("West");
        siebel1112directions.setRoomAhead(siebelAdventureGame.getGameLayout().getRoomByName("SiebelNorthHallway"));
        Direction[] directions = new Direction[] { siebel1112directions };
        siebel1112Room.setPossibleDirections(directions);

        assertEquals(siebel1112Room, siebelAdventureGame.getGameLayout().getAllRooms()[4]);

    }

    @Test
    public void roomNullInequalityTest() throws AssertionError {

        assertNotEquals(null, siebelAdventureGame.getGameLayout().getRoomByName("SiebelNorthHallway"));

    }

    @Test
    public void roomInequalityTest() throws AssertionError {

        Room siebel1112Room = siebelAdventureGame.getGameLayout().createNewRoom();

        siebel1112Room.setName("Siebel1112");
        siebel1112Room.setDescription("You are in Siebel 1112.  There is space for two code reviews in this room.");

        Direction siebel1112directions = siebel1112Room.createNewDirection();
        siebel1112directions.setDirectionName("West");
        siebel1112directions.setRoomAhead(siebelAdventureGame.getGameLayout().getRoomByName("SiebelNorthHallway"));
        Direction[] directions = new Direction[] { siebel1112directions };
        siebel1112Room.setPossibleDirections(directions);

        assertNotEquals(siebel1112Room, siebelAdventureGame.getGameLayout().getAllRooms()[3]);

    }

    @Test
    public void findRoomAheadTest() throws AssertionError {

        Room acmOffice = siebelAdventureGame.getGameLayout().createNewRoom();

        acmOffice.setName("AcmOffice");
        acmOffice.setDescription("You are in the ACM office.  There are lots of friendly ACM people.");

        Direction acmOfficeDirections = acmOffice.createNewDirection();
        acmOfficeDirections.setDirectionName("South");
        acmOfficeDirections.setRoomAhead(siebelAdventureGame.getGameLayout().getRoomByName("SiebelEntry"));
        acmOffice.setPossibleDirections(new Direction[] { acmOfficeDirections });


        assertEquals(acmOffice, siebelAdventureGame.getGameLayout()
                .getRoomByName("SiebelEntry").findRoomsInDirection("Northeast"));

    }

    @Test
    public void printDirectionsTest() throws AssertionError {

        Room siebelNorth = siebelAdventureGame
                    .getGameLayout().getRoomByName("SiebelNorthHallway");

        assertEquals("From here you can go: South, or NorthEast", siebelNorth.printAllDirections());

    }



}
