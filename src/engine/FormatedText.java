package engine;

import bagel.*;

/* FormatedText used to combile Location and Text
 * put text in a fix location 
 * */
public class FormatedText {

  private Loc loc;
  private DrawOptions opt = null;
  private Font font;
  private String text;

  public FormatedText(String fontpath, int size, String text, Loc loc) {
    this.font = new Font(fontpath, size);
    this.text = text;
    this.loc = loc;
  }

  public FormatedText(String fontpath, int size, String text, Loc loc, DrawOptions opt) {
    this.font = new Font(fontpath, size);
    this.text = text;
    this.loc = loc;
    this.opt = opt;
  }

  public void draw() {
    if (this.opt != null) {
      this.font.drawString(this.text, this.loc.getX(), this.loc.getY(), this.opt);
    } else {
      this.font.drawString(this.text, this.loc.getX(), this.loc.getY());
    }
  }
}
