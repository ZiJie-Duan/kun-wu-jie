package engine;

/* boundary class to control boundary in the game 
 * boundary defined by two points
 * top left point and bottom right point*/
public class Boundary {

  private Loc pointTL; // point top left
  private Loc pointBR; // point bottom right

  public Boundary(Loc tl, Loc br) {
    this.pointTL = tl;
    this.pointBR = br;
  }

  public void setBoundary(Loc tl, Loc br) {
    this.pointTL = tl;
    this.pointBR = br;
  }

  public Loc[] getBoundary() {
    // get location as array. x and y
    return new Loc[] { this.pointTL, this.pointBR };
  }

  public boolean inBoundary(Loc point) {
    // test is a point in a boundary or not.
    int px = point.getX();
    int py = point.getY();

    if (px < this.pointTL.getX() || px > this.pointBR.getX()) {
      return false;
    }

    if (py < this.pointTL.getY() || py > this.pointBR.getY()) {
      return false;
    }

    return true;
  }
}