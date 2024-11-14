package pokedex.datastructure.products;

public class NinthGenPokeball implements Pokeball{
    private String name;
    private String sprite;
    private String effect;
    private int price;

    public NinthGenPokeball(String name, String sprite, String effect, int price) {
        this.name = name;
        this.sprite = sprite;
        this.effect = effect;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSprite() {
        return sprite;
    }

    @Override
    public String getEffect() {
        return effect;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void show() {
        System.out.println("Nombre: " + name);
        System.out.println("Sprite: " + sprite);
        System.out.println("Efecto: " + effect);
        System.out.println("Precio: " + price);
    }
}
