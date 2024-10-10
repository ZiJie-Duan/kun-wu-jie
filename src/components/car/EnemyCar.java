package components.car;

import components.effect.Smoke;
import dependencies.Status;

public class EnemyCar extends Car {

    private double radius;
    private double health;
    private double damage;
    private double minSpeedY;
    private double maxSpeedY;
    private double speedX;

    public EnemyCar(double x, double y) {
        super(x, y, Status.getSt().gameProps.getProperty("gameObjects.enemyCar.image"));
        Status st = Status.getSt();

        this.radius = Double.parseDouble(st.gameProps.getProperty("gameObjects.enemyCar.radius"));
        this.health = Double.parseDouble(st.gameProps.getProperty("gameObjects.enemyCar.health"));
        this.damage = Double.parseDouble(st.gameProps.getProperty("gameObjects.enemyCar.damage"));
        this.minSpeedY = Double.parseDouble(st.gameProps.getProperty("gameObjects.enemyCar.minSpeedY"));
        this.maxSpeedY = Double.parseDouble(st.gameProps.getProperty("gameObjects.enemyCar.maxSpeedY"));
        this.speedX = Double.parseDouble(st.gameProps.getProperty("gameObjects.enemyCar.speedX"));
                
        // #enemyCar
        // gameObjects.enemyCar.image=res/enemyCar.png
        // gameObjects.enemyCar.radius=32.0
        // gameObjects.enemyCar.health=1.0
        // gameObjects.enemyCar.damage=0.5
        // gameObjects.enemyCar.minSpeedY=2
        // gameObjects.enemyCar.maxSpeedY=5
        // gameObjects.enemyCar.speedX=5
        // gameObjects.enemyCar.types=2

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

    }

    @Override
    protected void damageBehavior() {

    }
}
