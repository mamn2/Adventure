# Development Progress

----
## Fixes to make to original game:

1) Move main method to the top of the class
2) Replace inner classes with individual classes

----
## New rules for this game:

1) Player can win the game if they collect the winning item.
2) Player can carry a max of 3 items
3) Player can encounter monsters in certain rooms. In order to beat the monster, they need a certain item in their posession.

----
## Development additions for new features

###
Direction.class

1) Direction class now includes a boolean value that represents whether or not it is possible to move in a certain direction

2) Class now contains an array of Strings with the names of valid keys that you can use to unlock the direction

###
Item.class

1) Item is a new class that represent an object that the player has such as a key which can unlock a direction.

###
Room.class

1) Rooms now contain a list of items that a user can pickup.

###
Player.class

1) Player is a new class representing a player in the game. The player has a list of items (keys) that can be used for unlocking doors.

###
Layout.class

1) Layout class now contains a player.

###
Monster.class

1) The new monster class contains the name of the monster and the necessary repellent (Item) to survive the battle.
