package components.person;

import bagel.Image;
import components.car.Taxi;
import components.effect.Blood;
import dependencies.Status;
import engine.Loc;
import engine.Locatable;
import engine.Sprite;
import engine.spread.SpreadNull;
import engine.trigger.disTrigger.DisTrigger;
import spread.GamePlaySpread;
import triggers.AttackBothTrigger;
import triggers.AttackTargetTrigger;
import triggers.AttackerTrigger;

public class Person extends Sprite<GamePlaySpread, SpreadNull> implements AttackTargetTrigger {

    protected double health;
    protected double radius;

    protected int crushInvincibleTime;
    protected int freezTime;
    protected int freezMoveX;
    protected int freezMoveY;

    public Person(double x, double y, String imgPath) {
        super(GamePlaySpread.class, SpreadNull.class);
        Loc loc = new Loc(x, y);
        Image img = new Image(imgPath);
        init(loc, null, img, false);
    }

    protected void initPersonArgs(double health, double radius){
        this.health = health;
        this.radius = radius;
    }

    protected void die() {
        this.getParentElement().deferAddSubElement(new Blood(this.loc.getX(), this.loc.getY()));
        this.suicide();
      }

    @Override
    public void getHurts(double damage) {
        health -= damage;
    }

    @Override
    public double radius() {
        return radius;
    }

    protected void intoFreez(Locatable obj){
        this.crushInvincibleTime = Status.getSt().getInt("crush_invincible_time");
        this.freezTime = Status.getSt().getInt("freez_time");

        //get 8 direction in to freezMoveDir
        double x = this.loc.getX();
        double y = this.loc.getY();
        double ox = obj.getLoc().getX();
        double oy = obj.getLoc().getY();

        if (x < ox){
            this.freezMoveX = -2;
        } else {
            this.freezMoveX = +2;
        }

        if (y < oy){
            this.freezMoveY = -2;
        } else {
            this.freezMoveY = +2;
        }
    }

        
    @Override
    public void update() {

        if (this.crushInvincibleTime > 0){
            this.crushInvincibleTime -= 1;
        }

        if (this.freezTime > 0) {
            this.freezTime -= 1;
            this.moveY(this.freezMoveY);
            this.moveX(this.freezMoveX);
        }

        if (this.health <= 0) {
            this.die();
        }
    }

    @Override
    public void pairTriggerActive(Object obj) {
        if ((obj instanceof AttackerTrigger) && (this.visible)){
            if (this.isCollision((DisTrigger) obj) && (this.crushInvincibleTime <= 0) && !(obj instanceof Taxi)){
                this.intoFreez((Locatable) obj);
                this.getHurts(((AttackerTrigger) obj).damageValue());
            }
        }
    }
}

