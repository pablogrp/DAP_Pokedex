public class FifthGenPokeball {
    private String name;
    private String sprite;
    private String effect;
    private int price;

    public FifthGenPokeball(String name, String sprite, String effect, int price) {
        this.name = name;
        this.sprite = sprite;
        this.effect = effect;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getSprite() {
        return sprite;
    }

    public String getEffect() {
        return effect;
    }

    public int getPrice() {
        return price;
    }
}
