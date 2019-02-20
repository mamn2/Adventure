import java.util.List;

/**
 * State of the potential directions a room permits access to.
 */
public class Direction {

    //Must be North, South, East, Northeast, etc.
    private String directionName;

    //The room in the direction you are facing
    private Room roomAhead;

    //Checks if the room is locked or not.
    private boolean unlocked;

    //Array containing the valid key names that are needed to unlock this room
    private Item[] necessaryKeys;

    //Created for deserialization purposes, use "roomAhead"
    public String roomAheadName;

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
     * Checks if the room in the direction is locked or not
     * @return true if the room is locked, false if it is not.
     */
    public boolean isUnlocked() {
        return unlocked;
    }

    /**
     * Checks if the player can unlock this door
     * @param playerKeys all the items the player has
     * @return true if the door was unlocked, false if it wasn't.
     */
    public boolean unlockWithKey(List<Item> playerKeys) {

        boolean containsAllNecessaryKeys = true;

        //Go through all necessary keys for unlocking the room.
        for (Item key : necessaryKeys) {

            boolean containsThisKey = false;
            //Check if the player contains this key
            for (Item playerKey : playerKeys) {
                if (playerKey.equals(key)) {
                    containsThisKey = true;
                }
            }

            //If the player does not have this key, they can't unlock the door
            if (!containsThisKey) {
                containsAllNecessaryKeys = false;
                break;
            }
        }

        if (containsAllNecessaryKeys) {
            unlocked = true;
            return true;
        } else {
            System.out.println("Sorry you don't have the necessary keys to go to " + roomAheadName);
            System.out.println("You need: " + necessaryKeys[0].getName());
            return false;
        }

    }

    /**
     * Setter for unlocked
     * @param unlocked is boolean value that says if the room is locked or not
     */
    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    /**
     * Getter for room in the direction from current point.
     * @return room name that is in the direction from current point.
     */
    public Room getRoomAhead() {
        return roomAhead;
    }

    /**
     * Setter for room variable.
     * @param room in the direction of the current location.
     */
    public void setRoomAhead(Room room) {
        this.roomAhead = room;
    }

    /**
     * Setter for necessary keys
     */
    public void setNecessaryKeys(Item[] keys) {
        this.necessaryKeys = keys;
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
                && otherDirection.getRoomAhead().equals(this.roomAhead);

    }

}

