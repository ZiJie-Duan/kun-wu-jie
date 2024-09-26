package engine;

import java.awt.Image;

public abstract class Sprite<SI, SO> extends Element<SI, SO> {

  private Loc loc;
  private Boundary bd;
  private Image img;
  private Boolean enableBoundaryCheck = false;
  private Boolean visible = true;

  public void moveX(int x) {
    this.loc.moveX(x);
  }

  public void moveY(int y) {

  }
}
