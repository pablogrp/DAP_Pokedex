package pokedex.datastructure.products;

public class FirstGenBerry implements Berry {
    private String name;
    private String sprite;
    private String effect;
    private int growthTime;

    public FirstGenBerry(String name, String sprite, String effect, int growthTime) {
        this.name = name;
        this.sprite = sprite;
        this.effect = effect;
        this.growthTime = growthTime;
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
    public int getGrowthTime() {
        return growthTime;
    }

    @Override
    public void show() {
        System.out.println("pokedex.datastructure.products.Berry: " + name);
        System.out.println("Sprite: " + sprite);
        System.out.println("Effect: " + effect);
        System.out.println("Growth Time: " + growthTime);
    }
}