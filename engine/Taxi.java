import bagel.Input;
import bagel.Keys;

/* Please check my README file .*/
public class Taxi extends Sprite {

  private boolean neverHasPassenger = true;
  public boolean moveing = false;
  public boolean passengerIn = false;
  public boolean passengerComming = false;

  public Taxi(String imgPath, int x, int y) {
    super(imgPath, x, y);
  }

  @Override
  public void spreadIn(EasyMap spread) {
    if (passengerIn) {
      neverHasPassenger = false;
    }
    if (neverHasPassenger) {
      spread.set("tripStatus", 0);
    }
    if (passengerIn && !neverHasPassenger) {
      spread.set("tripStatus", 1);
    } else if (!neverHasPassenger) {
      spread.set("tripStatus", 2);
    }

  };

  @Override
  public void ctrlIn(Input input) {
    boolean moveing = false;
    if (input.isDown(Keys.UP)) {
      moveing = true;
    }
    if (input.isDown(Keys.LEFT)) {
      moveing = true;
      this.moveX(-1);
    }
    if (input.isDown(Keys.RIGHT)) {
      moveing = true;
      this.moveX(+1);
    }
    if (moveing) {
      this.moveing = true;
    } else {
      this.moveing = false;
    }
  }

  @Override
  public void update(Input input) {
    this.draw();
    super.update(input);
  }
}
