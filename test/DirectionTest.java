import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DirectionTest {

    private static Adventure siebelAdventureGame;

    @BeforeClass
    public static void setAdventureGame() throws IOException {

        siebelAdventureGame = Adventure.initialize("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");

    }

    @Test
    public void directionEqualityTest() throws AssertionError {

        Adventure.Layout.Room.Direction testDirection = siebelAdventureGame.getGameLayout().getRoomByName("Siebel1314")
                                                                                           .createNewDirection();

        testDirection.setDirectionName("North");
        testDirection.setRoomAhead(siebelAdventureGame.getGameLayout().getRoomByName("SiebelEastHallway"));

        assertEquals(testDirection, siebelAdventureGame.getGameLayout().getRoomByName("Siebel1314")
                                                                        .getPossibleDirections()[0]);

    }

    @Test
    public void directionInequalityTest() throws AssertionError {

        Adventure.Layout.Room.Direction testDirection = siebelAdventureGame.getGameLayout().getRoomByName("Siebel1314")
                .createNewDirection();

        testDirection.setDirectionName("North");
        testDirection.setRoomAhead(siebelAdventureGame.getGameLayout().getRoomByName("SiebelEastHallway"));

        assertNotEquals(testDirection, siebelAdventureGame.getGameLayout().getRoomByName("MatthewsStreet")
                .getPossibleDirections()[0]);

    }

}
