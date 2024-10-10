package components.item;

import dependencies.Status;

public class Coin extends Item {

  private double radius;
  private int ttl;

  public Coin(double x, double y) {
    super(x, y, Status.getSt().gameProps.getProperty("gameObjects.coin.image"));

    this.radius = Double.parseDouble(Status.getSt().gameProps.getProperty("gameObjects.coin.radius"));
    this.ttl = Integer.parseInt(Status.getSt().gameProps.getProperty("gameObjects.coin.maxFrames"));
  }

  @Override
  public void pairTriggerActive(Object obj) {
  }

  @Override
  public double radius() {
    return this.radius;
  }

  @Override
  public void update() {
    if (this.ttl <= 0){
      this.suicide();
    }
    this.ttl -= 1;
  }
}
