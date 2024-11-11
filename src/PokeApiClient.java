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

//    public String[][] getAllPokemonData(int offset, int limit) throws Exception {
//        List<String[]> allPokemonData = new ArrayList<>();
//
//        List<String> pokemonNames = getAllPokemonNames(offset,limit);
//
//        for (String pokemonName : pokemonNames) {
//            String[] pokemonData = getPokemonData(pokemonName);
//            allPokemonData.add(pokemonData);
//        }
//        return allPokemonData.toArray(new String[0][0]);
//    }

    // ------------------------------------ POKEMON ------------------------------------
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

    public String[] getPokemonData(String pokemonName) throws Exception {
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

    // ------------------------------------ POKEBALL ------------------------------------
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
                for (int i = 0; i < results.size(); i++) {
                    String name = allpokeballnames.get(i);
                    namepokeballsbyGeneration.add(name);
                }
                break;
        }
        return namepokeballsbyGeneration;
    }

    public String[] getPokeballData(String pokeballName) throws Exception {
        String url = BASE_URL + "item/" + pokeballName;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        String name = jsonResponse.get("name").getAsString();
        String sprite = jsonResponse.getAsJsonObject("sprites").get("default").getAsString();
        String effect = jsonResponse.get("effect_entries").getAsJsonArray().get(0).getAsJsonObject().get("short_effect").getAsString();
        int price = jsonResponse.get("cost").getAsInt();

        return new String[] { name, sprite, effect, Integer.toString(price) };
    }

    // ------------------------------------ BERRY ------------------------------------
    public List<String> getAllBerryNames(int gen) throws Exception {
        String url = BASE_URL + "berry?limit=64";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

        JsonArray results = jsonResponse.getAsJsonArray("results");
        List<String> allBerryNames = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            JsonObject berryObject = results.get(i).getAsJsonObject();
            String name = berryObject.get("name").getAsString();
            allBerryNames.add(name);
        }

        List<String> nameBerriesbyGeneration = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            String name = allBerryNames.get(i);
            nameBerriesbyGeneration.add(name);
        }

//        switch (gen) {
//            case 1:
//                for (int i = 0; i < 5; i++) {
//                    String name = allBerryNames.get(i);
//                    nameBerriesbyGeneration.add(name);
//                }
//                break;
//            case 5:
//                for (int i = 0; i < 29; i++) {
//                    String name = allBerryNames.get(i);
//                    nameBerriesbyGeneration.add(name);
//                }
//                break;
//            case 9:
//                for (int i = 0; i < results.size(); i++) {
//                    String name = allBerryNames.get(i);
//                    nameBerriesbyGeneration.add(name);
//                }
//                break;
//        }
        return nameBerriesbyGeneration;
    }


}
