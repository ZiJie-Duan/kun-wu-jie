package components.item;

import components.effect.Effect;
import dependencies.Status;

public class TripEnd extends Item {
    public TripEnd(double x, double y) {
        super(x, y, Status.getSt().gameProps.getProperty("gameObjects.tripEndFlag.image"));
    }

    @Override
    public double radius() {
        return 0; //just for default
    }

    @Override
    public void pairTriggerActive(Object obj) {

    }
}
