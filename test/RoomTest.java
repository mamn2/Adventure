import com.google.gson.JsonParser;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RoomTest {

    private static Adventure gringottsAdventureGame;

    @BeforeClass
    public static void setAdventureGame() throws IllegalArgumentException {

        gringottsAdventureGame = new Adventure(
                new JsonParser().parse(Data.getFileContentsAsString("Gringotts")).getAsJsonObject());

    }

    @Test
    public void getRoomEqualityTest() throws AssertionError {

        Room bellatrixVault = new Room();
        bellatrixVault.setName("Bellatrix's Vault");
        bellatrixVault.setDescription("You have entered Bellatrix Lestrange's Vault.");

        Direction bellatrixVaultDirections = new Direction();
        bellatrixVaultDirections.setDirectionName("West");
        bellatrixVaultDirections.setRoomAhead(gringottsAdventureGame.getGameLayout().getRoomByName("Vault Chamber Entrance"));
        bellatrixVaultDirections.setNecessaryKeys(new Item[] { new Item("Griphook"),
                new Item("the Sword Of Gryffindor")
        });
        bellatrixVaultDirections.setUnlocked(false);
        bellatrixVault.setPossibleDirections(new Direction[] { bellatrixVaultDirections });

        bellatrixVault.setItems(new Item[] { new Item("the Sword of Gryffindor") });
        bellatrixVault.setMonster(null);

        assertEquals(bellatrixVault, gringottsAdventureGame.getGameLayout().getRoomByName("Bellatrix's Vault"));

    }

    @Test
    public void roomNullInequalityTest() throws AssertionError {

        assertNotEquals(null, gringottsAdventureGame.getGameLayout().getRoomByName("Harry's Vault"));

    }

    @Test
    public void roomInequalityTest() throws AssertionError {

        Room diagonAlley = new Room();
        diagonAlley.setName("Diagon Alley");
        diagonAlley.setDescription("You are in Diagon Alley, in the intersection by Gringotts Bank.");

        Direction diagonAlleyDirection = new Direction();
        diagonAlleyDirection.setDirectionName("North");
        diagonAlleyDirection.setRoomAhead(gringottsAdventureGame.getGameLayout().getRoomByName("Gringotts Bank Lobby"));
        diagonAlleyDirection.setUnlocked(true);
        diagonAlleyDirection.setNecessaryKeys(new Item[0]);
        diagonAlley.setPossibleDirections(new Direction[] { diagonAlleyDirection });

        Item hermione = new Item("Hermione");
        Item ron = new Item("Ron");
        Item pigeon = new Item("a pigeon");
        diagonAlley.setItems(new Item[] { hermione, ron, pigeon });

        assertNotEquals(diagonAlley, gringottsAdventureGame.getGameLayout().getAllRooms()[3]);

    }

    @Test
    public void findRoomAheadTest() throws AssertionError {

        Room diagonAlley = new Room();
        diagonAlley.setName("Diagon Alley");
        diagonAlley.setDescription("You are in Diagon Alley, in the intersection by Gringotts Bank.");

        Direction diagonAlleyDirection = new Direction();
        diagonAlleyDirection.setDirectionName("North");
        diagonAlleyDirection.setRoomAhead(gringottsAdventureGame.getGameLayout().getRoomByName("Gringotts Bank Lobby"));
        diagonAlleyDirection.setUnlocked(true);
        diagonAlleyDirection.setNecessaryKeys(new Item[0]);
        diagonAlley.setPossibleDirections(new Direction[] { diagonAlleyDirection });

        Item hermione = new Item("Hermione");
        Item ron = new Item("Ron");
        Item pigeon = new Item("a pigeon");
        diagonAlley.setItems(new Item[] { hermione, ron, pigeon });


        assertEquals(diagonAlley, gringottsAdventureGame.getGameLayout()
                .getRoomByName("Gringotts Bank Lobby").findRoomsInDirection("South"));

    }

    @Test
    public void printDirectionsTest() throws AssertionError {

        Room vaultEntrance = gringottsAdventureGame.getGameLayout().getRoomByName("Vault Chamber Entrance");

        assertEquals("From here you can go: North, East, Down, or West", vaultEntrance.printAllDirections());

    }

    @Test
    public void printItemsTest() throws AssertionError {

        Room bellatrixVault = gringottsAdventureGame.getGameLayout().getRoomByName("Bellatrix's Vault");

        assertEquals("From here you can pickup: the Sword Of Gryffindor", bellatrixVault.printAllItems());

    }



}
