public class FifthGenPokemon implements Pokemon {
    //Private atrributes
    private String name;
    private String sprite;
    private int[] stats;
    private String[] types;
    private String mechanic;

    //Constructor
    public FifthGenPokemon(String name, String sprite, int[] stats, String[] types, String mecha) {
        this.name = name;
        this.sprite = sprite;
        this.stats = stats;
        this.types = types;
        this.mechanic = mecha;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSprite() {
        return sprite;
    }

    @Override
    public int[] getStats() {
        return stats;
    }

    @Override
    public String[] getTypes() {
        return types;
    }

    @Override
    public String getMechanic() {
        return "I can mega evolve!";
    }
}