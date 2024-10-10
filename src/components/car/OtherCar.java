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

    public OtherCar(double x, double y, int type) {
        super(x, y, 
        String.format(
            Status.getSt().gameProps.getProperty("gameObjects.otherCar.image"), type
        ));

        Status st = Status.getSt();

        double radius = Double.parseDouble(st.gameProps.getProperty("gameObjects.otherCar.radius"));
        double health = Double.parseDouble(st.gameProps.getProperty("gameObjects.otherCar.health")) * 100;
        double damage = Double.parseDouble(st.gameProps.getProperty("gameObjects.otherCar.damage")) * 100;
        int minSpeedY = Integer.parseInt(st.gameProps.getProperty("gameObjects.otherCar.minSpeedY"));
        int maxSpeedY = Integer.parseInt(st.gameProps.getProperty("gameObjects.otherCar.maxSpeedY"));

        this.initCarArgs(minSpeedY, maxSpeedY, health, radius, damage);
        this.resetRandomSpeedY();
    }
}
