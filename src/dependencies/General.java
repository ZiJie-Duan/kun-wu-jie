package dependencies;

import bagel.Font;

public class General {

    public static int getMidXlocForScreenWithFixWide(int wide, int screenWide){
        return (int) (screenWide - wide)/2 ;
    }

    public static int getStringWideWithFixFront(String front, int size, String text){
        Font ft = new Font(front, size);
        return (int)ft.getWidth(text);
    }

}
