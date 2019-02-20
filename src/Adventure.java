import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * This class represents the state and behavior of an Adventure game.
 */
public class Adventure {

    //Layout of the games locations, directions, rooms, etc.
    private Layout gameLayout;

    //A player object containing information about the user playing the game.
    public Player player;

    //Current room that the player is in.
    public Room currentRoom;

    /**
     * Initializes a new Adventure game through user input.
     */
    public static void main(String[] args) {

        Adventure adventure = null;

        System.out.println("Would you like to play this game using a file or URL?");
        System.out.println("Enter 'file' for a filepath or 'URL' for a URL");
        Scanner scanner = new Scanner(System.in);

        String urlOrFile = scanner.nextLine();

        switch (urlOrFile.toUpperCase()) {
            case "FILE":
                adventure = new Adventure(
                        new JsonParser().parse(Data.getFileContentsAsString("Gringotts")).getAsJsonObject());
                break;
            /*case "URL":
                System.out.println("Enter the URL you want to create a JSON for");
                String url = scanner.nextLine();
                while (adventure == null) {
                    adventure = initializeURL(url);
                }
                break;
            default:
                System.out.println("Sorry, you must enter 'URL' or 'File'");
                break;*/
        }

        adventure.playGame();

        int i = 0;
    }

    /**
     * Initializes a new Adventure game to ensure a user cannot create an invalid instance of Adventure.
     * @param url is a url containing a JSON of the Adventure structure.
     * @return a new Adventure class, or null if it is invalid.
     */
    public static Adventure initializeURL(String url) {

        try {
            URL urlInput = new URL(url);
            //Connecting to the URL
            URLConnection request = urlInput.openConnection();
            request.connect();

            //code below derived from:
            //https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
            //Gets JSON file from a URL
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonData = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()))
                    .getAsJsonObject();

            Adventure adventure = new Adventure(jsonData);

            System.out.println("Press enter to start game");
            return adventure;
        } catch (IOException e) {
            System.out.println("This is not a JSON URL, try again");
        } catch (Exception e) {
            System.out.println("This URL is not an Adventure game, try again");
        }

        return null;

    }

    /**
     * This constructor deserializes a JSON object to form an Adventure class.
     * @param jsonData A JSON file for an Adventure game
     * @throws NullPointerException if the JSON file does not correctly map to an Adventure game
     */
    public Adventure(JsonObject jsonData) throws NullPointerException {

        gameLayout = new Layout();
        player = new Player();

        JsonArray rooms = jsonData.get("rooms").getAsJsonArray();
        Room[] roomsArray = new Room[rooms.size()];

        //Goes through all the rooms in the JSON and converts them into an array of type Room.
        for (int i = 0; i < roomsArray.length; i++) {

            //takes all fields from JSON and uses them to initializeURL fields in Room class.
            roomsArray[i] = new Gson().fromJson(rooms.get(i), Room.class);

            //Stores all directions for room in a JsonArray.
            JsonArray directions = rooms.get(i).getAsJsonObject().get("directions").getAsJsonArray();
            Direction[] directionsInRoom = new Direction[directions.size()];

            //Goes through all the directions in each room and converts them into an array.
            for (int j = 0; j < directionsInRoom.length; j++) {
                //takes all fields from JSON and uses to initializeURL fields in Direction class.
                directionsInRoom[j] = new Gson().fromJson(directions.get(j), Direction.class);
                directionsInRoom[j].roomAheadName = directions.get(j).getAsJsonObject().get("room").getAsString();
            }
            roomsArray[i].setPossibleDirections(directionsInRoom);

        }

        //sets all fields of the Layout class in the gameLayout instance
        gameLayout.setAllRooms(roomsArray);
        gameLayout.setStartingRoom(gameLayout.getRoomByName(jsonData.get("startingRoom").getAsString()));
        gameLayout.setEndingRoom(gameLayout.getRoomByName(jsonData.get("endingRoom").getAsString()));
        gameLayout.setItemObjective(new Item(jsonData.get("itemObjective").getAsJsonObject().get("name").getAsString()));

        //iterates through all the rooms in the game
        for (int roomIndex = 0; roomIndex < gameLayout.getAllRooms().length; roomIndex++) {

            //Contains the current room we are iterating through
            Room room = gameLayout.getAllRooms()[roomIndex];

            //Iterates through all the directions from the room
            for (int directionIndex = 0; directionIndex < room.getPossibleDirections().length; directionIndex++) {

                //Contains the current direction we are iterating through
                Direction direction = room.getPossibleDirections()[directionIndex];

                direction.setRoomAhead(gameLayout.getRoomByName(direction.roomAheadName));

                //Contains the current direction in the form of a JsonObject
                JsonObject jsonDirection = rooms.get(roomIndex).getAsJsonObject().get("directions").getAsJsonArray()
                        .get(directionIndex).getAsJsonObject();

                direction.setUnlocked(jsonDirection.get("enabled").getAsBoolean());

                //A JsonArray containing all the necessary keys to open the room
                JsonArray jsonNecessaryKeys = jsonDirection.get("validKeyNames").getAsJsonArray();
                Item[] necessaryKeys = new Item[jsonNecessaryKeys.size()];

                for (int necessaryKeysIndex = 0; necessaryKeysIndex < necessaryKeys.length; necessaryKeysIndex++) {
                    necessaryKeys[necessaryKeysIndex] = new Item(jsonNecessaryKeys.get(necessaryKeysIndex).getAsString());
                }

                direction.setNecessaryKeys(necessaryKeys);

            }

        }

    }

    /**
     * Getter for gameLayout instance variable
     * @return the layout for this instance of Adventure
     */
    public Layout getGameLayout() {
        return gameLayout;
    }

    /**
     * Initializes game, takes user inputs, and logs the results to the console.
     */
    private void playGame() {

        //Initializes game
        currentRoom = gameLayout.getStartingRoom();
        System.out.println(currentRoom.getDescription());
        System.out.println("Your journey begins here");
        System.out.println(currentRoom.printAllDirections());
        System.out.println(currentRoom.printAllItems());

        //Scanner is used for recording user input
        Scanner scanner = new Scanner(System.in);

        //Game ends when the player contains their objective
        while (!player.getItems().contains(gameLayout.getItemObjective())) {

            //Lists the directions a user can travel
            String userInput = scanner.nextLine();

            if (userInputResponse(userInput) instanceof Room) {
                System.out.println(currentRoom.printAllDirections());
                System.out.println(currentRoom.printAllItems());
            }

        }

    }

    public Object userInputResponse(String userInput) {

        if (userInput.length() <= 3) {

            System.out.println("I don't understand " + userInput);

        } else if (userInput.equalsIgnoreCase("exit")
                || userInput.equalsIgnoreCase("quit")) {

            System.exit(1);
            return null;

        } else if (userInput.substring(0, 3).equalsIgnoreCase("GO ")) {

            Room tempCurrentRoom = goDirectionResponse(userInput);
            if (tempCurrentRoom != null) {

                currentRoom = tempCurrentRoom;
                System.out.println("\n-------------------------------------------------\n");
                System.out.println(currentRoom.getDescription());

                if (currentRoom.getMonster() != null) {

                    if (!player.playerHasMonsterRepellent(currentRoom.getMonster())) {
                        System.out.println("You encountered a " + currentRoom.getMonster().name + " and died");
                        System.exit(1);
                    } else {
                        System.out.println("You encountered a monster, but defeated it");
                    }

                }

                return tempCurrentRoom;

            }

        } else if (userInput.length() <= 7) {

            System.out.println("I don't understand " + userInput);

        } else if (userInput.substring(0, 7).equalsIgnoreCase("REMOVE ")) {

            return removeItemResponse(userInput);

        } else {

            pickupItemResponse(userInput);
            if (player.getItems().contains(gameLayout.getItemObjective())) {
                System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
                System.out.println("Congratulations, you found " + gameLayout.getItemObjective().getName()
                        + " and won the game!");
                System.exit(1);
            }

        }

        return null;

    }

    /**
     * This function picks up an item based on the user input
     * @param input is the user input
     * @return the Item you are picking up, null if you can't pickup the item.
     */
    public Item pickupItemResponse(String input) {

        if (input == null || input.length() < 8) {
            return null;
        }

        if (input.substring(0, 7).equalsIgnoreCase("pickup ")) {

            for (Item item : currentRoom.getItems()) {
                if (item.getName().equalsIgnoreCase(input.substring(7))) {
                    if (player.getItems().contains(item)) {
                        System.out.println("You already have " + item.getName());
                        return item;
                    } else {
                        player.addToItems(item);
                        return item;
                    }
                }
            }

            System.out.println("I can't " + input);

        } else {
            System.out.println("I don't understand " + "'" + input + "'");
            return null;
        }

        return null;

    }

    /**
     * This function will respond to when a user asks to remove an item.
     * The function will only remove items that it can remove.
     * @param input is the user input
     * @return the item that is being removed
     */
    public Item removeItemResponse(String input) {

        if (input.substring(0, 7).equalsIgnoreCase("remove ")) {
            for (Item item : player.getItems()) {
                if (item.getName().equalsIgnoreCase(input.substring(7))) {
                    player.removeFromItems(item);
                    return item;
                }
            }

            System.out.println("I can't " + input);
            return null;
        } else {
            System.out.println("I don't understand " + "'" + input + "'");
            return null;
        }

    }

    /**
     * Determines what to do if the user wants to go somewhere
     * @param input is the input the player made in the console
     * @return a room if there is one, null if the user entered the wrong input.
     *         System exits if the user prompts it to exit.
     */
    public Room goDirectionResponse(String input) {


        if (input.substring(0, 3).equalsIgnoreCase("GO ")) {

            //looks for rooms in direction specified by user
            //if there is no room in said direction, return null
            Room tempCurrentRoom = currentRoom.findRoomsInDirection(input.substring(3));
            if (tempCurrentRoom == null) {
                //Activates when their is no room in the inputted direction
                System.out.println("I can't " + input);
                return null;
            } else if (currentRoom.roomInDirectionIsUnlocked(input.substring(3))) {
                return tempCurrentRoom;
            } else if (currentRoom.getDirectionByName(input.substring(3)).unlockWithKey(player.getItems())) {
                return tempCurrentRoom;
            } else {
                return null;
            }

        } else {
            System.out.println("I don't understand " + "'" + input + "'");
            return null;
        }

    }

}
