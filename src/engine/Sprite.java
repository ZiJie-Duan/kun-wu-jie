package engine;

import bagel.Input;
import engine.spread.*;
import bagel.Image;
import engine.trigger.disTrigger.DisTrigger;

/*  Sprite class to control sprite in the game 
 *  sprite defined by location, boundary and image
 *   provide some basic function to control sprite
 *   move, boundary check, and defualt render function
 *  */
public abstract class Sprite<SI extends Spread, SO extends Spread> extends Element<SI, SO> implements Locatable{
  protected Loc loc;
  protected Boundary bd;
  protected Image img;
  protected Boolean enableBoundaryCheck = false;
  protected Boolean visible = true;

  public Sprite(Class<SI> sIClass, Class<SO> sOClass) {
    super(sIClass, sOClass);
  }

  public void init(Loc loc, Boundary bd, Image img) {
    // here i am not use the constructora
    // because i want other class to build up resouce and pass it to Sprite
    this.loc = loc;
    this.bd = bd;
    this.img = img;
  }

  public void init(Loc loc, Boundary bd, Image img, Boolean enableBoundaryCheck) {
    this.loc = loc;
    this.bd = bd;
    this.img = img;
    this.enableBoundaryCheck = enableBoundaryCheck;
  }

  public void setBoundary(Boolean bd) {
    this.enableBoundaryCheck = bd;
  }

  public void setVisibility(Boolean visible) {
    this.visible = visible;
  }

  protected void moveX(double dx) {
    if (enableBoundaryCheck) {
      if (bd.isInBoundary(loc.getX() + dx, loc.getY())) {
        this.loc.moveX(dx);
      }
    } else {
      this.loc.moveX(dx);
    }
  }

  protected void moveY(double dy) {
    if (enableBoundaryCheck) {
      if (bd.isInBoundary(loc.getX(), loc.getY() + dy)) {
        this.loc.moveY(dy);
      }
    } else {
      this.loc.moveY(dy);
    }
  }

  protected void setloc(double x, double y) {
    if (enableBoundaryCheck) {
      if (bd.isInBoundary(x, y)) {
        this.loc.setLoc(x, y);
      }
    } else {
      this.loc.setLoc(x, y);
    }
  }


  @Override
  public void ctrlIn(Input input) {

  }

  @Override
  public void update() {

  }

  @Override
  public void render() {
    if (visible) {
      img.draw(loc.getX(), loc.getY());
    }
  }

  @Override
  public Loc getLoc() {
    return this.loc.getLoc();
  }

  @Override
  public double distanceWith(Loc loc) {
    return this.loc.distanceWith(loc);
  }
}
