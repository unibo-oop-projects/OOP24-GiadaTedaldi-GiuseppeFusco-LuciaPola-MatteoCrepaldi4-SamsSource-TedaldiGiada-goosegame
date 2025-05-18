package it.unibo.goosegame.utilities;

public enum Card {
    NAME1("Nome1", "descrizione", 4, true, false, false), 
    NAME2("Nome2", "descrizione", 4, true, false, false), 
    NAME3("Nome3", "descrizione", 4, true, false, false), 
    NAME4("Nome4", "descrizione", 4, true, false, false), 
    NAME5("Nome5", "descrizione", 4, false, true, false),
    NAME6("Nome6", "descrizione", 4, true, false, false), 
    NAME7("Nome7", "descrizione", 4, true, false, false), 
    NAME8("Nome8", "descrizione", 4, true, false, false), 
    NAME9("Nome9", "descrizione", 4, true, false, false),
    NAME10("Nome10", "descrizione", 4, true, false, false), 
    NAME11("Nome11", "descrizione", 4, true, false, false), 
    NAME12("Nome12", "descrizione", 4, false, false, false), 
    NAME13("Nome13", "descrizione", 4, true, false, false),
    NAME14("Nome14", "descrizione", 4, false, true, false), 
    NAME15("Nome15", "descrizione", 4, true, false, false),
    // Carte speciali con remove
    REMOVE_OPPONENT("Rimuovi avversario", "Rimuove tutte le carte all'avversario", 0, true, true, true),
    REMOVE_SELF("Rimuovi proprie", "Rimuove tutte le proprie carte", 0, false, false, true);

    private final String name;
    private final String description;
    private final int steps;
    private final boolean bonus;
    private final boolean throwable;
    private final boolean remove;

    /**
     * @param name nome della carta
     * @param description descrizione dell'effetto
     * @param steps passi (se remove è true, viene forzato a 0)
     * @param isBonus true se è bonus
     * @param throwable true se può essere scartata/lanciata (regole speciali se remove)
     * @param remove true se è una carta che rimuove tutte le carte (proprie o avversario)
     */
    Card(String name, String description, int steps, boolean isBonus, boolean throwable, boolean remove) {
        this.name = name;
        this.description = description;
        // Se remove è true, steps deve essere 0
        this.steps = remove ? 0 : steps;
        this.bonus = isBonus;
        this.remove = remove;
        if (remove) {
            // Se remove è true, imposto throwable in base a bonus
            this.throwable = isBonus ? true : false;
        } else {
            // Se non è remove, regola normale: bonus non può essere throwable
            this.throwable = isBonus ? false : throwable;
        }
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getSteps() { return steps; }
    public boolean isBonus() { return bonus; }
    public boolean isThrowable() { return throwable; }
    public boolean isRemove() { return remove; }
}

