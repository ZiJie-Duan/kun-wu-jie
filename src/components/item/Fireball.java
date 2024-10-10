package components.item;

import dependencies.Status;
import engine.*;
import engine.spread.SpreadNull;
import bagel.*;
import engine.trigger.disTrigger.DisTrigger;
import engine.trigger.pairTrigger.PairTrigger;
import triggers.AttackTargetTrigger;
import triggers.AttackerTrigger;

public class Fireball extends Item implements AttackerTrigger {

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
    if (obj instanceof AttackTargetTrigger){
      if (this.isCollision((DisTrigger)obj)){
        this.suicide();
      }
    }
  }

  @Override
  public void ctrlIn(Input input){

  }

  @Override
  public void update(){
    this.moveY(-this.shootSpeedY);
  }

  @Override
  public double damageValue() {
    return this.damage;
  }
}
