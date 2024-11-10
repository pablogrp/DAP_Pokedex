public class FirstGenPokemon implements Pokemon {
    private String name;
    private String sprite;
    private int[] stats;
    private String[] types;
    private String mechanic;

    public FirstGenPokemon(String name, String sprite, int[] stats, String[] types, String mechanic) {
        this.name = name;
        this.sprite = sprite;
        this.stats = stats;
        this.types = types;
        this.mechanic = mechanic;
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
        return mechanic;
    }
}