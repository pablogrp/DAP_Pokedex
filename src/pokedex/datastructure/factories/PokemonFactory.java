package pokedex.datastructure.factories;

import pokedex.datastructure.products.Berry;
import pokedex.datastructure.products.Pokeball;
import pokedex.datastructure.products.Pokemon;

public interface PokemonFactory {
    Pokemon createPokemon(String name, String sprite, int[] stats, String[] types);
    Pokeball createPokeball(String name, String sprite, String effect, int price);
    Berry createBerry(String name, String sprite, String effect, int growthTime);
}
