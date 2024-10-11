package components.item;

import components.car.Taxi;
import components.effect.Effect;
import components.person.Driver;
import dependencies.Status;
import engine.Locatable;
import engine.trigger.disTrigger.DisTrigger;

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
        if (this.isCollision((DisTrigger)obj)){
            if (obj instanceof Taxi){
                this.suicide();
            }
            if (obj instanceof Driver){
                this.suicide();
            }
        }

    }

    @Override
    public void update() {
        if (this.ttl <= 0) {
            this.suicide();
        }
        this.ttl -= 1;
    }

}
