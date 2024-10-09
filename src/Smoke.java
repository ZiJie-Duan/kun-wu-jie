import bagel.Image;
import bagel.Input;
import engine.Loc;
import engine.Sprite;
import engine.spread.SpreadNull;

public class Smoke extends Effect {

    int duration;

    public Smoke(int x, int y) {
        super(
                x,
                y,
                Status.getSt().gameProps.getProperty("gameObjects.smoke.image"),
                Integer.parseInt(Status.getSt().gameProps.getProperty("gameObjects.smoke.ttl"))
        );
    }
}
