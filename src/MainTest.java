import com.google.gson.Gson;
import java.net.http.HttpClient;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        PokeApiClient pokeApiClient = new PokeApiClient(client, gson);

        try {
            // Obtener todos los nombres de los Pokémon de la primera generación
            List<String> firstGenPokemonNames = pokeApiClient.getAllPokemonNames(1);
            System.out.println("Nombres de Pokemons de la primera generacion:");
            for (String pokemonName : firstGenPokemonNames) {
                System.out.println(pokemonName);
            }
            System.out.println("Elige un pokemon de la lista: ");
            String pokemon = System.console().readLine();

            // Seteamos los stats y tipos del pokemon
            String[] pokemonData = pokeApiClient.getPokemonData(pokemon);
            int[] stats = new int[6];
            for (int i = 2; i < 8; i++) {
                stats[i - 2] = Integer.parseInt(pokemonData[i]);
            }
            String[] types = pokemonData[8].split(",");




            // CREACION DE LA FABRICA DE POKEMON -> FirstGenFactory
            // ESTA SERIA LA MANERA CORRECTA
            PokemonFactory firstGenFactory = new FirstGenFactory();
            Pokemon pokemon1 = firstGenFactory.createPokemon(pokemonData[0], pokemonData[1], stats ,types, pokemonData[9]);
//            Pokeball pokeball1 = firstGenFactory.createPokeball();
//            Berry berry1 = firstGenFactory.createBerry();

            pokemon1.show();



//            Pokeball pokeball1 = firstGenFactory.createPokeball();
            List<String> firtstGenPokeballs = pokeApiClient.getAllPokeballNames(1);
            System.out.println("\nNombres de pokeballs de la primera generacion:");
            for (String pokeballName : firtstGenPokeballs) {
                System.out.println(pokeballName);
            }
            System.out.println("Elige una pokeball de la lista: ");
            String pokeball = System.console().readLine();

            String[] pokeballData = pokeApiClient.getPokeballData(pokeball);
//            Pokeball pokeball1 = new FirstGenPokeball(pokeballData[0], pokeballData[1], pokeballData[2], Integer.parseInt(pokeballData[3]));
//            pokeball1.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}