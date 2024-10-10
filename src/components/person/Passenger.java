package components.person;

import bagel.*;
import components.effect.Blood;
import dependencies.Status;
import engine.IntelligentText;
import engine.Loc;
import engine.trigger.disTrigger.DisTrigger;
import triggers.AttackBothTrigger;
import triggers.AttackerTrigger;

public class Passenger extends Person {

    private double walkSpeedX;
    private double walkSpeedY;
    private double taxiDetectRadius;
    private double taxiGetInRadius;
    private double fontSize;

    private int priority;
    private double estimateCost;
    private int starty;
    private int endy;
    private int endx;

    private IntelligentText priorityText;
    private Loc priorityTextLoc;
    private IntelligentText estimateCostText;
    private Loc estimateCostTextLoc;


    // private int behavior = 0;
    // private int funnyCounter = 0;
    // private FormatedText priorityText;
    // private FormatedText estimateCostText;
    // 0: waiting, 1: moveToTaxi, 2: onTaxi
    // 3: moveToTripEnd, 4: finishTrip
    // 999: shoot/ or Be shoot by components.car.Taxi or components.person.Passenger

    public Passenger(int x, int y, int priority, int endx, int endy) {
        super(x, y, Status.getSt().gameProps.getProperty("gameObjects.passenger.image"));
        Status st = Status.getSt();

        double radius = Double.parseDouble(st.gameProps.getProperty("gameObjects.passenger.radius"));
        double health = Double.parseDouble(st.gameProps.getProperty("gameObjects.passenger.health")) * 100;
        this.initPersonArgs(health, radius);

        this.walkSpeedX = Double.parseDouble(st.gameProps.getProperty("gameObjects.passenger.walkSpeedX"));
        this.walkSpeedY = Double.parseDouble(st.gameProps.getProperty("gameObjects.passenger.walkSpeedY"));
        this.taxiDetectRadius = Double.parseDouble(st.gameProps.getProperty("gameObjects.passenger.taxiDetectRadius"));
        this.taxiGetInRadius = Double.parseDouble(st.gameProps.getProperty("gameObjects.passenger.taxiGetInRadius"));
        this.fontSize = Double.parseDouble(st.gameProps.getProperty("gameObjects.passenger.fontSize"));

        this.priority = priority;
        this.starty = y;
        this.endy = endy;
        this.endx = endx;
        this.estimateCost = calculateCost();

        this.priorityTextLoc = new Loc(this.loc);
        priorityText = new IntelligentText(
                st.gameProps.getProperty("font"),
                Integer.parseInt(st.gameProps.getProperty("gameObjects.passenger.fontSize")),
                "",
                this.priorityTextLoc);

        this.estimateCostTextLoc = new Loc(this.loc);
        estimateCostText = new IntelligentText(
                st.gameProps.getProperty("font"),
                Integer.parseInt(st.gameProps.getProperty("gameObjects.passenger.fontSize")),
                "",
                this.estimateCostTextLoc);

        //
        // TripEnd tripEnd = new TripEnd(
        // st.gameProps.getProperty("gameObjects.tripEndFlag.image"),
        // endx, endy);
        // tripEnd.setVisible(false);
        // this.addSubElement("tripEnd", tripEnd);
    }

    private double calculateCost() {
        return (starty - endy) * 0.1 + priority * priorityMap(priority);
    }

    private int priorityMap(int priority) {
        if (priority == 1) {
            return 50;
        } else if (priority == 2) {
            return 20;
        } else if (priority == 3) {
            return 10;
        }
        return 0;
    }

    // private void getOnTaxi(components.car.Taxi obj) {
    // obj.passengerIn = true;
    // this.setVisible(false);
    // TripEnd te = (TripEnd) this.getSubElement("tripEnd");
    // te.setVisible(true);
    // }
    //
    // private void getOffTaxi(components.car.Taxi obj) {
    // this.setVisible(true);
    // obj.passengerIn = false;
    // }
    //
    // private void finishTrip() {
    // TripEnd te = (TripEnd) this.getSubElement("tripEnd");
    // te.setVisible(false);
    // }
    //
    // private void moveToObj(Loc obj) {
    // int xDir = 1;
    // if (obj.getX() - this.getX() < 0) {
    // xDir = -1;
    // }
    // int yDir = 1;
    // if (obj.getY() - this.getY() < 0) {
    // yDir = -1;
    // }
    // this.move(xDir, yDir);
    // }
    //
    // private void taxiTrigger(components.car.Taxi obj) {
    // if (behavior == 0 && this.distanceWith(obj) < 25 && obj.moveing) {
    // behavior = 999;
    // if (!(this.taxi == null)) {
    // this.taxi.passengerIn = false;
    // }
    // }
    // if (behavior == 0 && this.distanceWith(obj) < 100 && !obj.moveing &&
    // !obj.passengerIn && !obj.passengerComming) {
    // this.taxi = obj;
    // obj.passengerComming = true;
    // behavior = 1;
    //
    // } else if (behavior == 1 && this.distanceWith(obj) < 100 && obj.moveing) {
    // obj.passengerComming = false;
    // behavior = 0;
    //
    // } else if (behavior == 1 && this.distanceWith(obj) <= 1 && !obj.moveing &&
    // !obj.passengerIn) {
    // obj.passengerComming = false;
    // behavior = 2;
    // this.getOnTaxi(obj);
    // }
    // }
    //
    // private void tripEndTrigger(TripEnd obj) {
    // if (behavior == 2 && !this.taxi.moveing &&
    // ((this.distanceWith(obj) < 80) ||
    // (this.taxi.getY() < this.getSubElement("tripEnd").getY()))) {
    // this.getOffTaxi(this.taxi);
    // behavior = 3;
    //
    // } else if (behavior == 3 && this.distanceWith(obj) <= 1) {
    // this.finishTrip();
    // behavior = 4;
    // }
    // }
    //
    // private void passengerShootTrigger(components.person.Passenger obj) {
    // if (behavior == 0 && this.distanceWith(obj) < 80 && obj.behavior == 999) {
    // behavior = 999;
    // }
    // }
    //
    // @Override
    // public void disTrigger(Loc obj) {
    // if (obj instanceof components.car.Taxi) {
    // this.taxiTrigger((components.car.Taxi) obj);
    // } else if (obj instanceof TripEnd && this.isSubElement((GameElement) obj)) {
    // this.tripEndTrigger((TripEnd) obj);
    // } else if (obj instanceof components.person.Passenger) {
    // this.passengerShootTrigger((components.person.Passenger) obj);
    // }
    // }
    //
    // private void doAction() {
    // if (behavior == 1) {
    // this.moveToObj(this.taxi);
    //
    // } else if (behavior == 2) {
    // this.setLoc(this.taxi.getX(), this.taxi.getY());
    //
    // } else if (behavior == 3) {
    // TripEnd te = (TripEnd) this.getSubElement("tripEnd");
    // this.moveToObj(te);
    //
    // } else if (behavior == 999) {
    // this.setVisible(false);
    // this.moveY(-15);
    // DrawOptions opt = new DrawOptions();
    // opt.setRotation(this.funnyCounter);
    // this.drawForce(opt);
    // this.funnyCounter += 1;
    // }
    // }
    //
    // @Override
    // public void spreadIn(EasyMap spread) {
    //
    // if (behavior == 2) {
    // spread.set("currentPriority", this.priority);
    // spread.set("currentFee", estimateCost);
    //
    // } else if (behavior == 3 && !this.alreadSendFlag) {
    // this.alreadSendFlag = true;
    // spread.accumulate("finishTrip", 1);
    // }
    // };

    @Override
    public void ctrlIn(Input input) {
        if (input.isDown(Keys.UP)) {
            this.moveY(sI.gameGlobalSpeed);
        }

        // if (input.isDown(Keys.A) && behavior == 2) {
        // behavior = 999;
        // this.taxi.passengerIn = false;
        // }
    }

    @Override
    public void update() {
        super.update();
        this.estimateCostTextLoc.setX(this.loc.getX() - 100);
        this.priorityTextLoc.setX(this.loc.getX() - 30);

        // this.selfTriggerWithSubElements();
        // this.doAction();
        // this.draw();
        // if (behavior <= 1) {
        // estimateCostText.setLoc(this.getX() - 100, this.getY());
        // estimateCostText.draw();
        // priorityText.setLoc(this.getX() - 30, this.getY());
        // priorityText.draw();
        // }
    }

    @Override
    public void pairTriggerActive(Object obj) {
        super.pairTriggerActive(obj);

    }

    @Override
    public void render(){
        super.render();
        this.estimateCostText.drawWithDouble(this.estimateCost);
        this.priorityText.drawWithInt(this.priority);
    }

}
