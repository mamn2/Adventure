public class Item {

    private String name;

    /**
     * Constructor for creating a new Item.
     * @param name is the name of the new Item you are creating.
     */
    public Item (String name) {
        this.name = name;
    }

    /**
     * Getter for name variable
     * @return the name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Tests the equality of two Items
     * @param otherObject the other Object that we are comparing
     * @return true if the Items have the same name, false if they do not have the same name.
     */
    @Override
    public boolean equals(Object otherObject) {

        if (!(otherObject instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) otherObject;

        //Returns if the names are equal or not
        return this.name.equals(otherItem.name);

    }

}
