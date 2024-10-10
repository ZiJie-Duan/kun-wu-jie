package components.car;

import components.effect.Fire;
import components.effect.Smoke;
import components.item.Fireball;
import dependencies.Status;
import engine.Locatable;
import engine.trigger.disTrigger.DisTrigger;
import triggers.AttackBothTrigger;
import triggers.AttackerTrigger;

import java.util.Random;

public class EnemyCar extends Car {

    private double fireballRadius;

    public EnemyCar(double x, double y) {
        super(x, y, Status.getSt().gameProps.getProperty("gameObjects.enemyCar.image"));
        Status st = Status.getSt();

        double radius = Double.parseDouble(st.gameProps.getProperty("gameObjects.enemyCar.radius"));
        double health = Double.parseDouble(st.gameProps.getProperty("gameObjects.enemyCar.health")) * 100;
        double damage = Double.parseDouble(st.gameProps.getProperty("gameObjects.enemyCar.damage")) * 100;
        double fireballRadius = Double.parseDouble(st.gameProps.getProperty("gameObjects.fireball.radius"));
        int minSpeedY = Integer.parseInt(st.gameProps.getProperty("gameObjects.enemyCar.minSpeedY"));
        int maxSpeedY = Integer.parseInt(st.gameProps.getProperty("gameObjects.enemyCar.maxSpeedY"));

        this.initCarArgs(minSpeedY, maxSpeedY, health, radius, damage);
        this.resetRandomSpeedY();

    }

    @Override
    public void update() {
        super.update();
        Random random = new Random();
        if (((random.nextInt(1000) + 1) % 300) == 0) {
            this.getParentElement().deferAddSubElement(new Fireball(this.loc.getX(),
                    this.loc.getY() - this.radius - this.fireballRadius));
        }
    }


}
