
package engine.trigger;

import engine.trigger.pairTrigger.*;

import engine.Element;

/* TriggerController is a core part for all the Trigger in engine
 * depend different interface be implment by different class 
 * TriggerController will automaticly trigger it 
 * TriggerController control Element class to do action
 * Thus this class have High coupling level with Element class */
public class TriggerController {

  public static void pairTrigger(Element<?, ?> parentElement) {
    if (parentElement instanceof PairTriggerActiveType) {

      // parent trigger with subElements and subElements trigger with subElements
      // this behavior aquires parentElement is PairTrigger Type
      if (parentElement instanceof TriggerAll && parentElement instanceof PairTrigger) {
      }

      // parent trigger with subElements
      // this behavior aquires parentElement is PairTrigger Type
      if (parentElement instanceof TriggerWithSub && parentElement instanceof PairTrigger) {
      }

      // only sbuElements trigger with subElements
      // thus parentElement not need to be any PairTrigger
      if (parentElement instanceof TriggerSub) {
      }
    }
  }
}
