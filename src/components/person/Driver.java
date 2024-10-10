package components.person;

import bagel.Input;
import bagel.Keys;
import components.car.Taxi;
import components.effect.Blood;
import dependencies.Status;
import engine.trigger.disTrigger.DisTrigger;
import triggers.AttackBothTrigger;
import triggers.AttackerTrigger;

public class Driver extends Person {

  private double walkSpeedX;
  private double walkSpeedY;
  private double radius;
  private double taxiGetInRadius;
  private double health;

  // pop out flag
  private boolean alreadyPopOut = false;

  public Driver(int x, int y) {
    super(x, y, Status.getSt().gameProps.getProperty("gameObjects.driver.image"));
    Status st = Status.getSt();
    this.walkSpeedX = Double.parseDouble(st.gameProps.getProperty("gameObjects.driver.walkSpeedX"));
    this.walkSpeedY = Double.parseDouble(st.gameProps.getProperty("gameObjects.driver.walkSpeedY"));
    this.radius = Double.parseDouble(st.gameProps.getProperty("gameObjects.driver.radius"));
    this.taxiGetInRadius = Double.parseDouble(st.gameProps.getProperty("gameObjects.driver.taxiGetInRadius"));
    this.health = Double.parseDouble(st.gameProps.getProperty("gameObjects.driver.health")) * 100;
  }

  private void driverPopOut() {
    this.loc.setX(this.loc.getX() - 50); // move driver out of taxi
  }

  private void driverGetOnTaxi() {
    this.alreadyPopOut = false;
    this.sI.driverInTaxi = true;
  }

  private void die() {
    this.getParentElement().deferAddSubElement(new Blood(this.loc.getX(), this.loc.getY()));
    this.suicide();
  }

  @Override
  public void getHurts(double damage) {
    this.health -= damage;
  }

  @Override
  public void ctrlIn(Input input) {
    // if driver in components.car.Taxi, then follow the movement of components.car.Taxi
    // if not, Y axis behavior is difference
    if (!this.sI.driverInTaxi) {
      if (input.isDown(Keys.UP)) {
        this.moveY(-this.walkSpeedY);
      }
      if (input.isDown(Keys.DOWN)) {
        this.moveY(+this.walkSpeedY);
      }
    }

    if (input.isDown(Keys.LEFT)) {
      this.moveX(-this.walkSpeedX);
    }

    if (input.isDown(Keys.RIGHT)) {
      this.moveX(this.walkSpeedX);
    }
  }

  @Override
  public void update() {
    // if driver is not in taxi and not already pop out
    if (!this.sI.driverInTaxi && !alreadyPopOut) {
      this.driverPopOut();
      this.alreadyPopOut = true;
    }

    // if driver is in taxi, hide the driver
    if (this.sI.driverInTaxi) {
      this.visible = false;
    } else {
      this.visible = true;
    }

    // sync driver health to spread
    this.sI.driverHealth = this.health;

    // if driver health is 0, add blood
    if (this.health <= 0) {
      die();
    }

  }

  @Override
  public void pairTriggerActive(Object obj) {

    if (obj instanceof Taxi) {
      // get on taxi
      if (this.loc.distanceWith(((Taxi) obj).getLoc()) < this.taxiGetInRadius) {
        this.driverGetOnTaxi();
      }
    }

    if (obj instanceof AttackerTrigger && (obj != this)){
      if (this.isCollision((DisTrigger) obj)) {
        this.getHurts(((AttackBothTrigger) obj).damageValue());
      }
    }

  }

  @Override
  public double radius() {
    return this.radius;
  }
}
