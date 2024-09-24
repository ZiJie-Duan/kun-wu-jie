import javax.print.FlavorException;

import bagel.*;
/* Please check my README file .*/
public class Loc {
  private int x;
  private int y;
  private int x1_b;
  private int y1_b;
  private int x2_b;
  private int y2_b;
  private boolean bc;
  private boolean enableLocSystem = true;

  public Loc() {
    this.x = 0;
    this.y = 0;
    this.y1_b = 0;
    this.x1_b = 0;
    this.y2_b = 0;
    this.x2_b = 0;
    this.bc = false;
  }

  public Loc(boolean enableLocSystem) {
    this.x = 0;
    this.y = 0;
    this.y1_b = 0;
    this.x1_b = 0;
    this.y2_b = 0;
    this.x2_b = 0;
    this.bc = false;
    this.enableLocSystem = enableLocSystem;
  }

  public Loc(int x, int y) {
    this.x = x;
    this.y = y;
    this.y1_b = 0;
    this.x1_b = 0;
    this.y2_b = 0;
    this.x2_b = 0;
    this.bc = false;
  }

  public Loc(int x, int y, int x1_b, int y1_b, int x2_b, int y2_b, boolean enable_bc) {
    this.x = x;
    this.y = y;
    this.y1_b = x1_b;
    this.x1_b = y1_b;
    this.y2_b = x2_b;
    this.x2_b = x2_b;
    this.bc = enable_bc;
  }

  public boolean boundaryCheck(int x, int y) {
    if ((x < x1_b) || (x > x2_b)) {
      return false;
    }
    if ((y < y1_b) || (y > y2_b)) {
      return false;
    }
    return true;
  }

  public void setBoundaryCheck(boolean bc) {
    this.bc = bc;
  }

  public void setBoundary(int x1_b, int y1_b, int x2_b, int y2_b) {
    this.x1_b = x1_b;
    this.y1_b = y1_b;
    this.x2_b = x2_b;
    this.y2_b = y2_b;
  }

  public void setLoc(int x, int y) {
    if (!this.boundaryCheck(x, y) && this.bc) {
      System.out.println("Err setLoc cross boundary");
    } else {
      this.x = x;
      this.y = y;
    }
  }

  public void setY(int y) {
    if (!this.boundaryCheck(x, y) && this.bc) {
      System.out.println("Err setY cross boundary");
    } else {
      this.y = y;
    }
  }

  public void setX(int x) {
    if (!this.boundaryCheck(x, y) && this.bc) {
      System.out.println("Err setX cross boundary");
    } else {
      this.x = x;
    }
  }

  public void moveX(int x) {
    if (!(!this.boundaryCheck(this.x + x, this.y) && this.bc)) {
      this.x += x;
    }
  }

  public void moveY(int y) {
    if (!(!this.boundaryCheck(this.x, this.y + y) && this.bc)) {
      this.y += y;
    }
  }

  public void move(int x, int y) {
    if (!(!this.boundaryCheck(this.x + x, this.y + y) && this.bc)) {
      this.x += x;
      this.y += y;
    }
  }

  public int[] getLoc() {
    return new int[] { this.x, this.y };
  }

  public int getY() {
    return this.y;
  }

  public int getX() {
    return this.x;
  }

  public double distanceWith(Loc loc) {
    return Math.sqrt(Math.pow(this.x - loc.getX(), 2) + Math.pow(this.y - loc.getY(), 2));
  }

  public boolean inBoundary(Loc loc) {
    return this.boundaryCheck(loc.getX(), loc.getY());
  }

}
