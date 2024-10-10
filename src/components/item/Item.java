package components.item;

import bagel.Image;
import bagel.Input;
import bagel.Keys;
import dependencies.Status;
import engine.Loc;
import engine.Sprite;
import engine.spread.SpreadNull;
import engine.trigger.disTrigger.DisTrigger;
import spread.GamePlaySpread;

public abstract class Item extends Sprite<GamePlaySpread, SpreadNull>
    implements DisTrigger {

  public Item(double x, double y, String imgPath) {
    super(GamePlaySpread.class, SpreadNull.class);
    Status st = Status.getSt();
    Loc loc = new Loc(x, y);
    Image img = new Image(imgPath);
    init(loc, null, img, false);
  }

  @Override
  public void ctrlIn(Input input) {
    if (input.isDown(Keys.UP)) {
      this.moveY(this.sI.gameGlobalSpeed);
    }
  }
}
