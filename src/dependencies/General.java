package dependencies;

import bagel.Font;

public class General {

  public static int getMidXlocForScreenWithFixWide(int textWide, int screenWide) {
    return (int) (screenWide - textWide) / 2;
  }

  public static int getStringWideWithFixFront(String front, int size, String text) {
    Font ft = new Font(front, size);
    return (int) ft.getWidth(text);
  }

}
