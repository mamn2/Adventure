import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class LayoutTest {

    private static Adventure siebelAdventureGame;

    @BeforeClass
    public static void setAdventureGame() throws IOException {

        siebelAdventureGame = new Adventure(
                new URL("https://courses.engr.illinois.edu/cs126/adventure/siebel.json"));

    }

    @Test
    public void getStartingRoomTest() throws AssertionError {

        Adventure.Layout.Room matthewsStreet = siebelAdventureGame.getGameLayout().createNewRoom();
        matthewsStreet.setName("MatthewsStreet");
        matthewsStreet.setDescription("You are on Matthews, outside the Siebel Center");

        Adventure.Layout.Room.Direction matthewsStreetDirections = matthewsStreet.createNewDirection();
        matthewsStreetDirections.setDirectionName("East");
        matthewsStreetDirections.setRoomAhead(siebelAdventureGame.getGameLayout().getRoomByName("SiebelEntry"));
        matthewsStreet.setPossibleDirections(new Adventure.Layout.Room.Direction[] { matthewsStreetDirections });

        assertEquals(matthewsStreet, siebelAdventureGame.getGameLayout().getStartingRoom());

    }

    @Test
    public void getEndingRoomTest() throws AssertionError {

        Adventure.Layout.Room siebel1314 = siebelAdventureGame.getGameLayout().createNewRoom();
        siebel1314.setName("Siebel1314");
        siebel1314.setDescription("You are in Siebel 1314.  There are happy CS 126 students doing a code review.");

        Adventure.Layout.Room.Direction siebel1314Directions = siebel1314.createNewDirection();
        siebel1314Directions.setDirectionName("North");
        siebel1314Directions.setRoomAhead(siebelAdventureGame.getGameLayout().getRoomByName("SiebelEastHallway"));
        siebel1314.setPossibleDirections(new Adventure.Layout.Room.Direction[] { siebel1314Directions });

        assertEquals(siebel1314, siebelAdventureGame.getGameLayout().getEndingRoom());

    }

    @Test
    public void getRoomByNameTest() throws AssertionError {

        Adventure.Layout.Room siebel1314 = siebelAdventureGame.getGameLayout().createNewRoom();
        siebel1314.setName("Siebel1314");
        siebel1314.setDescription("You are in Siebel 1314.  There are happy CS 126 students doing a code review.");

        Adventure.Layout.Room.Direction siebel1314Directions = siebel1314.createNewDirection();
        siebel1314Directions.setDirectionName("North");
        siebel1314Directions.setRoomAhead(siebelAdventureGame.getGameLayout().getRoomByName("SiebelEastHallway"));
        siebel1314.setPossibleDirections(new Adventure.Layout.Room.Direction[] { siebel1314Directions });

        assertEquals(siebel1314, siebelAdventureGame.getGameLayout().getRoomByName("Siebel1314"));

    }

    @Test
    public void getNonExistentRoomTest() throws AssertionError {

        assertNull(siebelAdventureGame.getGameLayout().getRoomByName("notaroom"));

    }

    @Test
    public void getNullRoomTest() throws AssertionError {

        assertNull(siebelAdventureGame.getGameLayout().getRoomByName(null));

    }



}
