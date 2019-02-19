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

    /**
     * Initializes a new Adventure game through user input.
     */
    public static void main(String[] args) {

        System.out.println("Enter a URL containing information about the game, press space after writing URL: ");
        Scanner scanner = new Scanner(System.in);

        Adventure adventure = null;

        //loops until user initializes adventure.
        while (adventure == null){
            adventure = initialize(scanner.nextLine());
        }

        adventure.playGame();

    }

    /**
     * Initializes a new Adventure game to ensure a user cannot create an invalid instance of Adventure.
     * @param url is a url containing a JSON of the Adventure structure.
     * @return a new Adventure class, or null if it is invalid.
     */
    public static Adventure initialize(String url) {

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

        JsonArray rooms = jsonData.get("rooms").getAsJsonArray();
        Room[] roomsArray = new Room[rooms.size()];

        //Goes through all the rooms in the JSON and converts them into an array of type Room.
        for (int i = 0; i < roomsArray.length; i++) {

            //takes all fields from JSON and uses them to initialize fields in Room class.
            roomsArray[i] = new Gson().fromJson(rooms.get(i), Room.class);

            //Stores all directions for room in a JsonArray.
            JsonArray directions = rooms.get(i).getAsJsonObject().get("directions").getAsJsonArray();
            Direction[] directionsInRoom = new Direction[directions.size()];

            //Goes through all the directions in each room and converts them into an array.
            for (int j = 0; j < directionsInRoom.length; j++) {
                //takes all fields from JSON and uses to initialize fields in Direction class.
                directionsInRoom[j] = new Gson().fromJson(directions.get(j), Direction.class);
                directionsInRoom[j].roomAheadName = directions.get(j).getAsJsonObject().get("room").getAsString();
            }
            roomsArray[i].setPossibleDirections(directionsInRoom);

        }

        //sets all fields of the Layout class in the gameLayout instance
        gameLayout.setAllRooms(roomsArray);
        gameLayout.setStartingRoom(gameLayout.getRoomByName(jsonData.get("startingRoom").getAsString()));
        gameLayout.setEndingRoom(gameLayout.getRoomByName(jsonData.get("endingRoom").getAsString()));

        //Sets all rooms that a direction points to
        for (Room room: gameLayout.getAllRooms()) {
            for (Direction direction : room.getPossibleDirections()) {
                direction.setRoomAhead(gameLayout.getRoomByName(direction.roomAheadName));
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
        Room currentRoom = gameLayout.getStartingRoom();
        System.out.println(currentRoom.getDescription());
        System.out.println("Your journey begins here");

        //Scanner is used for recording user input
        Scanner scanner = new Scanner(System.in);

        while (currentRoom != gameLayout.getEndingRoom()) {

            //Lists the directions a user can travel
            System.out.println(currentRoom.printAllDirections());

            Room tempCurrentRoom = null;
            while (tempCurrentRoom == null) {
                tempCurrentRoom = userInputResponse(scanner.nextLine(), currentRoom);
            }

            //Since the travel direction is possible, we move the user to a new room
            currentRoom = tempCurrentRoom;
            System.out.println(currentRoom.getDescription());

        }

    }

    /**
     * Determines what steps to take given the user input. Separated for testing purposes.
     * @param input is the input the player made in the console
     * @param currentRoom is the current room the user is in
     * @return a room if there is one, null if the user entered the wrong input.
     *         System exits if the user prompts it to exit.
     */
    public Room userInputResponse(String input, Room currentRoom) {

        if (input == null || input.length() < 3) {
            return null;
        }

        //Tests for base case exit or quit, case insensitive
        if (input.toUpperCase().equals("EXIT") || input.toUpperCase().equals("QUIT")) {
            System.exit(1);
            //Never executed, but needed by compiler
            return null;
        } else if (input.substring(0, 3).toUpperCase().equals("GO ")) {
            //looks for rooms in direction specified by user
            //if there is no room in said direction, return null
            Room tempCurrentRoom = currentRoom.findRoomsInDirection(input.substring(3));
            if (tempCurrentRoom == null) {
                //Activates when their is no room in the inputted direction
                System.out.println("I can't " + input);
                return null;
            } else if (tempCurrentRoom.equals(gameLayout.getEndingRoom())) {
                //reaching the last room finishes the game
                System.out.println(tempCurrentRoom.getDescription());
                System.out.println("You have reached your final destination.");
                System.exit(1);
                //Never executed, but needed by compiler
                return null;
            } else {
                return tempCurrentRoom;
            }
        } else {
            System.out.println("I don't understand " + "'" + input + "'");
            return null;
        }

    }

}
