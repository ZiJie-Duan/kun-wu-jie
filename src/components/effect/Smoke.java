package components.effect;

import dependencies.Status;

public class Smoke extends Effect {

    public Smoke(double x, double y) {
        super(
                x,
                y,
                Status.getSt().gameProps.getProperty("gameObjects.smoke.image"),
                Integer.parseInt(Status.getSt().gameProps.getProperty("gameObjects.smoke.ttl"))
        );
    }
}
