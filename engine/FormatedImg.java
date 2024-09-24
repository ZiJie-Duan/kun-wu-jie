import bagel.Image;
import bagel.Window;
/* Please check my README file .*/
public class FormatedImg extends Loc {

  private Image img;

  public FormatedImg(String imgPath) {
    super();
    this.img = new Image(imgPath);
  }

  public void autoSetXaxisMid() {
    this.setX((int) (Window.getWidth() / 2));
  }

  public void draw() {
    this.img.draw(this.getX(), this.getY());
  }

  public void draw(int x, int y) {
    this.img.draw(x, y);
  }
}
