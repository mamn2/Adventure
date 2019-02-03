import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class AdventureGameTest {

    private static Adventure siebelAdventureGame;

    @BeforeClass
    public static void setAdventureGame() throws IOException {

        siebelAdventureGame = new Adventure(
                new URL("https://courses.engr.illinois.edu/cs126/adventure/siebel.json"));

    }

    @Test
    public void falseURLTest() throws AssertionError {

        boolean exceptionThrown = false;

        try {
            new Adventure(new URL("www.google.com"));
        } catch (IOException e) {
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);

    }

    @Test
    public void getStartingRoomTest() throws AssertionError {

        assertEquals("MatthewsStreet", siebelAdventureGame.getGameLayout().getStartingRoom());

    }

    @Test
    public void getEndingRoomTest() throws AssertionError {

        assertEquals("Siebel1314", siebelAdventureGame.getGameLayout().getEndingRoom());

    }

    @Test
    public void getRoomEqualityTest() throws AssertionError {

        Adventure.Layout.Room testRoom = siebelAdventureGame.getGameLayout().createNewRoom();

        testRoom.setName("AcmOffice");
        testRoom.setDescription("You are in the ACM office.  There are lots of friendly ACM people.");

        Adventure.Layout.Room.Direction direction = testRoom.createNewDirection();
        direction.setDirectionName("South");
        direction.setRoom("SiebelEntry");
        Adventure.Layout.Room.Direction[] directions = new Adventure.Layout.Room.Direction[] { direction };
        testRoom.setPossibleDirections(directions);

        assertEquals(testRoom, siebelAdventureGame.getGameLayout().getAllRooms()[2]);

    }

    @Test
    public void testInvalidDirectionInput() throws AssertionError {

    }

    @Test
    public void testGoInvalidDirectionInput() throws AssertionError {

    }

    @Test
    public void testValidDirectionInputResponse() throws AssertionError {

    }

    @Test
    public void testExitInput() throws AssertionError {

    }

    @Test
    public void testQuitInput() throws AssertionError {

    }

    @Test
    public void testGameOver() throws AssertionError {

    }

}
