package engine;

/* Loc class for single point location */
public class Loc {
  private int x;
  private int y;

  public Loc() {
    this.x = 0;
    this.y = 0;
  }

  public Loc(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void setLoc(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void moveX(int x) {
    this.x += x;
  }

  public void moveY(int y) {
    this.y += y;
  }

  public void move(int x, int y) {
    this.x += x;
    this.y += y;
  }

  public int[] getLoc() {
    // get location as array. x and y
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

}
