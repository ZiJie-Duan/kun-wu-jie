import bagel.Input;
import bagel.Keys;
/* Please check my README file .*/
public class TripEnd extends Sprite {

  public TripEnd(String imgPath, int x, int y) {
    super(imgPath, x, y);
  }

  @Override
  public void spreadIn(EasyMap spread) {
  }

  @Override
  public void ctrlIn(Input input) {
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
