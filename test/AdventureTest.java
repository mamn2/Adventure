import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AdventureTest {

    private static Adventure siebelAdventureGame;

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @BeforeClass
    public static void setAdventureGame() throws IOException {

        siebelAdventureGame = Adventure.initialize("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");

    }

    @Test
    public void nonURLTest() throws AssertionError {

        assertNull(Adventure.initialize("not_a_url"));

    }

    @Test
    public void invalidURLTest() throws AssertionError {

        assertNull(Adventure.initialize("www.google.com"));

    }

    @Test
    public void invalidJSONTest() throws AssertionError {

        assertNull(Adventure.initialize("http://api.tvmaze.com/singlesearch/shows?q=mr-robot&embed=episodes"));

    }

    @Test
    public void testInvalidDirectionInput() throws AssertionError {

        //userInputResponse should return null if the input is not readable
        assertNull(siebelAdventureGame.userInputResponse("gophers",
                siebelAdventureGame.getGameLayout().getStartingRoom()));

    }

    @Test
    public void testGoInvalidDirectionInput() throws AssertionError {

        assertNull(siebelAdventureGame.userInputResponse("go eas",
                siebelAdventureGame.getGameLayout().getStartingRoom()));

    }

    @Test
    public void testValidDirectionInputResponse() throws AssertionError {

        Adventure.Layout.Room siebelEntry = siebelAdventureGame.getGameLayout().getRoomByName("SiebelEntry");
        Adventure.Layout.Room matthewsStreet = siebelAdventureGame.getGameLayout().getStartingRoom();

        assertEquals(siebelEntry, siebelAdventureGame.userInputResponse("GO east", matthewsStreet));

    }

    @Test
    public void testExitInput() throws AssertionError {

        exit.expectSystemExitWithStatus(1);
        siebelAdventureGame.userInputResponse("exit", siebelAdventureGame.getGameLayout().getStartingRoom());

    }

    @Test
    public void testQuitInput() throws AssertionError {

        exit.expectSystemExitWithStatus(1);
        siebelAdventureGame.userInputResponse("quit", siebelAdventureGame.getGameLayout().getAllRooms()[3]);

    }

    @Test
    public void testGameOver() throws AssertionError {

        exit.expectSystemExitWithStatus(1);
        siebelAdventureGame.userInputResponse("go soUth",
                siebelAdventureGame.getGameLayout().getRoomByName("SiebelEastHallway"));

    }



}
