public class FifthGenPokemon implements Pokemon{

    private String name;
    private String sprite;
    private int[] stats;
    private String[] types;
    private String mechanic;

    public FifthGenPokemon(String name, String sprite, int[] stats, String[] types, String mechanic) {
        this.name = name;
        this.sprite = sprite;
        this.stats = stats;
        this.types = types;
        this.mechanic = "I can Mega Evolve!";
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }

    @Override
    public int[] getStats() {
        return this.stats;
    }

    @Override
    public String[] getTypes() {
        return this.types;
    }

    @Override
    public String getMechanic() {
        return this.mechanic;
    }

    @Override
    public void show() {
        System.out.println("Nombre: " + name);
        System.out.println("Sprite: " + sprite);
        System.out.println("Tipos: " + String.join(", ", types));
        System.out.println("Mecánica: " + mechanic);
        System.out.println("Estadísticas (HP, Ataque, Defensa, Ataque Especial, Defensa Especial, Velocidad):");
        for (int stat : stats) {
            System.out.print(stat + " ");
        }
        System.out.println();
    }
}
