package engine;

/* Loc class for single point location */
public class Loc implements Locatable{
  private double x;
  private double y;

  public Loc() {
    this.x = 0;
    this.y = 0;
  }

  public Loc(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void setLoc(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void moveX(double x) {
    this.x += x;
  }

  public void moveY(double y) {
    this.y += y;
  }

  public void move(double x, double y) {
    this.x += x;
    this.y += y;
  }

  public double getY() {
    return this.y;
  }

  public double getX() {
    return this.x;
  }

  public double distanceWith(double x, double y) {
    return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
  }

  @Override
  public Loc getLoc() {
    return new Loc(this.x, this.y);
  }

  @Override
  public double distanceWith(Loc loc) {
    return Math.sqrt(Math.pow(this.x - loc.getX(), 2) + Math.pow(this.y - loc.getY(), 2));
  }

}
