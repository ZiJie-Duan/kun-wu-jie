package components.car;
import bagel.Input;
import bagel.Keys;
import components.effect.Fire;
import components.effect.Smoke;
import dependencies.Status;
import engine.spread.*;
import engine.*;
import bagel.Image;
import engine.trigger.disTrigger.DisTrigger;
import spread.GamePlaySpread;
import triggers.AttackBothTrigger;
import triggers.AttackerTrigger;

import java.util.Random;

public abstract class Car
    extends Sprite<GamePlaySpread, SpreadNull>
    implements AttackBothTrigger {

  protected int minSpeedY;
  protected int maxSpeedY;
  protected double speedY;
  protected double health;
  protected double radius;
  protected double damage;

  protected boolean intoFreezFlag = false;
  protected Locatable freezObj;

  protected boolean invincible;
  protected int crushInvincibleTime;
  protected int freezTime;
  protected boolean freezMoveUp;


  public Car(double x, double y, String imgPath) {
    super(GamePlaySpread.class, SpreadNull.class);
    Loc loc = new Loc(x, y);
    Loc locLT = new Loc(-1000, -1000);
    Loc locRB = new Loc(2000, 2000);
    Boundary boundary = new Boundary(locLT, locRB);
    Image img = new Image(imgPath);

    this.init(loc, boundary, img, true);
  }

  // this is a init method, just used to init all the car's attributes
  protected void initCarArgs(int minSpeedY, int maxSpeedY, double health, double radius, double damage){
    this.minSpeedY = minSpeedY;
    this.maxSpeedY = maxSpeedY;
    this.health = health;
    this.radius = radius;
    this.damage = damage;
  }

  public boolean inCrushInvincibleTime(){
    if (this.crushInvincibleTime > 0){
      return true;
    } else {
      return false;
    }
  }

  protected void resetRandomSpeedY(){
    Random random = new Random();
    this.speedY = minSpeedY + random.nextInt(maxSpeedY - minSpeedY + 1);
  }

  protected void freezBehavior(Locatable obj){
    this.crushInvincibleTime = Status.getSt().getInt("crush_invincible_time");
    this.freezTime = Status.getSt().getInt("freez_time");

    if (this.loc.getY() < obj.getLoc().getY()){
      this.freezMoveUp = true;
    } else {
      this.freezMoveUp = false;
    }
  }

  protected void intoFreez(Locatable obj){
    this.intoFreezFlag = true;
    this.freezObj = obj;
  }

  @Override
  public void ctrlIn(Input input){
    if (input.isDown(Keys.UP) && this.sI.driverInTaxi){
      this.moveY(this.sI.gameGlobalSpeed);
    }
  }

  @Override
  public void update() {

    if (intoFreezFlag){
      intoFreezFlag = false;
      this.freezBehavior(this.freezObj);
    }

    if (this.crushInvincibleTime > 0){
      this.crushInvincibleTime -= 1;
    } else {
      this.moveY(-this.speedY);
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


  protected void damageBehavior() {
    this.getParentElement().deferAddSubElement(new Fire(this.loc.getX(), this.loc.getY()));
    this.suicide();
  }


  @Override
  public void pairTriggerActive(Object obj) {

    if (this.isCollision((DisTrigger) obj) && obj instanceof AttackerTrigger && (obj != this)) {

      if ((this.crushInvincibleTime <= 0) && !this.invincible){

        if (obj instanceof Car){
          if (!((Car)obj).inCrushInvincibleTime()){
            this.intoFreez((Locatable) obj);
            this.getHurts(((AttackerTrigger) obj).damageValue());
          }

        } else {
          this.getHurts(((AttackerTrigger) obj).damageValue());
        }
      }
    }
    }

}
