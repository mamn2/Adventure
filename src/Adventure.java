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

public class Adventure {


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

    public Layout getGameLayout() {
        return gameLayout;
    }

    class Layout {

        private String startingRoom;
        private String endingRoom;
        private Room[] allRooms;

        public String getStartingRoom() {
            return startingRoom;
        }

        public void setStartingRoom(String startingRoom) {
            this.startingRoom = startingRoom;
        }

        public String getEndingRoom() {
            return endingRoom;
        }

        public void setEndingRoom(String endingRoom) {
            this.endingRoom = endingRoom;
        }

        public Room[] getAllRooms() {
            return allRooms;
        }

        public void setAllRooms(Room[] allRooms) {
            this.allRooms = allRooms;
        }

        public Room createNewRoom() {
            return new Room();
        }

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

        class Room {

            private String name;
            private String description;
            private Direction[] possibleDirections;

            public String getName() {
                return name;
            }

            public String getDescription() {
                return description;
            }

            public Direction[] getPossibleDirections() {
                return possibleDirections;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setPossibleDirections(Direction[] possibleDirections) {
                this.possibleDirections = possibleDirections;
            }

            public Direction createNewDirection() {
                return new Direction();
            }

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

            class Direction {

                //Must be north, south, east, or west
                private String directionName;

                //The room in the direction you are facing
                private String room;

                public String getDirectionName() {
                    return directionName;
                }

                public void setDirectionName(String directionName) {
                    this.directionName = directionName;
                }

                public String getRoom() {
                    return room;
                }

                public void setRoom(String room) {
                    this.room = room;
                }

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
