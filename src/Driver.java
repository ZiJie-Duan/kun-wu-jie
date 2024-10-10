import bagel.Image;
import bagel.Input;
import bagel.Keys;
import engine.Loc;
import engine.Sprite;
import engine.spread.SpreadNull;
import engine.trigger.pairTrigger.PairTrigger;

public class Driver extends Sprite<GamePlaySpread, SpreadNull>
    implements PairTrigger, DisTrigger, AttackTrigger {

  private Status st = Status.getSt();
  private int walkSpeedX;
  private int walkSpeedY;
  private int radius;
  private int taxiGetInRadius;
  private int health;

  // pop out flag
  private boolean alreadyPopOut = false;

  public Driver(int x, int y) {
    super(GamePlaySpread.class, SpreadNull.class);
    Loc loc = new Loc(x, y);
    Image img = new Image(st.gameProps.getProperty("gameObjects.driver.image"));
    init(loc, null, img, false);

    this.walkSpeedX = Integer.parseInt(st.gameProps.getProperty("gameObjects.driver.walkSpeedX"));
    this.walkSpeedY = Integer.parseInt(st.gameProps.getProperty("gameObjects.driver.walkSpeedY"));
    this.radius = Integer.parseInt(st.gameProps.getProperty("gameObjects.driver.radius"));
    this.taxiGetInRadius = Integer.parseInt(st.gameProps.getProperty("gameObjects.driver.taxiGetInRadius"));
    this.health = (int) Double.parseDouble(st.gameProps.getProperty("gameObjects.driver.health")) * 100;
  }

  private void driverPopOut() {
    this.loc.setX(this.loc.getX() - 50); // move driver out of taxi
  }

  private void driverGetOnTaxi() {
    this.alreadyPopOut = false;
    this.sI.driverInTaxi = true;
  }

  private void die() {
    this.getParentElement().addSubElement(new Blood(this.loc.getX(), this.loc.getY()));
    this.suicide();
  }

  @Override
  public void getHurts(int damage) {
    this.health -= damage;
  }

  @Override
  public int damageValue() {
    return 30; // just for test
  }

  @Override
  public void ctrlIn(Input input) {
    // if driver in Taxi, then follow the movement of Taxi
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

    if (obj instanceof DisTrigger && obj instanceof Taxi) {

      // get on taxi
      if (this.loc.distanceWith(((Taxi) obj).getLocClone()) < this.taxiGetInRadius) {
        this.driverGetOnTaxi();
      }

    }
  }

  @Override
  public int radius() {
    return this.radius;
  }
}
