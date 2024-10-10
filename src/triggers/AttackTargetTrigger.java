package triggers;

import engine.trigger.disTrigger.DisTrigger;

public interface AttackTargetTrigger extends DisTrigger {
    public void getHurts(double damage);
}
