package components.car;

import bagel.Input;
import bagel.Keys;
import components.effect.Fire;
import components.effect.TaxiBroken;
import components.item.Coin;
import components.item.InvinciblePower;
import dependencies.Status;
import engine.trigger.disTrigger.DisTrigger;

import java.util.Random;

/* Please check my README file .*/
public class Taxi extends Car {

  private Status st = Status.getSt();

  private double speedX;
  private int nextSpawnMaxY;
  private int nextSpawnMinY;



  public Taxi(double x, double y) {
    super(x, y, Status.getSt().gameProps.getProperty("gameObjects.taxi.image"));
    this.radius = Double.parseDouble(st.gameProps.getProperty("gameObjects.taxi.radius"));
    this.health = Double.parseDouble(st.gameProps.getProperty("gameObjects.taxi.health")) * 100;
    this.damage = Double.parseDouble(st.gameProps.getProperty("gameObjects.taxi.damage")) * 100;
    this.speedX = Double.parseDouble(st.gameProps.getProperty("gameObjects.taxi.speedX"));
    this.speedY = Double.parseDouble(st.gameProps.getProperty("gameObjects.taxi.speedY"));
    this.nextSpawnMaxY = Integer.parseInt(st.gameProps.getProperty("gameObjects.taxi.nextSpawnMaxY"));
    this.nextSpawnMinY = Integer.parseInt(st.gameProps.getProperty("gameObjects.taxi.nextSpawnMinY"));
  }


  // behavior when taxi is damaged
  @Override
  protected void damageBehavior() {
    // add broken taxi and fire effect
    this.getParentElement().deferAddSubElement(
        new TaxiBroken(this.loc.getX(), this.loc.getY()));
    this.getParentElement().deferAddSubElement(
        new Fire(this.loc.getX(), this.loc.getY()));

    // set flag to pop out driver
    this.sI.driverInTaxi = false;

    // gnerate new taxi via random location
    Random random = new Random();
    int new_y = this.nextSpawnMinY + random.nextInt(this.nextSpawnMaxY - this.nextSpawnMinY + 1);
    int new_lane = random.nextInt(3);
    int new_x;

    if (new_lane == 0) {
      new_x = Integer.parseInt(st.gameProps.getProperty("roadLaneCenter1"));
    } else if (new_lane == 1) {
      new_x = Integer.parseInt(st.gameProps.getProperty("roadLaneCenter2"));
    } else {
      new_x = Integer.parseInt(st.gameProps.getProperty("roadLaneCenter3"));
    }
    this.getParentElement().deferAddSubElement(new Taxi(new_x, new_y));

    // remove the taxi now
    this.suicide();
  }


  @Override
  public void ctrlIn(Input input) {
    this.sI.taxiMoveing = false;

    if (!this.sI.driverInTaxi) {
      if (input.isDown(Keys.UP)) {
        this.moveY(this.sI.gameGlobalSpeed);
      }

    } else if (this.freezTime <= 0){
      if (input.isDown(Keys.UP)) {
        this.sI.taxiMoveing = true;
      }

      if (input.isDown(Keys.LEFT)) {
        this.sI.taxiMoveing = true;
        this.moveX(-this.speedX);
      }

      if (input.isDown(Keys.RIGHT)) {
        this.sI.taxiMoveing = true;
        this.moveX(+this.speedX);
      }
    }
  }

  // in this game we not allow System to reset Taxi Speed
  @Override
  protected void resetRandomSpeedY(){
  }

  @Override
  public void pairTriggerActive(Object obj) {
    super.pairTriggerActive(obj);

    if (this.isCollision((DisTrigger) obj)){
      if (obj instanceof Coin) {
        if (this.sI.levelUpFrame <= 0) {
          this.sI.levelUpFrame = 500;
          this.sI.alreadyLevelUP = false;
        }
      } else if (obj instanceof InvinciblePower){
        this.sI.invinciblePowerFrame = 1000;
      }
    }
  }

  @Override
  public void update() {

    if (this.sI.invinciblePowerFrame > 0){
      this.invincible = true;
    } else {
      this.invincible = false;
    }

    if (this.crushInvincibleTime > 0){
      this.crushInvincibleTime -= 1;
    }

    if (this.freezTime > 0) {
      this.freezTime -= 1;
      if (this.freezMoveUp){
        this.moveY(-1);
      } else {
        this.moveY(1);
      }
    }

    if (this.health <= 0) {
      this.damageBehavior();
    }

    this.sI.taxiHealth = this.health;
  }
}
