package components.car;

import bagel.Input;
import components.effect.Fire;
import components.effect.Smoke;
import dependencies.Status;
import engine.trigger.disTrigger.DisTrigger;
import triggers.AttackBothTrigger;
import triggers.AttackerTrigger;

import java.util.Random;

public class OtherCar extends Car {

    private double radius;
    private double health;
    private double damage;
    private int type;

    private double speedY;


    public OtherCar(double x, double y, int type) {
        super(x, y, 
        String.format(
            Status.getSt().gameProps.getProperty("gameObjects.otherCar.image"), type
        ));

        Status st = Status.getSt();

        this.radius = Double.parseDouble(st.gameProps.getProperty("gameObjects.otherCar.radius"));
        this.health = Double.parseDouble(st.gameProps.getProperty("gameObjects.otherCar.health"));
        this.damage = Double.parseDouble(st.gameProps.getProperty("gameObjects.otherCar.damage"));
        int minSpeedY = Integer.parseInt(st.gameProps.getProperty("gameObjects.otherCar.minSpeedY"));
        int maxSpeedY = Integer.parseInt(st.gameProps.getProperty("gameObjects.otherCar.maxSpeedY"));
        //this.speedX = Double.parseDouble(st.gameProps.getProperty("gameObjects.otherCar.speedX"));
        this.type = type;

        Random random = new Random();
        this.speedY = minSpeedY + random.nextInt(maxSpeedY - minSpeedY + 1);
    
    }

    @Override
    public void update(){
        this.moveY(-this.speedY);

        if (this.health <= 0){
            this.damageBehavior();
        }
    }


    @Override
    public double damageValue() {
        return this.damage;
    }

    @Override
    public void getHurts(double damage) {
        this.health -= damage;
        this.getParentElement().deferAddSubElement(new Smoke(this.loc.getX(), this.loc.getY()));
    }

    @Override
    public double radius() {
        return this.radius;
    }

    @Override
    public void pairTriggerActive(Object obj) {
        if (obj instanceof AttackerTrigger && (obj != this)){
            if (this.isCollision((DisTrigger) obj)) {
                this.getHurts(((AttackBothTrigger) obj).damageValue());
            }
        }
    }

    @Override
    protected void damageBehavior() {
        this.getParentElement().deferAddSubElement(new Fire(this.loc.getX(), this.loc.getY()));
        this.suicide();
    }
}
