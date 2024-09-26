package engine;

import bagel.*;

/* FormatedImg used to combile Location and Image
* put image in a fix location */
public class FormatedImg {

  private Image img;
  private Loc loc;
  private DrawOptions opt = null;

  public FormatedImg(String imgPath, Loc loc) {
    this.img = new Image(imgPath);
    this.loc = loc;
  }

  public FormatedImg(String imgPath, Loc loc, DrawOptions opt) {
    this.img = new Image(imgPath);
    this.loc = loc;
    this.opt = opt;
  }

  public void draw() {
    if (opt != null) {
      this.img.draw(this.loc.getX(), this.loc.getY(), opt);
    } else {
      this.img.draw(this.loc.getX(), this.loc.getY());
    }
  }
}
