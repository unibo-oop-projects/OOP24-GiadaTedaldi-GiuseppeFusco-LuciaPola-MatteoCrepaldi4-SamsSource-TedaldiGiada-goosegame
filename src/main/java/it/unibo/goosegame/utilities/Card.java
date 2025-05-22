package it.unibo.goosegame.utilities;

/**
 * Enum representing all possible cards in the game.
 */
public enum Card {
    /** Example bonus card 1. */
    NAME1("Dora the Zdora", "description1", 4, false, false, false),
    /** Example bonus card 2. */
    NAME2("Franco Letame", "description2", 6, false, false, false),
    /** Example bonus card 3. */
    NAME3("Furio", "description3", 10, false, false, false),
    /** Example bonus card 4. */
    NAME4("Pijacked", "description4", 8, true, true, false),
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
    REMOVE_OPPONENT("Remove opponent", "Removes all cards from the opponent", 0, true, true, true),
    /** Removes all your own cards. */
    REMOVE_SELF("Remove own", "Removes all your own cards", 0, false, false, true);

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
