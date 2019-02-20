import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    private static Adventure gringottsAdventureGame;

    @Before
    public void setAdventureGame() throws IllegalArgumentException {

        gringottsAdventureGame = new Adventure(
                new JsonParser().parse(Data.getFileContentsAsString("Gringotts")).getAsJsonObject());

    }

    @Test
    public void testValidAddToItems() throws AssertionError {

        gringottsAdventureGame.currentRoom = gringottsAdventureGame.getGameLayout().getRoomByName("Diagon Alley");
        gringottsAdventureGame.player.addToItems(new Item("Hermione"));

        assertTrue(gringottsAdventureGame.player.getItems().contains(new Item("Hermione")));

    }

    @Test
    public void testInvalidAddToItems() throws AssertionError {

        gringottsAdventureGame.currentRoom = gringottsAdventureGame.getGameLayout().getRoomByName("Diagon Alley");
        gringottsAdventureGame.userInputResponse("pickup the Sword Of Gryffindor");

        assertFalse(gringottsAdventureGame.player.getItems().contains(new Item("the Sword Of Gryffindor")));

    }

    @Test
    public void testRemoveValidItem() throws AssertionError {

        gringottsAdventureGame.currentRoom = gringottsAdventureGame.getGameLayout().getRoomByName("Diagon Alley");
        gringottsAdventureGame.userInputResponse("pickup hermione");
        gringottsAdventureGame.userInputResponse("remove hermione");

        assertFalse(gringottsAdventureGame.player.getItems().contains(new Item("Hermione")));

    }

    @Test
    public void testAddMoreThan3Items() throws AssertionError {

        gringottsAdventureGame.currentRoom = gringottsAdventureGame.getGameLayout().getRoomByName("Diagon Alley");
        gringottsAdventureGame.userInputResponse("pickup hermione");
        gringottsAdventureGame.userInputResponse("pickup ron");
        gringottsAdventureGame.userInputResponse("pickup a pigeon");

        gringottsAdventureGame.userInputResponse("go north");
        gringottsAdventureGame.userInputResponse("pickup griphook");

        assertEquals(3,gringottsAdventureGame.player.getItems().size());


    }

}

