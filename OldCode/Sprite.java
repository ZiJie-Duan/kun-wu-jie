import bagel.*;
/* Please check my README file .*/
public abstract class Sprite extends GameElement {

  private Image img;
  private boolean visible = true;

  public Sprite(String imgPath) {
    super();
    this.img = new Image(imgPath);
  }

  public Sprite(String imgPath, int x, int y) {
    super();
    this.img = new Image(imgPath);
    this.setLoc(x, y);
  }

  public void draw() {
    if (visible) {
      this.img.draw(this.getX(), this.getY());
    }
  }

  public void draw(DrawOptions opt) {
    if (visible) {
      this.img.draw(this.getX(), this.getY(), opt);
    }
  }

  public void drawForce(DrawOptions opt) {
    this.img.draw(this.getX(), this.getY(), opt);
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }
}
