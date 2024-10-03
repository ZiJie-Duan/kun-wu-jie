import bagel.Input;
import bagel.Keys;
import bagel.Window;
import engine.Element;
import engine.FormatedImg;
import engine.Loc;
import engine.spread.SpreadNull;
import spread.GamePlaySpread;

public class Road extends Element<GamePlaySpread, SpreadNull> {

    private Loc roadLoc1;
    private Loc roadLoc2;
    private FormatedImg roadImg1;
    private FormatedImg roadImg2;
    private FormatedImg roadRainImg1;
    private FormatedImg roadRainImg2;
    private Status st;

    public Road(){
        st = Status.getSt();
        roadLoc1 = new Loc(512, 384);
        roadLoc2 = new Loc(512, -384);
        roadImg1 = new FormatedImg(st.gameProps.getProperty("backgroundImage.sunny"), roadLoc1);
        roadImg2 = new FormatedImg(st.gameProps.getProperty("backgroundImage.sunny"), roadLoc2);
        roadRainImg1 = new FormatedImg(st.gameProps.getProperty("backgroundImage.raining"), roadLoc1);
        roadRainImg2 = new FormatedImg(st.gameProps.getProperty("backgroundImage.raining"), roadLoc2);
    }

    @Override
    public void ctrlIn(Input input) {
        if (input.isDown(Keys.UP)) {
            roadLoc1.moveY(this.sI.taxiSpeed);
            roadLoc2.moveY(this.sI.taxiSpeed);

            if (roadLoc1.getY() > 1152 || roadLoc2.getY() > 1152) {
                if (roadLoc1.getY() > roadLoc2.getY()) {
                    roadLoc1.setY(roadLoc2.getY() - this.st.getInt("window_height"));
                } else {
                    roadLoc2.setY(roadLoc1.getY() - this.st.getInt("window_height"));
                }
            }
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        if (this.sI.isRaining) {
            roadRainImg1.draw();
            roadRainImg2.draw();
        } else {
            roadImg1.draw();
            roadImg2.draw();
        }
    }
}
