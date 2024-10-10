package components.effect;

import dependencies.Status;
import engine.spread.SpreadNull;
import engine.*;
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import spread.GamePlaySpread;

public class TaxiBroken extends Effect {

  public TaxiBroken(double x, double y) {
    super(x, y,
            Status.getSt().gameProps.getProperty("gameObjects.taxi.damagedImage"),
            Integer.parseInt(Status.getSt().gameProps.getProperty("gamePlay.maxFrames")));
  }
}
