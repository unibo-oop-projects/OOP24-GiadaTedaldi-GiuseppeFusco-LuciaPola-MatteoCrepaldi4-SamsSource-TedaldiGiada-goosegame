package it.unibo.goosegame.utilities;

public enum Card {
    NAME1("Nome1", "descrizione", 4, true, false), 
    NAME2("Nome2", "descrizione", 4, true, false), 
    NAME3("Nome3", "descrizione", 4, true, false), 
    NAME4("Nome4", "descrizione", 4, true, false), 
    NAME5("Nome5", "descrizione", 4, false, true),
    NAME6("Nome6", "descrizione", 4, true, false), 
    NAME7("Nome7", "descrizione", 4, true, false), 
    NAME8("Nome8", "descrizione", 4, true, false), 
    NAME9("Nome9", "descrizione", 4, true, false),
    NAME10("Nome10", "descrizione", 4, true, false), 
    NAME11("Nome11", "descrizione", 4, true, false), 
    NAME12("Nome12", "descrizione", 4, false, false), 
    NAME13("Nome13", "descrizione", 4, true, false),
    NAME14("Nome14", "descrizione", 4, false, true), 
    NAME15("Nome15", "descrizione", 4, true, false);

    private final String name;
    private final String description;
    private final int steps;
    private final boolean bonus;
    private final boolean throwable;

    Card(String name, String description, int steps, boolean isBonus, boolean throwable) {
        this.name = name;
        this.description = description;
        this.steps = steps;
        this.bonus = isBonus;
        this.throwable = throwable;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getSteps() { return steps; }
    public boolean isBonus() { return bonus;}
    public boolean isThrowable() { return throwable;}

}
