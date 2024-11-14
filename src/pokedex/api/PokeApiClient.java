package pokedex.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * A client for interacting with the PokeAPI.
 */
public class PokeApiClient {

    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    private final HttpClient client;
    private final Gson gson;

    /**
     * Constructs a new pokedex.api.PokeApiClient.
     *
     * @param client the HttpClient to use for making requests
     * @param gson the Gson instance to use for parsing JSON responses
     */
    public PokeApiClient(HttpClient client, Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    /**
     * Retrieves a list of all pokedex.datastructure.products.Pokemon names for a given generation.
     *
     * @param gen the generation of pokedex.datastructure.products.Pokemon to retrieve
     * @return a list of pokedex.datastructure.products.Pokemon names
     * @throws Exception if an error occurs during the request
     */
    public List<String> getAllPokemonNames(int gen) throws Exception {
        String url;
        HttpRequest request = switch (gen) {
            case 1 -> {
                url = BASE_URL + "pokemon?limit=" + 151 + "&offset=" + 0;
                yield HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();
            }
            case 5 -> {
                url = BASE_URL + "pokemon?limit=" + 156 + "&offset=" + 493;
                yield HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();
            }
            case 9 -> {
                url = BASE_URL + "pokemon?limit=" + 300 + "&offset=" + 905;
                yield HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();
            }
            default -> null;
        };

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        JsonArray results = jsonResponse.getAsJsonArray("results");
        List<String> pokemonNames = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            JsonObject pokemonObject = results.get(i).getAsJsonObject();
            String name = pokemonObject.get("name").getAsString();
            pokemonNames.add(name);
        }
        return pokemonNames;
    }

    /**
     * Retrieves data for a specific pokedex.datastructure.products.Pokemon.
     *
     * @param pokemonName the name of the pokedex.datastructure.products.Pokemon to retrieve data for
     * @return an array of strings containing the pokedex.datastructure.products.Pokemon's data
     * @throws Exception if an error occurs during the request
     */
    public String[] getPokemonData(String pokemonName) throws Exception {
        String url = BASE_URL + "pokemon/" + pokemonName;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        String name = jsonResponse.get("name").getAsString();
        String sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"; // URL por defecto para el sprite
        JsonObject sprites = jsonResponse.getAsJsonObject("sprites");
        if (sprites != null && sprites.has("front_default") && !sprites.get("front_default").isJsonNull()) {
            sprite = sprites.get("front_default").getAsString();
        }

        JsonArray typesArray = jsonResponse.getAsJsonArray("types");
        StringBuilder typesBuilder = new StringBuilder();
        for (int i = 0; i < typesArray.size(); i++) {
            if (i > 0) {
                typesBuilder.append(",");
            }
            typesBuilder.append(typesArray.get(i).getAsJsonObject().getAsJsonObject("type").get("name").getAsString());
        }
        JsonArray statsArray = jsonResponse.getAsJsonArray("stats");
        String[] stats = new String[6];
        for (int i = 0; i < 6; i++) {
            stats[i] = statsArray.get(i).getAsJsonObject().get("base_stat").getAsString();
        }
        return new String[] {name, sprite, stats[0], stats[1], stats[2], stats[3], stats[4], stats[5], typesBuilder.toString()};
    }

    /**
     * Retrieves a list of all pokedex.datastructure.products.Pokeball names for a given generation.
     *
     * @param gen the generation of Pokeballs to retrieve
     * @return a list of pokedex.datastructure.products.Pokeball names
     * @throws Exception if an error occurs during the request
     */
    public List<String> getAllPokeballNames(int gen) throws Exception {
        String url = BASE_URL+  "item?limit=2229";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        JsonArray results = jsonResponse.getAsJsonArray("results");
        List<String> allpokeballnames = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            JsonObject itemObject = results.get(i).getAsJsonObject();
            String name = itemObject.get("name").getAsString();
            if (name.contains("-ball") &&
                    name.chars().filter(ch -> ch == '-').count() == 1 &&
                    !name.contains("-balloon")) {
                allpokeballnames.add(name);
            }
        }
        List<String> namepokeballsbyGeneration = new ArrayList<>();

        switch (gen) {
            case 1:
                for (int i = 0; i < 5; i++) {
                    String name = allpokeballnames.get(i);
                    namepokeballsbyGeneration.add(name);
                }
                break;
            case 5:
                for (int i = 0; i < 29; i++) {
                    String name = allpokeballnames.get(i);
                    namepokeballsbyGeneration.add(name);
                }
                break;
            case 9:
                for (int i = 0; i < 47; i++) {
                    String name = allpokeballnames.get(i);
                    namepokeballsbyGeneration.add(name);
                }
                break;
        }
        return namepokeballsbyGeneration;
    }

    /**
     * Retrieves data for a specific pokedex.datastructure.products.Pokeball.
     *
     * @param pokeballName the name of the pokedex.datastructure.products.Pokeball to retrieve data for
     * @return an array of strings containing the pokedex.datastructure.products.Pokeball's data
     * @throws Exception if an error occurs during the request
     */
    public String[] getPokeballData(String pokeballName) throws Exception {
        String url = BASE_URL + "item/" + pokeballName;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        String name = jsonResponse.get("name").getAsString();
        String sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png"; // URL por defecto para el sprite
        JsonObject sprites = jsonResponse.getAsJsonObject("sprites");
        if (sprites != null && sprites.has("default") && !sprites.get("default").isJsonNull()) {
            sprite = sprites.get("default").getAsString();
        }
        // Comprobación de 'effect_entries' y 'short_effect'
        String effect = "No effect description available"; // Valor por defecto
        JsonArray effectEntries = jsonResponse.getAsJsonArray("effect_entries");
        if (effectEntries != null && !effectEntries.isEmpty()) {
            JsonObject firstEffectEntry = effectEntries.get(0).getAsJsonObject();
            if (firstEffectEntry.has("short_effect") && !firstEffectEntry.get("short_effect").isJsonNull()) {
                effect = firstEffectEntry.get("short_effect").getAsString();
            }
        }
        int price = jsonResponse.get("cost").getAsInt();

        return new String[] { name, sprite, effect, Integer.toString(price) };
    }

    /**
     * Retrieves a list of all pokedex.datastructure.products.Berry names for a given generation.
     *
     * @param gen the generation of Berries to retrieve
     * @return a list of pokedex.datastructure.products.Berry names
     * @throws Exception if an error occurs during the request
     */
    public List<String> getAllBerryNames(int gen) throws Exception {
        String url = BASE_URL + "item?limit=725";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        JsonArray results = jsonResponse.getAsJsonArray("results");
        List<String> allBerryNames = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            JsonObject itemObject = results.get(i).getAsJsonObject();
            String name = itemObject.get("name").getAsString();
            if (name.contains("-berry")) {
                allBerryNames.add(name);
            }
        }
        List<String> nameBerriesbyGeneration = new ArrayList<>();

        switch (gen) {
            case 1:
                System.out.println("No hay berries en la primera generacion");
                break;
            case 5:
                for (int i = 0; i < 64; i++) {
                    String name = allBerryNames.get(i);
                    nameBerriesbyGeneration.add(name);
                }
                break;
            case 9:
                for (int i = 0; i < 67; i++) {
                    String name = allBerryNames.get(i);
                    nameBerriesbyGeneration.add(name);
                }
                break;
        }
        return nameBerriesbyGeneration;
    }

    /**
     * Retrieves data for a specific pokedex.datastructure.products.Berry.
     *
     * @param berryName the name of the pokedex.datastructure.products.Berry to retrieve data for
     * @return an array of strings containing the pokedex.datastructure.products.Berry's data
     * @throws Exception if an error occurs during the request
     */
    public String[] getBerryData(String berryName) throws Exception {
        String url = BASE_URL + "item/" + berryName;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        String name = jsonResponse.get("name").getAsString();
        String sprite = BASE_URL + "pokemon/ditto"; // URL por defecto para el sprite
        JsonObject sprites = jsonResponse.getAsJsonObject("sprites");
        if (sprites != null && sprites.has("default") && !sprites.get("default").isJsonNull()) {
            sprite = sprites.get("default").getAsString();
        }
        String effect = jsonResponse.get("effect_entries").getAsJsonArray().get(0).getAsJsonObject().get("short_effect").getAsString();
        int size = jsonResponse.get("cost").getAsInt();

        return new String[] { name, sprite, effect, Integer.toString(size) };
    }
}
