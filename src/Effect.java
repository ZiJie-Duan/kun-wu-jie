
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import engine.Loc;
import engine.Sprite;
import engine.spread.SpreadNull;

public abstract class Effect extends Sprite<GamePlaySpread, SpreadNull> {

  int ttl;

  public Effect(int x, int y, String imgPath, int ttl) {
    super(GamePlaySpread.class, SpreadNull.class);
    Loc loc = new Loc(x, y);
    Image img = new Image(imgPath);
    this.ttl = ttl;

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
    if (ttl <= 0) {
      suicide();
    } else {
      ttl -= 1;
    }

  }
}
