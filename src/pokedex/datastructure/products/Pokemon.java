package pokedex.datastructure.products;

public interface Pokemon {
    String getName();
    String getSprite();
    int[] getStats();// hp, attack, defense, special attack, special defense, speed
    String[] getTypes();
    String getMechanic();
    void show();
}
