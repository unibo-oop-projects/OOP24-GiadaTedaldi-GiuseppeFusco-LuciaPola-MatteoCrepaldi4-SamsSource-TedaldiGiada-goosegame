package it.unibo.goosegame.utilities;

/**
 * Enum representing all possible cards in the game.
 */
public enum Card {
    /** Example card 1. */
    NAME1("Dora the Zdora", "Dora, the iconic Romagnol housewife," 
    + "hears your honk from the garden."
    + "Inspired, she dreams up a new recipe… with goose in it!"
    + "She waddles after you for 2 spaces, but quickly gives up, out of breath.",
     2, false, false, false),
    /** Example card 2. */
    NAME2("Franco the Fuming Farmer", "Franco Letame has anger issues… and more." 
    + "You interrupted his “session” with a loud honk in the garden. Enraged," 
    + "he charges after you — but after 4 spaces of furious pursuit, he trips over his own trousers and faceplants into the dirt."
    + "You still move back 4 spaces… but at least he’s out of the game for now.",
     4, false, false, false),
    /** Example card 3. */
    NAME3("Furio", "You heard a low growl. You turned around." 
    + "There he was: Furio — eyes locked, tail still, judgment activated." 
    + "You ran. He ran faster." 
    + "You flapped. He lunged." 
    + "Some say you're still honking in fear." 
    + "You may have escaped, but only after losing 10 spaces… and your will to honk.", 7, false, false, false),
    /** Example bonus card 4. */
    NAME4("Pijacked", "Unleash a furious flock of pigeons on your opponent!" 
    + "Pecking relentlessly, the birds drive them back 6 spaces in sheer panic and feathered fury.",
     6, true, true, false),
    /** Example card 5. */
    NAME5("Goose Launcher", "Ever dreamed of launching a goose into the stratosphere?" 
    + "Well, this might be your chance! Step onto the Goose Launcher" 
    + "— or maybe it's just a regular trampoline, who's to say" 
    + "— and soar 4 spaces ahead.", 4, true, false, false),
    /** Example card 6. */
    NAME6("Wobble Ladder", "So tall it scrapes the clouds..." 
    + "and so wobbly it makes your feathers twitch." 
    + "Thankfully, it’s safer than it looks! Climb with courage and move 3 spaces ahead." 
    + "Goose tested, goose approved.", 2, true, false, false),
    /** Example card 7. */
    NAME7("The Backflap Gap", "Hidden behind a suspiciously flappy piece of fence," 
    + "this sneaky little gap is your ticket to glory." 
    + "Wiggle through like a pro and skip 2 spaces ahead." 
    + "Warning: tail feathers may get ruffled.", 2, true, false, false),
    /** Example car 8. */
    NAME8("Goose Bump", "You honk into a hollow log, sending a delayed echo bouncing across the board." 
    + "The target thinks they’re being followed and flees backward 3 spaces." 
    + "Next time, they'll think twice before trusting acoustics", 3, true, false, false),
    /** Example card 9. */
    NAME9("Tailwind Trouble", "You channel every ounce of pent-up honk rage into one explosive flap." 
    + "Fueled by old rivalries, spilled birdseed, and that one time they called you a duck," 
    + "you unleash a gust so strong it launches the target backwards 6 spaces." 
    + "Feathers everywhere. Dignity nowhere.", 6, true, true, false),
    /** Example card 10. */
    NAME10("False Alarm", "You flap your wings in the distance like something’s terribly wrong." 
    + "The target freezes, then backs up cautiously," 
    + "unsure if it’s a trap or just goose drama. Target backwards 2 place",2, true, true, false),
    /** Example card 11. */
    NAME11("Psych-Honk", "You don’t do anything… yet." 
    + "You just stare, tilt your head slowly, and smile with your beak."
    + "The target overthinks every life decision and backs away 3 steps in existential panic", 3, true, false, false),
    /** Example card 12. */
    NAME12("Tailwind Boost", "A sudden gust catches your tail feathers just right." 
    + "No idea where it came from, but hey " 
    + "— three free spaces. Don’t question wind miracles.", 3, true, false, false),
    /** Example card 13. */
    NAME13("Panic Skip", "You hear something that may or may not be Furio." 
    + "You bolt forward in a panic, wings flapping, heart pounding." 
    + "Whatever it was — you're not sticking around to find out"
    + "Move forward 4 spaces", 4, true, false, false),
    /** Example card 14. */
    NAME14("Speed Waddle", "You enter the zone." 
    + "Your waddle is swift, efficient, aerodynamic." 
    + "You are no longer a goose. You are a land torpedo."
    + "Move forward 6 spaces", 6, true, false, false),
    /** Example card 15. */
    NAME15("Name15", "You try squeezing through a suspicious gap in the fence." 
    + "You get stuck. Wiggle, squirm, regret." 
    + "Eventually, you backtrack to find another way around" 
    + "feathers slightly ruffled, pride fully dented. You move back 3 spaces", 3, false, false, false),
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
