import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class RoomTest {

    private static Adventure siebelAdventureGame;

    @BeforeClass
    public static void setAdventureGame() throws IOException {

        siebelAdventureGame = new Adventure(
                new URL("https://courses.engr.illinois.edu/cs126/adventure/siebel.json"));

    }

    @Test
    public void getRoomEqualityTest() throws AssertionError {

        Adventure.Layout.Room testRoom = siebelAdventureGame.getGameLayout().createNewRoom();

        testRoom.setName("AcmOffice");
        testRoom.setDescription("You are in the ACM office.  There are lots of friendly ACM people.");

        Adventure.Layout.Room.Direction direction = testRoom.createNewDirection();
        direction.setDirectionName("South");
        direction.setNextRoom(siebelAdventureGame.getGameLayout().getAllRooms()[1]);
        Adventure.Layout.Room.Direction[] directions = new Adventure.Layout.Room.Direction[] { direction };
        testRoom.setPossibleDirections(directions);

        assertEquals(testRoom, siebelAdventureGame.getGameLayout().getAllRooms()[2]);

    }

}
