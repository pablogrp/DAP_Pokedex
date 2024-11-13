public class NinthGenFactory implements PokemonFactory{
    @Override
    public Pokemon createPokemon(String name, String sprite, int[] stats, String[] types, String mechanic) {
        return new NinthGenPokemon(name, sprite, stats, types, mechanic);
    }

    @Override
    public Pokeball createPokeball(String name, String sprite, String effect, int price) {
        return new NinthGenPokeball(name, sprite, effect, price);
    }

    @Override
    public Berry createBerry(String name, String sprite, String effect, int growthTime) {
        return new NinthGenBerry(name, sprite, effect, growthTime);
    }

}
