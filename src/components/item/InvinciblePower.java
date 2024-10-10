package components.item;

import components.effect.Effect;
import dependencies.Status;

public class InvinciblePower extends Item {

    private double radius;
    private int ttl;

    public InvinciblePower(double x, double y) {
        super(x, y, Status.getSt().gameProps.getProperty("gameObjects.invinciblePower.image"));
        this.radius = Double.parseDouble(Status.getSt().gameProps.getProperty("gameObjects.invinciblePower.radius"));
        this.ttl = Integer.parseInt(Status.getSt().gameProps.getProperty("gameObjects.invinciblePower.maxFrames"));
    }

    @Override
    public double radius() {
        return this.radius;
    }

    @Override
    public void pairTriggerActive(Object obj) {

    }

    @Override
    public void update() {
        if (this.ttl <= 0) {
            this.suicide();
        }
        this.ttl -= 1;
    }

}
