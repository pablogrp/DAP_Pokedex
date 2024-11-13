public interface PokemonFactory {
    Pokemon createPokemon(String name, String sprite, int[] stats, String[] types);
    Pokeball createPokeball(String name, String sprite, String effect, int price);
    Berry createBerry(String name, String sprite, String effect, int growthTime);
}
