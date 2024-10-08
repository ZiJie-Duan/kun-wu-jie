import engine.*;
import engine.spread.SpreadNull;
import bagel.*;
import engine.trigger.pairTrigger.PairTrigger;

public class Fireball extends Sprite<SpreadNull, SpreadNull>
    implements PairTrigger {

  public Fireball(int x, int y) {
    super(SpreadNull.class, SpreadNull.class);

    Loc loc = new Loc(x, y);
    Loc locLT = new Loc(0, 0);
    Loc locRB = new Loc(800, 600);
    Boundary boundary = new Boundary(locLT, locRB);
    Image img = new Image("res/fireball.png");
    this.init(loc, boundary, img);
  }

  public void pairTriggerActive(Object obj) {

  }

  @Override
  public void ctrlIn(Input input) {
  }

  @Override
  public void update() {
    this.moveY(-5);
  }
}
