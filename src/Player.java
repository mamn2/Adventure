import java.util.LinkedList;

public class Player {

    //contains all the items this player has.
    private LinkedList<Item> items = new LinkedList<>();

    /**
     * Getter for list of items that the player has.
     * @return all the items the player has.
     */
    public LinkedList<Item> getItems() {
        return items;
    }

    /**
     * Add an item to the current list of items.
     * If the number of items is already too high, it will not add the item.
     * @param newItem is the item you want to add.
     */
    public void addToItems(Item newItem) {

        if (items.size() < 3) {
            System.out.println("You have picked up " + newItem.getName());
            items.add(newItem);
        } else {
            System.out.println("You can only have 3 items in this list, please remove an item to make way");
        }

    }

    /**
     * Remove an item from the current list of items.
     * If the item does not exist, it will print that it doesn't exist.
     * @param oldItem is the item you want to remove.
     */
    public void removeFromItems(Item oldItem) {

        if (items.contains(oldItem)) {
            System.out.println("You have removed " + oldItem.getName());
            items.remove(oldItem);
        } else {
            System.out.println("The player does not have the item you want to remove");
        }

    }

    public boolean checkPlayerHasMonsterRepellent(Monster monster) {
        boolean playerHasMonsterRepellent = false;
        for (Item item : items) {
            if (item.equals(monster.repellent)) {
                playerHasMonsterRepellent = true;
            }
        }
        return playerHasMonsterRepellent;
    }

}
