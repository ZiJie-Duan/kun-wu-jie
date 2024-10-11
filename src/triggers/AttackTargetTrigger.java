package triggers;

import engine.trigger.disTrigger.DisTrigger;

// impliment this to be attacked
public interface AttackTargetTrigger extends DisTrigger {
    public void getHurts(double damage);
}
