import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class LayoutTest {

    private static Adventure gringottsAdventureGame;

    @Before
    public void setAdventureGame() throws IllegalArgumentException {

        gringottsAdventureGame = new Adventure(
                new JsonParser().parse(Data.getFileContentsAsString("Gringotts")).getAsJsonObject());

    }

    @Test
    public void getStartingRoomTest() throws AssertionError {

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

        assertEquals(diagonAlley, gringottsAdventureGame.getGameLayout().getStartingRoom());

    }

    @Test
    public void getEndingRoomTest() throws AssertionError {

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

        assertEquals(bellatrixVault, gringottsAdventureGame.getGameLayout().getEndingRoom());

    }

    @Test
    public void getRoomByNameTest() throws AssertionError {

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
    public void getNonExistentRoomTest() throws AssertionError {

        assertNull(gringottsAdventureGame.getGameLayout().getRoomByName("notaroom"));

    }

    @Test
    public void getNullRoomTest() throws AssertionError {

        assertNull(gringottsAdventureGame.getGameLayout().getRoomByName(null));

    }

}
