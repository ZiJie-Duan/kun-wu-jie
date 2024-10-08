
import bagel.Input;
import bagel.Keys;
import engine.spread.*;
import engine.trigger.pairTrigger.PairTrigger;
import engine.*;
import bagel.Image;

public abstract class Car
    extends Sprite<GamePlaySpread, SpreadNull>
    implements PairTrigger {

  private Status st = Status.getSt();

  public Car(int x, int y, String imgPath) {
    super(GamePlaySpread.class, SpreadNull.class);
    Loc loc = new Loc(x, y);
    Loc locLT = new Loc(0, 0);
    Loc locRB = new Loc(800, 600);
    Boundary boundary = new Boundary(locLT, locRB);
    Image img = new Image(imgPath);

    this.init(loc, boundary, img, true);
  }

}
