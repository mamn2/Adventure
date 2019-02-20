import com.google.gson.JsonParser;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DirectionTest {

    private static Adventure gringottsAdventureGame;

    @BeforeClass
    public static void setAdventureGame() throws IllegalArgumentException {

        gringottsAdventureGame = new Adventure(
                new JsonParser().parse(Data.getFileContentsAsString("Gringotts")).getAsJsonObject());

    }

    @Test
    public void directionEqualityTest() throws AssertionError {

        Direction testDirection = new Direction();

        testDirection.setDirectionName("North");
        testDirection.setRoomAhead(gringottsAdventureGame.getGameLayout().getRoomByName("Gringotts Bank Lobby"));
        testDirection.setNecessaryKeys(new Item[0]);
        testDirection.setUnlocked(true);

        assertEquals(testDirection, gringottsAdventureGame.getGameLayout().getRoomByName("Diagon Alley")
                                                                        .getPossibleDirections()[0]);

    }

    @Test
    public void directionNullInequalityTest() throws AssertionError {

        assertNotEquals(null,
                gringottsAdventureGame.getGameLayout().getStartingRoom().getPossibleDirections()[0]);

    }

    @Test
    public void directionInequalityTest() throws AssertionError {

        Direction testDirection = new Direction();

        testDirection.setDirectionName("North");
        testDirection.setRoomAhead(gringottsAdventureGame.getGameLayout().getRoomByName("Gringotts Bank Lobby"));

        assertNotEquals(testDirection, gringottsAdventureGame.getGameLayout().getRoomByName("Gringotts Bank Lobby")
                .getPossibleDirections()[0]);

    }

}
