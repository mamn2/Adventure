import java.util.Arrays;

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

    //Array containing all the items inside this room.
    private Item[] items;

    //Contains the monster in the room (if one exists).
    private Monster monster;

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
     * Getter for the room monster
     * @return the monster in the room
     */
    public Monster getMonster() {
        return monster;
    }

    /**
     * Getter for all items in the room
     * @return all the items inside the room
     */
    public Item[] getItems() {
        return items;
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
     * Setter for all item in the room
     * @param items all the items in the room
     */
    public void setItems(Item[] items) {
        this.items = items;
    }

    /**
     * Setter for the room monster
     * @param monster is the monster you are setting
     */
    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    /**
     * Finds a room that is in the direction inputted.
     * @param travelDirection the direction the user wants to travel.
     * @return a room that is in the travelling direction, if there is none it returns null.
     */
    public Room findRoomsInDirection(String travelDirection) {

        //ensures neither the current room or travelDirection are null
        if (travelDirection != null) {
            //iterates through the possible directions you can travel to from current room
            for (Direction direction : possibleDirections) {
                //finds a room that it is in that direction
                if (direction.getDirectionName().toUpperCase().equals(travelDirection.toUpperCase())) {
                    return direction.getRoomAhead();
                }
            }
        }

        return null;

    }

    public boolean roomInDirectionIsUnlocked(String travelDirection) {

        //ensures neither the current room or travelDirection are null
        if (travelDirection != null) {
            //iterates through the possible directions you can travel to from current room
            for (Direction direction : possibleDirections) {
                //finds a room that it is in that direction
                if (direction.getDirectionName().toUpperCase().equals(travelDirection.toUpperCase())) {
                    return direction.isUnlocked();
                }
            }
        }

        return false;

    }

    public Direction getDirectionByName(String directionName) {
        if (directionName == null) {
            return null;
        }

        for (Direction direction : possibleDirections) {
            if (direction.getDirectionName().toUpperCase().equals(directionName.toUpperCase())) {
                return direction;
            }
        }

        return null;
    }

    /**
     * Finds the potential directions that a player can move given their current location.
     * @return a String of all the locations they can go to.
     */
    public String printAllDirections() {

        StringBuilder roomList = new StringBuilder();
        roomList.append("From here you can go: ");

        for (int i = 0; i < possibleDirections.length; i++) {

            if (i == 0) {
                roomList.append(possibleDirections[0].getDirectionName());
                if (possibleDirections.length > 1) {
                    roomList.append(", ");
                }
            } else if (i != possibleDirections.length - 1) {
                roomList.append(possibleDirections[i].getDirectionName());
                roomList.append(", ");
            } else {
                roomList.append("or ");
                roomList.append(possibleDirections[i].getDirectionName());
            }

        }

        return roomList.toString();

    }

    public String printAllItems() {

        StringBuilder itemList = new StringBuilder();
        itemList.append("From here you can pickup: ");

        for (int i = 0; i < items.length; i++) {

            if (i == 0) {
                itemList.append(items[0].getName());
                if (items.length > 1) {
                    itemList.append(", ");
                }
            } else if (i != items.length - 1) {
                itemList.append(items[i].getName());
                itemList.append(", ");
            } else {
                itemList.append("or ");
                itemList.append(items[i].getName());
            }

        }

        return itemList.toString();

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
}
