
package engine.trigger;

import engine.Element;

/* TriggerController is a core part for all the Trigger in engine
 * depend different interface be implment by different class 
 * TriggerController will automaticly trigger it 
 * TriggerController control Element class to do action
 * Thus this class have High coupling level with Element class */
public class TriggerController {

  public static boolean triggerWithDifferentBehavior(Element<?, ?> parentElement) {
    if (parentElement instanceof TriggerBehaviorType) {
      if (parentElement instanceof TriggerAll || parentElement instanceof TriggerWithSub) {
        for (Element<?, ?> subElement : parentElement.getSubElementList()) {
        }
      } else if (parentElement instanceof TriggerAll || parentElement instanceof TriggerSub) {
        for (Element<?, ?> subElement : parentElement.getSubElementList()) {
        }
      }
    }
  }

  public static void trigger(Element parentElement) {
    TriggerController.triggerWithDifferentBehavior(parentElement);
  }

}
