public class FifthGenBerry {
    private String name;
    private String sprite;
    private String effect;
    private int growthTime;

    public FifthGenBerry(String name, String sprite, String effect, int growthTime) {
        this.name = name;
        this.sprite = sprite;
        this.effect = effect;
        this.growthTime = growthTime;
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

    public int getGrowthTime() {
        return growthTime;
    }
}
