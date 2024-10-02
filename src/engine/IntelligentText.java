package engine;

import bagel.DrawOptions;
import bagel.Font;

public class IntelligentText extends FormatedText {

  private String prefix;
  private boolean enabled_AutoX = false;
  private int screenWidth;

  public IntelligentText(String fontpath, int size, String text) {
    super(fontpath, size, text);
    prefix = this.text;
  }

  public IntelligentText(String fontpath, int size, String text, Loc loc) {
    super(fontpath, size, text, loc);
    prefix = this.text;
  }

  public IntelligentText(String fontpath, int size, String text, Loc loc, DrawOptions opt) {
    super(fontpath, size, text, loc, opt);
    prefix = this.text;
  }

  public void enable_AutoX(int width) {
    this.enabled_AutoX = true;
    this.screenWidth = width;
  }

  public void setX(int x) {
    this.loc.setX(x);
  }

  public void setY(int y) {
    this.loc.setY(y);
  }

  @Override
  public void draw() {
    if (this.enabled_AutoX) {
      this.loc.setX((this.screenWidth - this.getTextWidth()) / 2);
    }
    super.draw();
  }

  public void drawWithString(String text) {
    this.text = this.prefix + text;
    this.draw();
  }

  public void drawWithInt(Integer num) {
    this.text = this.prefix + String.valueOf(num);
    this.draw();
  }

  public void drawWithDouble(Double num) {
    this.text = this.prefix + String.valueOf(num);
    this.draw();
  }
}
