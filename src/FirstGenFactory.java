public class FirstGenFactory implements PokemonFactory {

    @Override
    public Pokemon[] createPokemons() {
        return new FirstGenPokemon[] {
                new FirstGenPokemon("Bulbasaur", "bulbasaur.png", new int[] {45, 49, 49, 65, 65, 45}, new String[] {"Grass", "Poison"}, "Overgrow"),
                new FirstGenPokemon("Charmander", "charmander.png", new int[] {39, 52, 43, 60, 50, 65}, new String[] {"Fire"}, "Blaze"),
                new FirstGenPokemon("Squirtle", "squirtle.png", new int[] {44, 48, 65, 50, 64, 43}, new String[] {"Water"}, "Torrent")
        };
    }

    @Override
    public Pokeball[] createPokeballs() {
        return new Pokeball[] {
                new FirstGenPokeball("Poke Ball", "pokeball.png", "A device for catching wild Pokémon.", 200),
                new FirstGenPokeball("Great Ball", "greatball.png", "A good, high-performance Ball.", 600),
                new FirstGenPokeball("Ultra Ball", "ultraball.png", "An ultra-high-performance Ball.", 1200)
        };
    }

    @Override
    public Berry[] createBerrys() {
        return new FirstGenBerry[] {
                new FirstGenBerry("Oran Berry", "oranberry.png", "Restores 10 HP when HP drops to half or less.", 3),
                new FirstGenBerry("Sitrus Berry", "sitrusberry.png", "Restores 30 HP when HP drops to half or less.", 6),
                new FirstGenBerry("Lum Berry", "lumberry.png", "Cures any status conditions.", 8)
        };
    }
}