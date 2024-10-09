import engine.Sprite;
import engine.spread.SpreadNull;

public class Blood extends Effect {

    public Blood(int x, int y) {
        super(
                x,
                y,
                Status.getSt().gameProps.getProperty("gameObjects.blood.image"),
                Integer.parseInt(Status.getSt().gameProps.getProperty("gameObjects.blood.ttl"))
        );
    }
}
