package pokedex.app;
import pokedex.api.PokeApiClient;
import com.google.gson.Gson;
import java.net.http.HttpClient;

public class Main {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        PokeApiClient pokeApiClient = new PokeApiClient(client, gson);

        PokedexApp px = new PokedexApp(pokeApiClient);
        px.setVisible(true);
    }
}
