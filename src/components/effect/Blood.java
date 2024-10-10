package components.effect;

import dependencies.Status;

public class Blood extends Effect {

    public Blood(double x, double y) {
        super(
                x,
                y,
                Status.getSt().gameProps.getProperty("gameObjects.blood.image"),
                Integer.parseInt(Status.getSt().gameProps.getProperty("gameObjects.blood.ttl"))
        );
    }
}
