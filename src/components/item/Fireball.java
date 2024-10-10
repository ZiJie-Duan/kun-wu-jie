package components.item;

import dependencies.Status;
import engine.*;
import engine.spread.SpreadNull;
import bagel.*;
import engine.trigger.pairTrigger.PairTrigger;

public class Fireball extends Item{

  private double radius;
  private double damage;
  private double shootSpeedY;

  public Fireball(double x, double y) {
    super(x, y, Status.getSt().gameProps.getProperty("gameObjects.fireball.image"));

    this.radius = Double.parseDouble(Status.getSt().gameProps.getProperty("gameObjects.fireball.radius"));
    this.damage = Double.parseDouble(Status.getSt().gameProps.getProperty("gameObjects.fireball.damage")) * 100;
    this.shootSpeedY = Double.parseDouble(Status.getSt().gameProps.getProperty("gameObjects.fireball.shootSpeedY"));
  }

  @Override
  public double radius() {
    return this.radius;
  }

  @Override
  public void pairTriggerActive(Object obj) {

  }
}
