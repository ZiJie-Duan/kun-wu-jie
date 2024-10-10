
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import engine.Loc;
import engine.Sprite;
import engine.spread.SpreadNull;
import engine.trigger.pairTrigger.PairTrigger;

public abstract class Item extends Sprite<GamePlaySpread, SpreadNull>
    implements PairTrigger, DisTrigger {

  public Item(int x, int y, String imgPath) {
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

  @Override
  public void update() {
  }
}
