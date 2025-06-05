package it.unibo.goosegame.utilities;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Enum representing all possible cards in the game.
 */
public enum Card {
    /** Example card 1. */
    NAME1("Dora the Zdora", "Dora, the iconic Romagnol housewife," 
    + "hears your honk from the garden."
    + "Inspired, she dreams up a new recipe… with goose in it!"
    + "She waddles after you for 2 spaces, but quickly gives up, out of breath.",
     2, false, false),
    /** Example card 2. */
    NAME2("Franco the Fuming Farmer", "Franco Letame has anger issues… and more." 
    + "You interrupted his “session” with a loud honk in the garden. Enraged," 
    + "he charges after you — but after 4 spaces of furious pursuit, he trips over his own trousers and faceplants into the dirt."
    + "You still move back 4 spaces… but at least he’s out of the game for now.",
     4, false, false),
    /** Example card 3. */
    NAME3("Furio", "You heard a low growl. You turned around." 
    + "There he was: Furio — eyes locked, tail still, judgment activated." 
    + "You ran. He ran faster." 
    + "You flapped. He lunged." 
    + "Some say you're still honking in fear." 
    + "You may have escaped, but only after losing 5 spaces… and your will to honk. You move back 5 spaces", 5, false, false),
    /** Example card 5. */
    NAME5("Goose Launcher", "Ever dreamt of launching a goose into space?" 
    + "Hop on the Goose Launcher — or just a trampoline? — and leap 4 spaces ahead!", 4, true, false),
    /** Example card 6. */
    NAME6("Wobble Ladder", "So tall it scrapes the clouds," 
    + "so wobbly it makes your feathers twitch. Move ahead 2 spaces", 2, true, false),
    /** Example card 7. */
    NAME7("The Backflap Gap", "After waddling along that endless fence," 
    + "you spot it — freedom at last! Move ahead 2 spaces", 2, true, false),
    /** Example card 12. */
    NAME12("Tailwind Boost", "A sudden gust hits your tail feathers just right." 
    + "No idea where it came from — but hey, three free spaces. Don’t question wind miracles. Move ahead 3 spaces", 3, true, false),
    /** Example card 13. */
    NAME13("Panic Skip", "You hear something… maybe Furio?" 
    + "You panic-flap forward, heart racing — no time to check." 
    + "Move ahead 4 spaces.", 4, true, false),
    /** Example card 14. */
    NAME14("Speed Waddle", "You enter the zone." 
    + "Your waddle sharpens — swift, aerodynamic." 
    + "You’re no longer a goose. You’re a land torpedo." 
    + "Move ahead 5 spaces.", 5, true, false),
    /** Example card 15. */
    NAME15("Wrong Way", "You try squeezing through a suspicious gap in the fence." 
    + "You get stuck. Wiggle, squirm, regret." 
    + "Eventually, you backtrack to find another way around" 
    + "feathers slightly ruffled, pride fully dented. You move back 3 spaces", 3, false, false),
    /** Removes all your own cards. */
    REMOVE("Sneaky Goblin Thief", "This slippery goblin vanishes with everything you've got" 
    + "Discard your entire deck — every last card disappears into the shadows.",
     0, false, true);

    private static final Random RANDOM = new Random();
    private final String name;
    private final String description;
    private final int steps;
    private final boolean bonus;
    private final boolean remove;

    /**
     * Card constructor.
     * @param name the name of the card
     * @param description the effect description
     * @param steps steps (if remove is true, it is forced to 0)
     * @param isBonus true if it is a bonus card
     * @param remove true if it is a card that removes all cards (own or opponent's)
     */
    Card(final String name, final String description, final int steps,
     final boolean isBonus, final boolean remove) {
        this.name = name;
        this.description = description;
        // If remove is true, steps must be 0
        this.steps = remove ? 0 : steps;
        this.bonus = isBonus;
        this.remove = remove;
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
    /** @return true if the card is of type remove */
    public boolean isRemove() {
         return remove; }

    /**
     * Draws a random card from the pool of cards.
     * @return a random malus card
     */
     public static Card drawMalusCard() {
        final List<Card> pool = List.of(values()).stream()
            .filter(Predicate.not(Card::isBonus))
            .collect(Collectors.toList());
        if (pool.isEmpty()) {
            return null;
        } else {
            return pool.get(RANDOM.nextInt(pool.size()));
        }
    }

    /**
     * Draws a random bonus card from the pool of cards.
     * @return a random bonus card
     */
     public static Card drawBonusCard() {
        final List<Card> pool = List.of(values()).stream()
            .filter(Card::isBonus)
            .collect(Collectors.toList());
        if (pool.isEmpty()) {
            return null;
        } else {
            return pool.get(RANDOM.nextInt(pool.size()));
        }
     }
}
