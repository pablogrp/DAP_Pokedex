import com.google.gson.Gson;
import java.net.http.HttpClient;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        PokeApiClient pokeApiClient = new PokeApiClient(client, gson);

        try {
            // ------------------------------------ POKEMON ------------------------------------
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

            PokemonFactory firstGenFactory = new FirstGenFactory();
            // Creamos un pokemon de la primera generación
            Pokemon pokemon1 = firstGenFactory.createPokemon(pokemonData[0], pokemonData[1], stats ,types, pokemonData[9]);
            pokemon1.show();



            // ------------------------------------ POKEBALL ------------------------------------
            // Obtener todos los nombres de las pokeballs de la primera generación
            List<String> firtstGenPokeballs = pokeApiClient.getAllPokeballNames(1);
            System.out.println("\nNombres de pokeballs de la primera generacion:");
            for (String pokeballName : firtstGenPokeballs) {
                System.out.println(pokeballName);
            }
            System.out.println("Elige una pokeball de la lista: ");
            String pokeball = System.console().readLine();

            String[] pokeballData = pokeApiClient.getPokeballData(pokeball);
            // Creamos una pokeball de la primera generación
            Pokeball pokeball1 = firstGenFactory.createPokeball(pokeballData[0], pokeballData[1], pokeballData[2], Integer.parseInt(pokeballData[3]));
            pokeball1.show();



            // ------------------------------------ BERRY ------------------------------------
            // Obtener todos los nombres de las berries de la primera generación
            List<String> firstGenBerries = pokeApiClient.getAllBerryNames(1);
            System.out.println("\nNombres de berries de TODAS LAS BERRYS:");
            for (String berryName : firstGenBerries) {
                System.out.println(berryName);
            }







        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}