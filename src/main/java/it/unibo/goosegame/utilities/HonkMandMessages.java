package it.unibo.goosegame.utilities;

/**
 * Utility class containing UI messages and labels for the HonkMand minigame.
 * <p>
 * This class centralizes all user-facing strings for the HonkMand game, making localization and maintenance easier.
 * It is not intended to be instantiated.
 */
public final class HonkMandMessages {

    /** Label for the close game button. */
    public static final String CLOSE_GAME = "Close Game";
    /** Message shown when the player input is correct. */
    public static final String CORRECT = "Great!";
    /** Message shown when the game is over. */
    public static final String GAME_OVER = "Game Over!";
    /** Label for the current level. */
    public static final String LEVEL_LABEL = "Level: ";
    /** Label for the maximum level. */
    public static final String MAX_LEVEL_LABEL = "Max level: ";

    /** Label for the current score. */
    public static final String SCORE_LABEL = "Score: ";
    /** Label for the start button. */
    public static final String START_BUTTON = "Start Game";
    /** Title of the game. */
    public static final String TITLE = "HonkMand";
    /** Message for the victory dialog. */
    public static final String VICTORY_MESSAGE = "Congratulations! You won!";
    /** Title for the victory dialog. */
    public static final String VICTORY_TITLE = "Victory!";
    /** Message shown when the sequence is being displayed. */
    public static final String WATCH_SEQUENCE = "Watch the sequence!";
    /** Message shown when the player wins. */
    public static final String WIN = "You won!";
    /** Message shown when it's the player's turn. */
    public static final String YOUR_TURN = "Your turn!";

    private HonkMandMessages() { }
}
