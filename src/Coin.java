

public class Coin extends Item {


    public Coin(int x, int y) {
        super(x, y, Status.getSt().gameProps.getProperty("gameObjects.coin.image"));
    }

    @Override
    public void pairTriggerActive(Object obj) {
    }
}
