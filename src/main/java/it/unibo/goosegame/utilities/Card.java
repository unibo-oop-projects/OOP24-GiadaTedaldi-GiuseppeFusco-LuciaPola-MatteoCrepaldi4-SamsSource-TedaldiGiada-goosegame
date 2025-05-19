package it.unibo.goosegame.utilities;

/**
 * Enum representing all possible cards in the game.
 */
public enum Card {
    /** Carta bonus di esempio 1. */
    NAME1("Nome1", "descrizione1", 4, true, false, false),
    /** Carta bonus di esempio 2. */
    NAME2("Nome2", "descrizione2", 4, true, false, false),
    /** Carta bonus di esempio 3. */
    NAME3("Nome3", "descrizione3", 4, true, false, false),
    /** Carta bonus di esempio 4. */
    NAME4("Nome4", "descrizione4", 4, true, false, false),
    /** Carta malus throwable di esempio. */
    NAME5("Nome5", "descrizione5", 4, false, true, false),
    /** Carta bonus di esempio 5. */
    NAME6("Nome6", "descrizione6", 4, true, false, false),
    /** Carta bonus di esempio 6. */
    NAME7("Nome7", "descrizione7", 4, true, false, false),
    /** Carta bonus di esempio 7. */
    NAME8("Nome8", "descrizione8", 4, true, false, false),
    /** Carta bonus di esempio 8. */
    NAME9("Nome9", "descrizione9", 4, true, false, false),
    /** Carta bonus di esempio 9. */
    NAME10("Nome10", "descrizione10", 4, true, false, false),
    /** Carta bonus di esempio 10. */
    NAME11("Nome11", "descrizione11", 4, true, false, false),
    /** Carta malus non throwable di esempio. */
    NAME12("Nome12", "descrizione12", 4, false, false, false),
    /** Carta bonus di esempio 11. */
    NAME13("Nome13", "descrizione13", 4, true, false, false),
    /** Carta malus throwable di esempio 2. */
    NAME14("Nome14", "descrizione14", 4, false, true, false),
    /** Carta bonus di esempio 12. */
    NAME15("Nome15", "descrizione15", 4, true, false, false),
    /** Rimuove tutte le carte all'avversario. */
    REMOVE_OPPONENT("Rimuovi avversario", "Rimuove tutte le carte all'avversario", 0, true, true, true),
    /** Rimuove tutte le proprie carte. */
    REMOVE_SELF("Rimuovi proprie", "Rimuove tutte le proprie carte", 0, false, false, true);

    private final String name;
    private final String description;
    private final int steps;
    private final boolean bonus;
    private final boolean throwable;
    private final boolean remove;

    /**
     * Costruttore della carta.
     * @param name nome della carta
     * @param description descrizione dell'effetto
     * @param steps passi (se remove è true, viene forzato a 0)
     * @param isBonus true se è bonus
     * @param throwable true se può essere scartata/lanciata (regole speciali se remove)
     * @param remove true se è una carta che rimuove tutte le carte (proprie o avversario)
     */
    Card(final String name, final String description, final int steps, final boolean isBonus, final boolean throwable, final boolean remove)
    {
        this.name = name;
        this.description = description;
        // Se remove è true, steps deve essere 0
        this.steps = remove ? 0 : steps;
        this.bonus = isBonus;
        this.remove = remove;
        if (remove)
        {
            // Se remove è true, imposto throwable in base a bonus
            this.throwable = isBonus;
        }
        else
        {
            // Se non è remove, regola normale: bonus non può essere throwable
            this.throwable = isBonus ? false : throwable;
        }
    }

    /** @return il nome della carta */
    public String getName() { return name; }
    /** @return la descrizione della carta */
    public String getDescription() { return description; }
    /** @return i passi associati alla carta */
    public int getSteps() { return steps; }
    /** @return true se la carta è bonus */
    public boolean isBonus() { return bonus; }
    /** @return true se la carta è throwable */
    public boolean isThrowable() { return throwable; }
    /** @return true se la carta è di tipo remove */
    public boolean isRemove() { return remove; }
}
