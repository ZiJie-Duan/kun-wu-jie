
import engine.Sprite;
import engine.spread.SpreadNull;

public class Fire extends Effect {

    public Fire(int x, int y) {
        super(
                x,
                y,
                Status.getSt().gameProps.getProperty("gameObjects.fire.image"),
                Integer.parseInt(Status.getSt().gameProps.getProperty("gameObjects.fire.ttl"))
        );
    }
}
