public interface PokemonFactory {
//    Pokemon[] createPokemons();
//    Pokeball[] createPokeballs();
//    Berry[] createBerrys();

    Pokemon createPokemon(String name, String sprite, int[] stats, String[] types, String mechanic);
    Pokeball createPokeball(String name, String sprite, String effect, int price);
    Berry createBerry(String name, String sprite, String effect, int growthTime);
}
