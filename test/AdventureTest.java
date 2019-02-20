import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AdventureTest {

    private static Adventure gringottsAdventureGame;

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void setAdventureGame() throws IllegalArgumentException {

        gringottsAdventureGame = new Adventure(
                new JsonParser().parse(Data.getFileContentsAsString("Gringotts")).getAsJsonObject());

    }

    @Test
    public void nonURLTest() throws AssertionError {

        assertNull(Adventure.initializeURL("not_a_url"));

    }

    @Test
    public void invalidURLTest() throws AssertionError {

        assertNull(Adventure.initializeURL("www.google.com"));

    }

    @Test
    public void invalidJSONTest() throws AssertionError {

        assertNull(Adventure.initializeURL("http://api.tvmaze.com/singlesearch/shows?q=mr-robot&embed=episodes"));

    }

    @Test
    public void validJSONTest() throws AssertionError {

        assertTrue(Adventure.initializeURL(
                "https://pastebin.com/raw/YXd9RmGh")
                instanceof Adventure);

    }

    @Test
    public void testInvalidDirectionInput() throws AssertionError {

        //goDirectionResponse should return null if the input is not readable
        //assertNull(gringottsAdventureGame.goDirectionResponse("gophers",
        //        gringottsAdventureGame.getGameLayout().getStartingRoom()));

    }

    @Test
    public void testGoInvalidDirectionInput() throws AssertionError {

        //assertNull(gringottsAdventureGame.goDirectionResponse("go eas",
        //        gringottsAdventureGame.getGameLayout().getStartingRoom()));

    }

    @Test
    public void testValidDirectionInputResponse() throws AssertionError {

        Room siebelEntry = gringottsAdventureGame.getGameLayout().getRoomByName("SiebelEntry");
        Room matthewsStreet = gringottsAdventureGame.getGameLayout().getStartingRoom();

        //assertEquals(siebelEntry, gringottsAdventureGame.goDirectionResponse("GO east", matthewsStreet));

    }

    @Test
    public void testExitInput() throws AssertionError {

        exit.expectSystemExitWithStatus(1);
        gringottsAdventureGame.userInputResponse("exit");

    }

    @Test
    public void testQuitInput() throws AssertionError {

        exit.expectSystemExitWithStatus(1);
        gringottsAdventureGame.userInputResponse("quit");

    }

    @Test
    public void testGameWin() throws AssertionError {

        exit.expectSystemExitWithStatus(1);
        gringottsAdventureGame.currentRoom = gringottsAdventureGame.getGameLayout().getRoomByName("Bellatrix's Vault");
        gringottsAdventureGame.userInputResponse("pickup the Sword of Gryffindor");

    }

    @Test
    public void testGameLostToMonster() throws AssertionError {

        exit.expectSystemExitWithStatus(1);
        gringottsAdventureGame.currentRoom = gringottsAdventureGame.getGameLayout().getRoomByName("Vault Chamber Entrance");
        gringottsAdventureGame.userInputResponse("go down");

    }


}
