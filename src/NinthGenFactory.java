public class NinthGenFactory implements PokemonFactory{
    @Override
    public Pokemon[] createPokemons() {
        return new Pokemon[0];
    }

    @Override
    public Pokeball[] createPokeballs() {
        return new Pokeball[0];
    }

    @Override
    public Berry[] createBerrys() {
        return new Berry[0];
    }
}
