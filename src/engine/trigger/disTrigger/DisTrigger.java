package engine.trigger.disTrigger;
import engine.Locatable;
import engine.trigger.pairTrigger.PairTrigger;

public interface DisTrigger extends PairTrigger, Locatable {
    public double radius();
    public default boolean isCollision(DisTrigger obj){
        if (this.distanceWith(obj.getLoc()) < (this.radius() + obj.radius())){
            return true;
        } else {
            return false;
        }
    }
}