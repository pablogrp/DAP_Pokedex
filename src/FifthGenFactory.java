public class FifthGenFactory implements PokemonFactory {
    @Override
    public Pokemon createPokemon(String name, String sprite, int[] stats, String[] types, String mechanic) {
        return new FifthGenPokemon(name, sprite, stats, types, mechanic);
    }


    @Override
    public Pokeball createPokeball(String name, String sprite, String effect, int price) {
        return new FifthGenPokeball(name, sprite, effect, price);
    }

    @Override
    public Berry createBerry(String name, String sprite, String effect, int growthTime) {
        return new FifthGenBerry(name, sprite, effect, growthTime);
    }
}
