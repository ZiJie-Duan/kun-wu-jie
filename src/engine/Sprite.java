package engine;

import engine.spread.*;
import java.awt.Image;

public abstract class Sprite<SI extends Spread, SO extends Spread> extends Element<SI, SO> {
  private Loc loc;
  private Boundary bd;
  private Image img;
  private Boolean enableBoundaryCheck = false;

  public Sprite(Loc loc, Boundary bd, Image img) {
    this.loc = loc;
    this.bd = bd;
    this.img = img;
  }

  public Sprite(Loc loc, Boundary bd, Image img, Boolean enableBoundaryCheck) {
    this.loc = loc;
    this.bd = bd;
    this.img = img;
    this.enableBoundaryCheck = enableBoundaryCheck;
  }

}
