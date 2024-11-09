import java.util.ArrayList;
import java.util.List;

public class FifthGenFactory implements PokemonFactory {
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
