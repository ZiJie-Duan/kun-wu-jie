package components.car;
import engine.spread.*;
import engine.*;
import bagel.Image;
import spread.GamePlaySpread;
import triggers.AttackBothTrigger;

public abstract class Car
    extends Sprite<GamePlaySpread, SpreadNull>
    implements AttackBothTrigger {

  public Car(double x, double y, String imgPath) {
    super(GamePlaySpread.class, SpreadNull.class);
    Loc loc = new Loc(x, y);
    Loc locLT = new Loc(-1000, -1000);
    Loc locRB = new Loc(2000, 2000);
    Boundary boundary = new Boundary(locLT, locRB);
    Image img = new Image(imgPath);

    this.init(loc, boundary, img, true);
  }

  // each car might have different damage behavior
  protected abstract void damageBehavior();
}
