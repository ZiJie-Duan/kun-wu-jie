import engine.spread.SpreadNull;
import engine.*;
import bagel.Image;
import bagel.Input;
import bagel.Keys;

public class TaxiBroken extends Sprite<GamePlaySpread, SpreadNull> {

  public TaxiBroken(int x, int y) {
    super(GamePlaySpread.class, SpreadNull.class);
    Status st = Status.getSt();
    Loc loc = new Loc(x, y);
    Image img = new Image(st.gameProps.getProperty("gameObjects.taxi.damagedImage"));

    this.init(loc, null, img, false);
  }

  @Override
  public void ctrlIn(Input input) {
    if (input.isDown(Keys.UP)) {
      this.moveY(this.sI.taxiSpeed);
    }
  }

  @Override
  public void update() {
  }

}
