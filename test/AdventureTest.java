import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class AdventureTest {

    private static Adventure siebelAdventureGame;

    @BeforeClass
    public static void setAdventureGame() throws IOException {

        siebelAdventureGame = new Adventure(
                new URL("https://courses.engr.illinois.edu/cs126/adventure/siebel.json"));

    }

    @Test
    public void nonURLTest() throws AssertionError {

        boolean exceptionThrown = false;

        try {
            new Adventure(new URL("not_a_url"));
        } catch (IOException e) {
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);

    }

    @Test
    public void invalidURLTest() throws AssertionError {

        boolean exceptionThrown = false;

        try {
            new Adventure(new URL("www.google.com"));
        } catch (IOException e) {
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);

    }

    @Test
    public void invalidJSONTest() throws AssertionError {

        boolean nullPtrExceptionThrown = false;

        try {
            new Adventure(new URL("http://api.tvmaze.com/singlesearch/shows?q=mr-robot&embed=episodes"));
        } catch (NullPointerException e) {
            nullPtrExceptionThrown = true;
        } catch (IOException e) {
            //if this exception is thrown, the null pointer exception was not thrown and the assertion is false
        }

        assertTrue(nullPtrExceptionThrown);

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
