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
            List<String> firstGenPokemonNames = pokeApiClient.getAllPokemonNames(0, 151);
            System.out.println("Nombres de Pokemons de la primera generacion:");
            for (String pokemonName : firstGenPokemonNames) {
                System.out.println(pokemonName);
            }
            System.out.println("Elige un pokemon de la lista: ");
            String pokemon = System.console().readLine();

            String[] pokemonData = pokeApiClient.getPokemonData(pokemon);
            int[] stats = new int[6];
            for (int i = 2; i < 8; i++) {
                stats[i - 2] = Integer.parseInt(pokemonData[i]);
            }
            String[] types = pokemonData[8].split(",");


            // ESTA SERIA LA MANERA CORRECTA
//            PokemonFactory firstGenFactory = new FirstGenFactory();
//            Pokemon pokemon1 = firstGenFactory.createPokemon();
//            Pokeball pokeball1 = firstGenFactory.createPokeball();
//            Berry berry1 = firstGenFactory.createBerry();


            FirstGenFactory factory = new FirstGenFactory();
            FirstGenPokemon pokemon1 = new FirstGenPokemon(pokemonData[0], pokemonData[1], stats ,types, pokemonData[9]);
            pokemon1.show();

              // Obtener los datos de todos los Pokémon
//            String[][] allPokemonData = pokeApiClient.getAllPokemonData(0, 151);
//            System.out.println("Datos de Pokémon:");
//            for (String[] pokemonData : allPokemonData) {
//                System.out.println("Nombre: " + pokemonData[0]);
//                System.out.println("Sprite: " + pokemonData[1]);
//                System.out.println("Tipos: " + pokemonData[8]);
//                System.out.println("Mecánica: " + pokemonData[9]);
//                System.out.println("Estadísticas (HP, Ataque, Defensa, Ataque Especial, Defensa Especial, Velocidad):");
//                for (int i = 2; i < 8; i++) {
//                    System.out.print(pokemonData[i] + " ");
//                }
//                System.out.println("\n");
//            }

            // Obtener y mostrar datos de todas las berries de quinta generación
//            String[][] allBerryData = pokeApiClient.getAllBerryData(70);  // 1000 es un límite amplio
//            System.out.println("\nDatos de Berries:");
//            for (String[] berryData : allBerryData) {
//                System.out.println("Nombre: " + berryData[0]);
//                System.out.println("Sprite: " + berryData[1]);
//                System.out.println("Efecto: " + berryData[2]);
//                System.out.println("Tiempo de crecimiento: " + berryData[3]);
//                System.out.println();
//            }


            // Obtener y mostrar datos de todas las pokeballs de quinta generación
//            String[][] allPokeballData = pokeApiClient.getAllPokeballData(1000);  // 1000 es un límite amplio
//            System.out.println("\nDatos de Pokeballs:");
//            for (String[] pokeballData : allPokeballData) {
//                System.out.println("Nombre: " + pokeballData[0]);
//                System.out.println("Sprite: " + pokeballData[1]);
//                System.out.println("Efecto: " + pokeballData[2]);
//                System.out.println("Precio: " + pokeballData[3]);
//                System.out.println();
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}