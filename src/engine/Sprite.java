package engine;

import java.awt.Image;

public abstract class Sprite<SI, SO> extends Element<SI, SO> {

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
