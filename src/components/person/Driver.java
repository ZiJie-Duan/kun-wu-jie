package components.person;

import bagel.Input;
import bagel.Keys;
import components.car.Taxi;
import components.effect.Blood;
import components.item.Coin;
import components.item.InvinciblePower;
import dependencies.Status;
import engine.trigger.disTrigger.DisTrigger;
import triggers.AttackBothTrigger;
import triggers.AttackerTrigger;

public class Driver extends Person {

  private double walkSpeedX;
  private double walkSpeedY;
  private double taxiGetInRadius;

  // pop out flag
  private boolean alreadyPopOut = false;

  public Driver(int x, int y) {
    super(x, y, Status.getSt().gameProps.getProperty("gameObjects.driver.image"));
    Status st = Status.getSt();
    this.walkSpeedX = Double.parseDouble(st.gameProps.getProperty("gameObjects.driver.walkSpeedX"));
    this.walkSpeedY = Double.parseDouble(st.gameProps.getProperty("gameObjects.driver.walkSpeedY"));
    this.taxiGetInRadius = Double.parseDouble(st.gameProps.getProperty("gameObjects.driver.taxiGetInRadius"));

    double radius = Double.parseDouble(st.gameProps.getProperty("gameObjects.driver.radius"));
    double health = Double.parseDouble(st.gameProps.getProperty("gameObjects.driver.health")) * 100;
    this.initPersonArgs(health, radius);
  }

  private void driverPopOut() {
    this.loc.setX(this.loc.getX() - 50); // move driver out of taxi
  }

  private void driverGetOnTaxi() {
    this.alreadyPopOut = false;
    this.sI.driverInTaxi = true;
  }

  @Override
  public void ctrlIn(Input input) {
    // if driver in components.car.Taxi, then follow the movement of components.car.Taxi
    // if not, Y axis behavior is difference
    if (this.freezTime <= 0){

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
  }

  @Override
  public void update() {
    super.update();

    this.sI.driverLoc = this.loc;

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
  }

  @Override
  public void pairTriggerActive(Object obj) {
    super.pairTriggerActive(obj);

    if (obj instanceof Taxi) {
      // get on taxi
      if (this.loc.distanceWith(((Taxi) obj).getLoc()) < this.taxiGetInRadius) {
        this.driverGetOnTaxi();
      }
    }

    if (this.isCollision((DisTrigger) obj)){
      if (obj instanceof Coin){
        if (this.sI.levelUpFrame <= 0){
          this.sI.levelUpFrame = 500;
          this.sI.alreadyLevelUP = false;
        }
      } else if (obj instanceof InvinciblePower){
        this.sI.invinciblePowerFrame = 1000;
      }
    }
  }
}
