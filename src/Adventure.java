import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

/**
 * This class represents the state and behavior of an Adventure game.
 */
public class Adventure {

    //Layout of the games locations, directions, rooms, etc.
    private Layout gameLayout;

    /**
     * This class deserializes a JSON object from a URL to form an Adventure class.
     * @param jsonURL a URL that links to a JSON file.
     * @throws IOException if the URL does not exist or does not link a JSON file.
     */
    public Adventure(URL jsonURL) throws IOException {

        //Connecting to the URL
        URLConnection request = jsonURL.openConnection();
        request.connect();

        //code below derived from:
        //https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
        //Gets JSON file from a URL
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonData = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()))
                                                .getAsJsonObject();

        gameLayout = new Layout();
        gameLayout.setStartingRoom(jsonData.get("startingRoom").getAsString());
        gameLayout.setEndingRoom(jsonData.get("endingRoom").getAsString());

        JsonArray rooms = jsonData.get("rooms").getAsJsonArray();
        Layout.Room[] roomsArray = new Layout.Room[rooms.size()];
        //Goes through all the rooms in the JSON and converts them into an array of type Room.
        for (int i = 0; i < roomsArray.length; i++) {
            //takes all fields from JSON and uses them to initialize fields in room class.
            roomsArray[i] = new Gson().fromJson(rooms.get(i), Layout.Room.class);

            JsonArray directions = rooms.get(i).getAsJsonObject().get("directions").getAsJsonArray();
            Layout.Room.Direction[] directionsInRoom = new Layout.Room.Direction[directions.size()];
            //Goes through all the directions in each room and converts them into an array.
            for (int j = 0; j < directionsInRoom.length; j++) {
                //takes all fields from JSON and uses to initialize fields in Direction class.
                directionsInRoom[j] = new Gson().fromJson(directions.get(j), Layout.Room.Direction.class);
            }
            roomsArray[i].setPossibleDirections(directionsInRoom);
        }
        gameLayout.setAllRooms(roomsArray);

    }

    /**
     * Getter for gameLayout instance variable
     * @return the layout for this instance of Adventure
     */
    public Layout getGameLayout() {
        return gameLayout;
    }

    /**
     * Represents the layout of the game including all the rooms, directions, etc.
     */
    public class Layout {

        //Name of the first starting point in game
        private String startingRoom;

        //Name of the last point in the game, reaching this point terminates the game
        private String endingRoom;

        //Consists of all the rooms in the game
        private Room[] allRooms;

        /**
         * Getter for name of the starting room.
         * @return the name of the first room.
         */
        public String getStartingRoom() {
            return startingRoom;
        }

        /**
         * Setter for starting room in the game.
         * @param startingRoom name of the starting room being set.
         */
        public void setStartingRoom(String startingRoom) {
            this.startingRoom = startingRoom;
        }

        /**
         * Getter for name of the ending room.
         * @return the name of the last room.
         */
        public String getEndingRoom() {
            return endingRoom;
        }

        /**
         * Setter for the name of the ending room.
         * @param endingRoom the name of the last room being set.
         */
        public void setEndingRoom(String endingRoom) {
            this.endingRoom = endingRoom;
        }

        /**
         * Getter for all the rooms within a layout.
         * @return all rooms in the layout.
         */
        public Room[] getAllRooms() {
            return allRooms;
        }

        /**
         * Setter for all the rooms within a layout.
         * @param allRooms is all the rooms in the layout being set.
         */
        public void setAllRooms(Room[] allRooms) {
            this.allRooms = allRooms;
        }

        /**
         * Creates a new room of this instance.
         * Note: this does not add to the Room array.
         * @return a new room of the current instance of Layout
         */
        public Room createNewRoom() {
            return new Room();
        }

        /**
         * Equality checker for layouts.
         * @param other is the other object it is being compared to.
         * @return true if the objects are equal, false otherwise.
         */
        @Override
        public boolean equals(Object other) {

            if (!(other instanceof Layout)) {
                return false;
            }

            Layout otherLayout = (Layout) other;
            return otherLayout.getStartingRoom().equals(this.startingRoom)
                    && otherLayout.getEndingRoom().equals(this.endingRoom)
                    && Arrays.deepEquals(otherLayout.getAllRooms(), this.allRooms);

        }

        /**
         * This class represents the state and behavior of a Room in the Adventure game.
         */
        public class Room {

            //Name of the room
            private String name;

            //A description of the room.
            private String description;

            //All the possible directions the player can travel
            private Direction[] possibleDirections;

            /**
             * Getter for name of the room.
             * @return the name of the room.
             */
            public String getName() {
                return name;
            }

            /**
             * Getter for room description.
             * @return the room description.
             */
            public String getDescription() {
                return description;
            }

            /**
             * Getter for all the possible directions a user can travel.
             * @return all possible directions a user can travel.
             */
            public Direction[] getPossibleDirections() {
                return possibleDirections;
            }

            /**
             * Setter for name of the room.
             * @param name name of the room being set.
             */
            public void setName(String name) {
                this.name = name;
            }

            /**
             * Setter for a description of the room.
             * @param description a description of the room being set.
             */
            public void setDescription(String description) {
                this.description = description;
            }

            /**
             * Setter for all possible directions a user can travel.
             * @param possibleDirections is all the possible directions a user can travel.
             */
            public void setPossibleDirections(Direction[] possibleDirections) {
                this.possibleDirections = possibleDirections;
            }

            /**
             * Creates a new direction of this instance of room.
             * Note: This does not add to the Direction array.
             * @return a new direction of this instance of Room.
             */
            public Direction createNewDirection() {
                return new Direction();
            }

            /**
             * Tests for the equality of two rooms.
             * @param other is the other object being compared to this instance of Room.
             * @return true if they are equal, false if they are not equal.
             */
            @Override
            public boolean equals(Object other) {

                if (!(other instanceof Room)) {
                    return false;
                }

                Room otherRoom = (Room) other;
                return otherRoom.getName().equals(this.name)
                        && otherRoom.getDescription().equals(this.description)
                        && Arrays.deepEquals(otherRoom.getPossibleDirections(), this.possibleDirections);

            }

            /**
             * State of the potential directions a room permits access to.
             */
            public class Direction {

                //Must be North, South, East, Northeast, etc.
                private String directionName;

                //The room in the direction you are facing
                private String room;

                /**
                 * Gets the direction name (North, South, East, etc.)
                 * @return direction.
                 */
                public String getDirectionName() {
                    return directionName;
                }

                /**
                 * Setter for directionName
                 * @param directionName name of the direction (South, Southeast, etc)
                 */
                public void setDirectionName(String directionName) {
                    this.directionName = directionName;
                }

                /**
                 * Getter for room in the direction from current point.
                 * @return room name that is in the direction from current point.
                 */
                public String getRoom() {
                    return room;
                }

                /**
                 * Setter for room variable.
                 * @param room in the direction of the current location.
                 */
                public void setRoom(String room) {
                    this.room = room;
                }

                /**
                 * Tests equality of 2 Direction objects
                 * @param other is the other object being compared.
                 * @return true if they are equal, false if they are not
                 */
                @Override
                public boolean equals(Object other) {

                    if (!(other instanceof Direction)) {
                        return false;
                    }

                    Direction otherDirection = (Direction) other;

                    return otherDirection.getDirectionName().equals(this.directionName)
                            && otherDirection.getRoom().equals(this.room);

                }

            }

        }

    }



}
