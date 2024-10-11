package triggers;

import engine.trigger.disTrigger.DisTrigger;

// impliment this interface to attack others
public interface AttackerTrigger extends DisTrigger {
    public double damageValue();
}
