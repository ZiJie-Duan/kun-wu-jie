package components.person;

import bagel.*;
import components.car.Taxi;
import components.effect.Blood;
import components.item.TripEnd;
import dependencies.Status;
import engine.IntelligentText;
import engine.Loc;
import engine.Locatable;
import engine.trigger.disTrigger.DisTrigger;
import engine.trigger.pairTrigger.TriggerWithSub;
import triggers.AttackBothTrigger;
import triggers.AttackerTrigger;

public class Passenger extends Person implements TriggerWithSub {

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

    private Taxi taxi;
    private TripEnd tripEnd;

     private int behavior = 0;
    //     0: waiting, 1: moveToTaxi, 2: onTaxi
    //     3: moveToTripEnd, 4: finishTrip

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


         this.tripEnd = new TripEnd(endx, endy);
         tripEnd.setVisibility(false);
         this.deferAddSubElement(tripEnd);
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

     private void getOnTaxi() {
         this.sI.passengerInTrip = true;
         this.setVisibility(false);
         tripEnd.setVisibility(true);
     }

     private void getOffTaxi() {
        this.sI.passengerInTrip = false;
        this.setVisibility(true);
     }

     private void finishTrip() {
         tripEnd.suicide();
     }

     private void moveToObj(Loc obj) {
         int xDir = 1;
         if (obj.getX() - this.loc.getX() < 0) {
            xDir = -1;
         }
         int yDir = 1;
         if (obj.getY() - this.loc.getY() < 0) {
             yDir = -1;
         }
         this.moveX(xDir);
         this.moveY(yDir);
     }

    @Override
    public void ctrlIn(Input input) {
        if (input.isDown(Keys.UP)) {
            this.moveY(sI.gameGlobalSpeed);
        }
    }

    @Override
    public void update() {
        super.update();

        this.sO = this.sI;

        if (behavior == 1){
            this.moveToObj(this.taxi.getLoc());
        } else if (behavior == 2){
            System.out.println("yesyes");
            this.loc.setLoc(this.taxi.getLoc().getX(), this.taxi.getLoc().getY());
        } else if (behavior == 3){
            this.moveToObj(this.tripEnd.getLoc());
        }

        this.estimateCostTextLoc.setX(this.loc.getX() - 100);
        this.priorityTextLoc.setX(this.loc.getX() - 30);
    }

    @Override
    public void pairTriggerActive(Object obj) {
        super.pairTriggerActive(obj);

        if (obj instanceof Taxi && behavior <= 1){
            if (this.loc.distanceWith(((Locatable)obj).getLoc()) < taxiGetInRadius) {
                if (!this.sI.taxiMoveing && !this.sI.passengerInTrip){
                    this.behavior = 2;
                    this.getOnTaxi();
                }
            } else if (this.loc.distanceWith(((Locatable)obj).getLoc()) < taxiDetectRadius) {
                if (!this.sI.taxiMoveing && !this.sI.passengerInTrip){
                    this.behavior = 1;
                    this.taxi = (Taxi) obj;
                } else {
                    this.behavior = 0;
                }
            }
        }

        if (obj instanceof TripEnd){
            if (this.loc.distanceWith(((Locatable)obj).getLoc()) < 2) {
                this.behavior = 4;
                this.finishTrip();
            } else if (this.loc.distanceWith(((Locatable)obj).getLoc()) < 80) {
                if (!this.sI.taxiMoveing){
                    this.behavior = 3;
                    this.getOffTaxi();
                }
            }
        }

    }

    @Override
    public void render(){
        super.render();
        this.estimateCostText.drawWithDouble(this.estimateCost);
        this.priorityText.drawWithInt(this.priority);
    }

}
