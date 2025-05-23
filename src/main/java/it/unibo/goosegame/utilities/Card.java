package it.unibo.goosegame.utilities;

/**
 * Enum representing all possible cards in the game.
 */
public enum Card {
    /** Example malus card 1. */
    NAME1("Dora the Zdora", "Dora, the iconic Romagnol housewife," 
    + "hears your honk from the garden."
    + "Inspired, she dreams up a new recipe… with goose in it!"
    + "She waddles after you for 2 spaces, but quickly gives up, out of breath.",
     2, false, false, false),
    /** Example malus card 2. */
    NAME2("Franco the Fuming Farmer", "Franco Letame has anger issues… and more." 
    + "You interrupted his “session” with a loud honk in the garden. Enraged," 
    + "he charges after you — but after 4 spaces of furious pursuit, he trips over his own trousers and faceplants into the dirt."
    + "You still move back 4 spaces… but at least he’s out of the game for now.",
     4, false, false, false),
    /** Example malus card 3. */
    NAME3("Furio", "description3", 10, false, false, false),
    /** Example bonus card 4. */
    NAME4("Pijacked", "Unleash a furious flock of pigeons on your opponent!" 
    + "Pecking relentlessly, the birds drive them back 6 spaces in sheer panic and feathered fury.",
     6, true, true, false),
    /** Example throwable malus card. */
    NAME5("Name5", "description5", 4, false, true, false),
    /** Example bonus card 5. */
    NAME6("Name6", "description6", 4, true, false, false),
    /** Example bonus card 6. */
    NAME7("Name7", "description7", 4, true, false, false),
    /** Example bonus card 7. */
    NAME8("Name8", "description8", 4, true, false, false),
    /** Example bonus card 8. */
    NAME9("Name9", "description9", 4, true, false, false),
    /** Example bonus card 9. */
    NAME10("Name10", "description10", 4, true, false, false),
    /** Example bonus card 10. */
    NAME11("Name11", "description11", 4, true, false, false),
    /** Example non-throwable malus card. */
    NAME12("Name12", "description12", 4, false, false, false),
    /** Example bonus card 11. */
    NAME13("Name13", "description13", 4, true, false, false),
    /** Example throwable malus card 2. */
    NAME14("Name14", "description14", 4, false, true, false),
    /** Example bonus card 12. */
    NAME15("Name15", "description15", 4, true, false, false),
    /** Removes all cards from the opponent. */
    REMOVE_OPPONENT("Goblin Hexmage", "This mischievous goblin wizard unleashes a powerful hex," 
    + "banishing all of your opponent’s cards" 
    + " The affected player loses their entire hand", 0, true, true, true),
    /** Removes all your own cards. */
    REMOVE_SELF("Sneaky Goblin Thief", "This slippery goblin vanishes with everything you've got" 
    + "Discard your entire deck — every last card disappears into the shadows.",
     0, false, false, true);

    private final String name;
    private final String description;
    private final int steps;
    private final boolean bonus;
    private final boolean throwable;
    private final boolean remove;

    /**
     * Card constructor.
     * @param name the name of the card
     * @param description the effect description
     * @param steps steps (if remove is true, it is forced to 0)
     * @param isBonus true if it is a bonus card
     * @param throwable true if it can be discarded/thrown (special rules if remove)
     * @param remove true if it is a card that removes all cards (own or opponent's)
     */
    Card(final String name, final String description, final int steps,
     final boolean isBonus, final boolean throwable, final boolean remove) {
        this.name = name;
        this.description = description;
        // If remove is true, steps must be 0
        this.steps = remove ? 0 : steps;
        this.bonus = isBonus;
        this.remove = remove;
        if (remove) {
            // If remove is true, set throwable based on bonus
            this.throwable = isBonus;
        } else {
            // If not remove, normal rule: bonus cannot be throwable
            this.throwable = !isBonus && throwable;
        }
    }

    /** @return the name of the card */
    public String getName() { 
        return name; }
    /** @return the description of the card */
    public String getDescription() {
         return description; }
    /** @return the steps associated with the card */
    public int getSteps() {
         return steps; }
    /** @return true if the card is a bonus */
    public boolean isBonus() {
         return bonus; }
    /** @return true if the card is throwable */
    public boolean isThrowable() {
         return throwable; }
    /** @return true if the card is of type remove */
    public boolean isRemove() {
         return remove; }
}
