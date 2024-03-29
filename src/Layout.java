/**
 * Represents the layout of the game including all the rooms, directions, etc.
 */
public class Layout {

    //Name of the first starting point in game
    private Room startingRoom;

    //Name of the last point in the game, reaching this point terminates the game
    private Room endingRoom;

    //The item that the player needs to win the game
    private Item itemObjective;

    //Consists of all the rooms in the game
    private Room[] allRooms;

    /**
     * Getter for name of the starting room.
     * @return the name of the first room.
     */
    public Room getStartingRoom() {
        return startingRoom;
    }

    /**
     * Setter for starting room in the game.
     * @param startingRoom name of the starting room being set.
     */
    public void setStartingRoom(Room startingRoom) {
        this.startingRoom = startingRoom;
    }

    /**
     * Getter for name of the ending room.
     * @return the name of the last room.
     */
    public Room getEndingRoom() {
        return endingRoom;
    }

    /**
     * Setter for the name of the ending room.
     * @param endingRoom the name of the last room being set.
     */
    public void setEndingRoom(Room endingRoom) {
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
     * Searches if any of the rooms has the name passed in.
     * If it does, it returns that room.
     * @param name name of the room being searched, case sensitive.
     * @return room that was found, if none was found it returns null
     */
    public Room getRoomByName(String name) {

        if (name == null || allRooms == null) {
            return null;
        }

        for (Room room : allRooms) {
            if (room.getName().equals(name)) {
                return room;
            }
        }

        return null;

    }

    /**
     * Getter for item objective
     * @return gets the item needed to win the game
     */
    public Item getItemObjective() {
        return itemObjective;
    }

    /**
     * Setter for item objective
     * @param itemObjective the item you want to win the game.
     */
    public void setItemObjective(Item itemObjective) {
        this.itemObjective = itemObjective;
    }


}
