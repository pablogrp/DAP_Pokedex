public interface PokemonFactory {
//    Pokemon[] createPokemons();
//    Pokeball[] createPokeballs();
//    Berry[] createBerrys();

    Pokemon createPokemon(String name, String sprite, int[] stats, String[] types, String mechanic);
}
