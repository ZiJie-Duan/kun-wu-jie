import bagel.Input;
import bagel.Keys;
/* Please check my README file .*/
public class Coin extends Sprite implements DisTrigger {

  public Coin(String imgPath, int x, int y) {
    super(imgPath, x, y);
  }

  @Override
  public void disTrigger(Loc obj) {
    // if the object is a taxi and the distance between the coin and the taxi is less than 100
    if (obj instanceof Taxi) {
      if (this.distanceWith(obj) < 100) {
        this.suicide();
      }
    }
  }

  @Override
  public void spreadIn(EasyMap spread) {
  };

  @Override
  public void ctrlIn(Input input) {
    // move down with taxi
    if (input.isDown(Keys.UP)) {
      this.moveY(5);
    }
  }

  @Override
  public void update(Input input) {
    this.draw();
    super.update(input);
  }
}
