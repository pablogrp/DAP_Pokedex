package pokedex.datastructure.products;

public class FirstGenPokemon implements Pokemon {
    private String name;
    private String sprite;
    private int[] stats;
    private String[] types;
    private String mechanic;

    public FirstGenPokemon(String name, String sprite, int[] stats, String[] types) {
        this.name = name;
        this.sprite = sprite;
        this.stats = stats;
        this.types = types;
        this.mechanic = "None";
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