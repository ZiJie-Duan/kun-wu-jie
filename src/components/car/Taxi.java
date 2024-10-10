package components.car;

import bagel.Input;
import bagel.Keys;
import components.effect.Fire;
import components.effect.Smoke;
import components.effect.TaxiBroken;
import dependencies.Status;
import engine.trigger.disTrigger.DisTrigger;
import triggers.AttackBothTrigger;
import triggers.AttackerTrigger;

import java.util.Random;

/* Please check my README file .*/
public class Taxi extends Car {

  private Status st = Status.getSt();

  private double radius;
  private double health;
  private double damage;
  private double speedX;
  private double speedY;
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
  public double damageValue() {
    return this.damage;
  }

  @Override
  public void getHurts(double damage) {
    this.health -= damage;
    this.getParentElement().deferAddSubElement(new Smoke(this.loc.getX(), this.loc.getY()));
  }

  @Override
  public double radius() {
    return this.radius;
  }

  @Override
  public void pairTriggerActive(Object obj) {

    if (obj instanceof AttackerTrigger && (obj != this)){
      if (this.isCollision((DisTrigger) obj)) {
        this.getHurts(((AttackBothTrigger) obj).damageValue());
      }
    }

  }

  @Override
  public void ctrlIn(Input input) {
    this.sI.taxiMoveing = false;

    if (!this.sI.driverInTaxi) {
      if (input.isDown(Keys.UP)) {
        this.moveY(this.sI.gameGlobalSpeed);
      }

    } else {
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

  @Override
  public void update() {
    this.sI.taxiHealth = this.health;
    if (this.health <= 0) {
      this.damageBehavior();
    }
  }

}
