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

    private boolean hasUmbrella;
    private boolean alreadyShiftPriority = false;
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

    private boolean PassengerAlreadyPopOut = false;

     private int behavior = 0;
    //     0: waiting, 1: moveToTaxi, 2: onTaxi
    //     3: moveToTripEnd, 4: finishTrip

    public Passenger(int x, int y, int priority, int endx, int endy, int umbrella) {
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

        if (umbrella == 1) {
            this.hasUmbrella = true;
        } else {
            this.hasUmbrella = false;
        }

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
        this.sI.playerScore += this.sI.estimateCost;
     }

     private void finishTrip() {
         tripEnd.suicide();
     }

     private void passengerPopOut(){
        if (!PassengerAlreadyPopOut){
            this.loc.moveX(-100);
            this.setVisibility(true);
            PassengerAlreadyPopOut = true;
        }
     }

    private void passengerGetInAgain(){
        if (PassengerAlreadyPopOut){
            this.setVisibility(false);
            PassengerAlreadyPopOut = false;
        }
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

        if (!this.alreadyShiftPriority && this.sI.isRaining && !this.hasUmbrella){
            this.priority = 1;
            this.alreadyShiftPriority = true;
        }

        this.estimateCost = calculateCost();

        this.sO = this.sI; // share the Spread to TripEnd.

        // behavior 0 and 4 just do nothing

        // behavior 1: moveToTaxi
        if (behavior == 1){
            this.moveToObj(this.taxi.getLoc());
        
        // behavior 2: onTaxi
        } else if (behavior == 2){

            this.sI.passengerHealth = this.health;
            this.sI.estimateCost = this.estimateCost;
            this.sI.priority = this.priority;


            if (!this.sI.driverInTaxi){
                this.passengerPopOut();
                this.moveToObj(this.sI.driverLoc);
            } else {
                this.passengerGetInAgain();
                this.loc.setLoc(this.taxi.getLoc().getX(), this.taxi.getLoc().getY());
            }
            
        // behavior 3: moveToTripEnd
        } else if (behavior == 3){
            this.moveToObj(this.tripEnd.getLoc());
        }

        if ((behavior == 2) && (this.sI.levelUpFrame > 0) &&(!this.sI.alreadyLevelUP)){
            if (this.priority <= 1){
                this.priority = 1;
            } else {
                this.priority -= 1;
            }

            this.sI.alreadyLevelUP = true;
        }

        // Sync the text location with the passenger location
        this.estimateCostTextLoc.setX(this.loc.getX() - 100);
        this.estimateCostTextLoc.setY(this.loc.getY());
        this.priorityTextLoc.setX(this.loc.getX() - 30);
        this.priorityTextLoc.setY(this.loc.getY());
    }

    @Override
    public void pairTriggerActive(Object obj) {
        super.pairTriggerActive(obj);
        
        // 0: waiting, 1: moveToTaxi, 2: onTaxi
        // this code only works when the passenger is waiting or moving to taxi
        if (behavior <= 1 && obj instanceof Taxi && this.sI.driverInTaxi){

            // if the passenger is close enough to the taxi
            if (this.loc.distanceWith(((Locatable)obj).getLoc()) < taxiGetInRadius) {
                // if the taxi is not moving and there is no passenger in the taxi
                if (!this.sI.taxiMoveing && !this.sI.passengerInTrip){
                    this.behavior = 2;
                    this.getOnTaxi();
                }
            
            // if the passenger is not close enough to the taxi (but still close enough to detect it)
            } else if (this.loc.distanceWith(((Locatable)obj).getLoc()) < taxiDetectRadius) {
                // if the taxi is not moving and there is no passenger in the taxi
                if (!this.sI.taxiMoveing && !this.sI.passengerInTrip){
                    // move to the taxi
                    this.behavior = 1;
                    this.taxi = (Taxi) obj;
                } else {
                    // if the taxi is moving or there is a passenger in the taxi
                    // the passenger will back to waiting
                    this.behavior = 0;
                }
            }
        }
        
        // 2: onTaxi, 3: moveToTripEnd
        // this code only works when the passenger is on the taxi
        if (behavior <= 2 && obj instanceof TripEnd){
            // when the passenger is close enough to the trip end
            // and the taxi is not moving, then the passenger will get off the taxi
             if ((this.loc.distanceWith(((Locatable)obj).getLoc()) < 80) ||
                     (this.loc.getY() < ((TripEnd) obj).getLoc().getY())) {
                if (!this.sI.taxiMoveing){
                    this.behavior = 3;
                    this.getOffTaxi();
                }
            }
        }
        
        // 3: moveToTripEnd, 4: finishTrip
        // this code only works when the passenger is moving to the trip end
        if (behavior <= 3 && obj instanceof TripEnd){
            // when the passenger is close enough to the trip end
            // the passenger will finish the trip
          if (this.loc.distanceWith(((Locatable)obj).getLoc()) < 2) {
            this.behavior = 4;
            this.finishTrip();
        }}

    }

    @Override
    public void die(){
        super.die();
        this.sI.passengerInTrip = false; // passenger is dead
        // so the passenger is not in the trip
    }

    @Override
    public void render(){
        super.render();

        // before the passenger get on the taxi
        //  the passenger will show the priority and the estimate cost
        if (this.behavior <= 1){
            this.estimateCostText.drawWithDouble(this.estimateCost);
            this.priorityText.drawWithInt(this.priority);
        }
    }

}
