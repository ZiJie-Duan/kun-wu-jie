package components.effect;

import dependencies.Status;

public class Fire extends Effect {

    public Fire(double x, double y) {
        super(
                x,
                y,
                Status.getSt().gameProps.getProperty("gameObjects.fire.image"),
                Integer.parseInt(Status.getSt().gameProps.getProperty("gameObjects.fire.ttl"))
        );
    }
}
