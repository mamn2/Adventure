import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
    public void validJSONTest() throws AssertionError {

        assertTrue(Adventure.initialize(
                "https://courses.engr.illinois.edu/cs126/sp2019/adventure/student_submissions/CustomAdventureGame.json")
                instanceof Adventure);

    }

    @Test
    public void testInvalidDirectionInput() throws AssertionError {

        //goDirectionResponse should return null if the input is not readable
        assertNull(siebelAdventureGame.goDirectionResponse("gophers",
                siebelAdventureGame.getGameLayout().getStartingRoom()));

    }

    @Test
    public void testGoInvalidDirectionInput() throws AssertionError {

        assertNull(siebelAdventureGame.goDirectionResponse("go eas",
                siebelAdventureGame.getGameLayout().getStartingRoom()));

    }

    @Test
    public void testValidDirectionInputResponse() throws AssertionError {

        Room siebelEntry = siebelAdventureGame.getGameLayout().getRoomByName("SiebelEntry");
        Room matthewsStreet = siebelAdventureGame.getGameLayout().getStartingRoom();

        assertEquals(siebelEntry, siebelAdventureGame.goDirectionResponse("GO east", matthewsStreet));

    }

    @Test
    public void testExitInput() throws AssertionError {

        exit.expectSystemExitWithStatus(1);
        siebelAdventureGame.goDirectionResponse("exit", siebelAdventureGame.getGameLayout().getStartingRoom());

    }

    @Test
    public void testQuitInput() throws AssertionError {

        exit.expectSystemExitWithStatus(1);
        siebelAdventureGame.goDirectionResponse("quit", siebelAdventureGame.getGameLayout().getAllRooms()[3]);

    }

    @Test
    public void testGameOver() throws AssertionError {

        exit.expectSystemExitWithStatus(1);
        siebelAdventureGame.goDirectionResponse("go soUth",
                siebelAdventureGame.getGameLayout().getRoomByName("SiebelEastHallway"));

    }



}
