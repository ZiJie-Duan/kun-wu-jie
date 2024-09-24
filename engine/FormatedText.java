import bagel.*;
import java.util.Properties;

import javax.crypto.spec.PBEKeySpec;
/* Please check my README file .*/
public class FormatedText extends Loc {

  private Font font;
  private int size;
  private String text;

  public FormatedText(String fontpath, int size) {
    super();
    this.font = new Font(fontpath, size);
  }

  public FormatedText(String fontpath, int size, String text) {
    super();
    this.font = new Font(fontpath, size);
    this.text = text;
  }

  public void autoSetXaxisMid() {
    this.setX((int) (Window.getWidth() - font.getWidth(text)) / 2);
  }

  public void autoSetXaxisMid(String text) {
    this.setX((int) (Window.getWidth() - font.getWidth(text)) / 2);
  }

  public void draw() {
    if (text == null) {
      System.out.println("Draw Null String");
    } else {
      font.drawString(text, this.getX(), this.getY());
    }
  }

  public void draw(String text) {
    font.drawString(text, this.getX(), this.getY());
  }

  public void autoXDraw(String text) {
    this.autoSetXaxisMid(text);
    font.drawString(text, this.getX(), this.getY());
  }

  public void draw(DrawOptions opt) {
    if (this.text == null) {
      System.out.println("Draw Null String");
    } else {
      this.font.drawString(this.text, this.getX(), this.getY(), opt);
    }
  }

  public void draw(String text, DrawOptions opt) {
    this.font.drawString(text, this.getX(), this.getY(), opt);
  }

  public void autoXDraw(String text, DrawOptions opt) {
    this.autoSetXaxisMid(text);
    this.font.drawString(text, this.getX(), this.getY(), opt);
  }

  public void drawWithExtraInt(int num) {
    font.drawString(text + num, this.getX(), this.getY());
  }

  public void drawWithExtraDouble(Double num) {
    font.drawString(text + String.format("%.2f", num), this.getX(), this.getY());
  }

}
