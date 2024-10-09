import bagel.Input;
import bagel.Keys;
import engine.spread.*;
import engine.*;
import bagel.Image;

/* Please check my README file .*/
public class Taxi extends Car {

  private Status st = Status.getSt();
  private int health = 100;

  public Taxi(int x, int y) {
    super(x, y, Status.getSt().gameProps.getProperty("gameObjects.taxi.image"));
  }

  public void pairTriggerActive(Object obj) {
    if (obj instanceof Fireball) {
      if (this.loc.distanceWith(((Fireball) obj).getLocClone()) < 20) {
        this.health -= 20;
      }
    }
  }

  @Override
  public void ctrlIn(Input input) {
    this.sI.taxiMoveing = false;

    if (!this.sI.driverInTaxi) {
      if (input.isDown(Keys.UP)) {
        this.moveY(this.sI.taxiSpeed);
      }

    } else {
      if (input.isDown(Keys.UP)) {
        this.sI.taxiMoveing = true;
      }

      if (input.isDown(Keys.LEFT)) {
        this.sI.taxiMoveing = true;
        this.moveX(-1);
      }

      if (input.isDown(Keys.RIGHT)) {
        this.sI.taxiMoveing = true;
        this.moveX(+1);
      }
    }
  }

  @Override
  public void update() {
    this.sI.taxiHealth = this.health;
    if (this.health <= 0) {
      this.suicide();
      this.getParentElement().addSubElement(
          new TaxiBroken(this.loc.getX(), this.loc.getY()));
    }
  }

}
