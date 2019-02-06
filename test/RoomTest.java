import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RoomTest {

    private static Adventure siebelAdventureGame;

    @BeforeClass
    public static void setAdventureGame() throws IOException {

        siebelAdventureGame = Adventure.initialize("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");

    }

    @Test
    public void getRoomEqualityTest() throws AssertionError {

        Adventure.Layout.Room siebel1112Room = siebelAdventureGame.getGameLayout().createNewRoom();

        siebel1112Room.setName("Siebel1112");
        siebel1112Room.setDescription("You are in Siebel 1112.  There is space for two code reviews in this room.");

        Adventure.Layout.Room.Direction siebel1112directions = siebel1112Room.createNewDirection();
        siebel1112directions.setDirectionName("West");
        siebel1112directions.setRoomAhead(siebelAdventureGame.getGameLayout().getRoomByName("SiebelNorthHallway"));
        Adventure.Layout.Room.Direction[] directions = new Adventure.Layout.Room.Direction[] { siebel1112directions };
        siebel1112Room.setPossibleDirections(directions);

        assertEquals(siebel1112Room, siebelAdventureGame.getGameLayout().getAllRooms()[4]);

    }

    @Test
    public void findRoomAheadTest() throws AssertionError {

        Adventure.Layout.Room acmOffice = siebelAdventureGame.getGameLayout().createNewRoom();

        acmOffice.setName("AcmOffice");
        acmOffice.setDescription("You are in the ACM office.  There are lots of friendly ACM people.");

        Adventure.Layout.Room.Direction acmOfficeDirections = acmOffice.createNewDirection();
        acmOfficeDirections.setDirectionName("South");
        acmOfficeDirections.setRoomAhead(siebelAdventureGame.getGameLayout().getRoomByName("SiebelEntry"));
        acmOffice.setPossibleDirections(new Adventure.Layout.Room.Direction[] { acmOfficeDirections });


        assertEquals(acmOffice, siebelAdventureGame.getGameLayout()
                .getRoomByName("SiebelEntry").findRoomsInDirection("Northeast"));

    }

    @Test
    public void printDirectionsTest() throws AssertionError {

        Adventure.Layout.Room siebelNorth = siebelAdventureGame
                    .getGameLayout().getRoomByName("SiebelNorthHallway");

        assertEquals("From here you can go: South, or NorthEast", siebelNorth.printAllDirections());

    }



}
