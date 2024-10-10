
public class Coin extends Item {

  public int radius() {
    return 20;
  }

  public Coin(int x, int y) {
    super(x, y, Status.getSt().gameProps.getProperty("gameObjects.coin.image"));
  }

  @Override
  public void pairTriggerActive(Object obj) {
  }
}
