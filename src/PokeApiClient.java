import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
public class PokeApiClient {

    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    private final HttpClient client;
    private final Gson gson;

    public PokeApiClient(HttpClient client, Gson gson) {
        this.client = client;
        this.gson = gson;
    }

    // Método para obtener datos de todos los Pokémon
    public String[][] getAllPokemonData(int offset, int limit) throws Exception {
        List<String[]> allPokemonData = new ArrayList<>();

        List<String> pokemonNames = getAllPokemonNames(offset, limit);

        for (String pokemonName : pokemonNames) {
            String[] pokemonData = getPokemonData(pokemonName);
            allPokemonData.add(pokemonData);
        }

        return allPokemonData.toArray(new String[0][0]);
    }

    // Método para obtener nombres de Pokémon
    private List<String> getAllPokemonNames(int offset, int limit) throws Exception {
        String url = BASE_URL + "pokemon?limit=" + limit + "&offset=" + offset;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

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

    // Método para obtener datos individuales de Pokémon
    private String[] getPokemonData(String pokemonName) throws Exception {
        String url = BASE_URL + "pokemon/" + pokemonName;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        String name = jsonResponse.get("name").getAsString();
        String sprite = jsonResponse.getAsJsonObject("sprites").get("front_default").getAsString();

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

        String mechanic = "None";
        return new String[] {
                name, sprite, stats[0], stats[1], stats[2], stats[3], stats[4], stats[5], typesBuilder.toString(), mechanic
        };
    }
    // Método para obtener datos de todas las Berries
    public String[][] getAllBerryData(int limit) throws Exception {
        String url = BASE_URL + "berry?limit=" + limit + "&offset=0";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        JsonArray results = jsonResponse.getAsJsonArray("results");
        List<String[]> allBerryData = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            JsonObject berryObject = results.get(i).getAsJsonObject();
            String name = berryObject.get("name").getAsString();
            if (isFifthGenBerry(name)) {
                String[] berryData = getBerryData(name);
                allBerryData.add(berryData);
            }
            String[] berryData = getBerryData(name);
            allBerryData.add(berryData);
        }

        return allBerryData.toArray(new String[0][0]);
    }

    //Metodo para obtener datos individuales de una Berry
    private String[] getBerryData(String berryName) throws Exception {
        String url = BASE_URL + "berry/" + berryName;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        // Obtener los datos de la berry
        String name = jsonResponse.get("name").getAsString();
        String sprite = "";  // No hay sprites para berries en esta API
        String effect = "";  // Inicializamos el efecto como vacío en caso de que no haya información

        // Extraer el efecto desde "effect_entries", que contiene un array con detalles del efecto
        JsonArray effectEntries = jsonResponse.getAsJsonArray("effect_entries");
        if (effectEntries != null && effectEntries.size() > 0) {
            effect = effectEntries.get(0).getAsJsonObject().get("short_effect").getAsString();
        }

        int growthTime = jsonResponse.get("growth_time").getAsInt();

        // Retornar los valores en el orden adecuado según la interfaz Berry
        return new String[] { name, sprite, effect, Integer.toString(growthTime) };
    }



    // Método para obtener datos de todas las Pokeballs (items hasta la quinta generación)
    public String[][] getAllPokeballData(int limit) throws Exception {
        String url = BASE_URL + "item?limit=" + limit;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        JsonArray results = jsonResponse.getAsJsonArray("results");
        List<String[]> allPokeballData = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            JsonObject itemObject = results.get(i).getAsJsonObject();
            String name = itemObject.get("name").getAsString();
            if (isFifthGenPokeball(name)) { // Solo Pokeballs de la quinta generación
                String[] pokeballData = getPokeballData(name);
                allPokeballData.add(pokeballData);
            }
        }

        return allPokeballData.toArray(new String[0][0]);
    }

    // Método para verificar si un item es una Pokeball de la quinta generación
    private boolean isFifthGenPokeball(String name) {
        // Lista de nombres de Pokeballs hasta la quinta generación
        List<String> fifthGenPokeballs = List.of("poke-ball", "great-ball", "ultra-ball", "master-ball", "safari-ball",
                "fast-ball", "level-ball", "lure-ball", "heavy-ball", "love-ball", "friend-ball", "moon-ball",
                "sport-ball", "park-ball", "dream-ball");
        return fifthGenPokeballs.contains(name);
    }

    //Metodo para verificar si un item es una Berry de la quinta generación
    private boolean isFifthGenBerry(String name) {
        // Lista de nombres de Berries hasta la quinta generación
        List<String> fifthGenBerries = List.of("cheri", "chesto", "pecha", "rawst", "aspear", "leppa", "oran", "persim", "lum", "sitrus", "figy", "wiki", "mago", "aguav", "iapapa", "razz", "bluk", "nanab", "wepear", "pinap", "pomeg", "kelpsy", "qualot", "hondew", "grepa", "tamato", "cornn", "magost", "rabuta", "nomel", "spelon", "pamtre", "watmel", "durin", "belue", "occa", "passho", "wacan", "rindo", "yache", "chople", "kebia", "shuca", "coba", "payapa", "tanga", "charti", "kasib", "haban", "colbur", "babiri", "chilan", "liechi", "ganlon", "salac", "petaya", "apicot", "lansat", "starf", "enigma", "micle", "custap", "jaboca", "rowap");
        return fifthGenBerries.contains(name);
    }

    // Método para obtener datos individuales de una Pokeball
    private String[] getPokeballData(String pokeballName) throws Exception {
        String url = BASE_URL + "item/" + pokeballName;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        String name = jsonResponse.get("name").getAsString();
        String sprite = jsonResponse.getAsJsonObject("sprites").get("default").getAsString();
        String effect = jsonResponse.get("effect_entries").getAsJsonArray().get(0).getAsJsonObject().get("effect").getAsString();
        int price = jsonResponse.get("cost").getAsInt();

        return new String[] { name, sprite, effect, Integer.toString(price) };
    }
}