import bagel.Input;
import bagel.Keys;
import java.util.Random;

/* Please check my README file .*/
public class Taxi extends Car {

  private Status st = Status.getSt();

  private int radius;
  private int health;
  private int damage;
  private int speedX;
  private int speedY;
  private int nextSpawnMaxY;
  private int nextSpawnMinY;


  public Taxi(int x, int y) {
    super(x, y, Status.getSt().gameProps.getProperty("gameObjects.taxi.image"));
    
    this.radius = (int)Double.parseDouble(st.gameProps.getProperty("gameObjects.taxi.radius"));
    this.health = (int)Double.parseDouble(st.gameProps.getProperty("gameObjects.taxi.health")) * 100;
    this.damage = (int)Double.parseDouble(st.gameProps.getProperty("gameObjects.taxi.damage")) * 100;
    this.speedX = Integer.parseInt(st.gameProps.getProperty("gameObjects.taxi.speedX"));
    this.speedY = Integer.parseInt(st.gameProps.getProperty("gameObjects.taxi.speedY"));
    this.nextSpawnMaxY = Integer.parseInt(st.gameProps.getProperty("gameObjects.taxi.nextSpawnMaxY"));
    this.nextSpawnMinY = Integer.parseInt(st.gameProps.getProperty("gameObjects.taxi.nextSpawnMinY"));
  }

  public void pairTriggerActive(Object obj) {
    if (obj instanceof Fireball) {
      if (this.loc.distanceWith(((Fireball) obj).getLocClone()) < 20) {
        this.health -= 20;
      }
    }
  }

  // behavior when taxi is damaged
  private void damage() {

    // add broken taxi and fire effect
    this.getParentElement().addSubElement(
        new TaxiBroken(this.loc.getX(), this.loc.getY()));
    this.getParentElement().addSubElement(
      new Fire(this.loc.getX(), this.loc.getY())
    );

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
    this.getParentElement().addSubElement(new Taxi(new_x,new_y));

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
      this.damage();
    }
  }

  @Override
  public int radius() {
    return this.radius;
  }
}
