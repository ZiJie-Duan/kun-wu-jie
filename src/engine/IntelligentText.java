package engine;

import bagel.DrawOptions;
import bagel.Font;

public class IntelligentText extends FormatedText{

    private String prefix;

    public IntelligentText(String fontpath, int size, String text) {
        super(fontpath,size,text);
        prefix = this.text;
    }

    public IntelligentText(String fontpath, int size, String text, Loc loc) {
        super(fontpath,size,text,loc);
        prefix = this.text;
    }

    public IntelligentText(String fontpath, int size, String text, Loc loc, DrawOptions opt) {
        super(fontpath,size,text,loc, opt);
        prefix = this.text;
    }

    public void setX(int x){
        this.loc.setX(x);
    }

    public void setY(int y){
        this.loc.setY(y);
    }

    public void drawWithString(String text){
        this.text = this.prefix + text;
        this.draw();
    }

    public void drawWithInt(Integer num){
        this.text = this.prefix + String.valueOf(num);
        this.draw();
    }

    public void drawWithDouble(Double num){
        this.text = this.prefix + String.valueOf(num);
        this.draw();
    }
}
