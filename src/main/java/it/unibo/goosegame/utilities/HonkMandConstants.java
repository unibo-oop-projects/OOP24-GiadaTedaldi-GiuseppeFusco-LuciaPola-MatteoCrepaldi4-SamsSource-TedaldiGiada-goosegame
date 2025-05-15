package it.unibo.goosegame.utilities;

/**
 * Utility class containing constants for the HonkMand minigame.
 * <p>
 * This class defines timing, animation, and game configuration constants used throughout the HonkMand game logic and UI.
 * It is not intended to be instantiated.
 */
public final class HonkMandConstants {

    /** Duration for which a button lights up on click (ms). */
    public static final int BUTTON_CLICK_LIGHT_DURATION = 300;
    /** Delay before enabling buttons after sequence (ms). */
    public static final int BUTTON_ENABLE_DELAY = 500;
    /** Duration for which a button lights up (ms). */
    public static final int BUTTON_LIGHT_DURATION = 500;
    /** Delay between each step in the celebration animation (ms). */
    public static final int CELEBRATION_STEP_DELAY = 400;
    /** Number of flashes for game over animation. */
    public static final int GAME_OVER_FLASH_COUNT = 3;
    /** Delay between each flash in game over animation (ms). */
    public static final int GAME_OVER_FLASH_DELAY = 200;
    /** Maximum level to win the game. */
    public static final int MAX_LEVEL = 5;
    /** Delay before starting the next round (ms). */
    public static final int NEXT_ROUND_DELAY = 1000;
    /** Delay before the sequence starts (ms). */
    public static final int SEQUENCE_START_DELAY = 1000;
    /** Delay between each step in the sequence (ms). */
    public static final int SEQUENCE_STEP_DELAY = 700;
    /** Delay before showing the win dialog (ms). */
    public static final int WIN_DIALOG_DELAY = 1000;

    private HonkMandConstants() { }
}
