/**
 * State of the potential directions a room permits access to.
 */
public class Direction {

    //Must be North, South, East, Northeast, etc.
    private String directionName;

    //The room in the direction you are facing
    private Room roomAhead;

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

