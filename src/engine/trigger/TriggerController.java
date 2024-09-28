
package engine.trigger;

import engine.trigger.pairTrigger.*;

import engine.Element;

/* TriggerController is a core part for all the Trigger in engine
 * depend different interface be implment by different class 
 * TriggerController will automaticly trigger it 
 * TriggerController control Element class to do action
 * Thus this class have High coupling level with Element class */
public class TriggerController {

  private static void pairTriggerActive(PairTrigger e1, PairTrigger e2) {
    if (e1.pairTriggerType() == e2.pairTriggerType()) {
      e1.pairTriggerActive(e2);
      e2.pairTriggerActive(e1);
    }
  }

  public static void pairTrigger(Element<?, ?> parentElement) {
    if (parentElement instanceof PairTriggerBehaviorType) {

      // parent trigger with subElements and subElements trigger with subElements
      // this behavior aquires parentElement is PairTrigger Type
      if (parentElement instanceof TriggerAll && parentElement instanceof PairTrigger) {
        // try each subElement in parentElement is PairTrigger or not
        for (Element<?, ?> subElement : parentElement.getSubElementList()) {
          if (subElement instanceof PairTrigger) {

            TriggerController.pairTriggerActive((PairTrigger) parentElement, (PairTrigger) subElement);
          }
        }

        // subElements trigger with subElements
        for (Element<?, ?> sube1 : parentElement.getSubElementList()) {
          for (Element<?, ?> sube2 : parentElement.getSubElementList()) {

            // check if sube1 and sube2 is PairTrigger
            if (sube1 instanceof PairTrigger && sube2 instanceof PairTrigger) {
              TriggerController.pairTriggerActive((PairTrigger) sube1, (PairTrigger) sube2);
            }
          }
        }

      } else {
        // parent trigger with subElements
        // this behavior aquires parentElement is PairTrigger Type
        if (parentElement instanceof TriggerWithSub && parentElement instanceof PairTrigger) {
          // try each subElement in parentElement is PairTrigger or not
          for (Element<?, ?> subElement : parentElement.getSubElementList()) {
            if (subElement instanceof PairTrigger) {

              TriggerController.pairTriggerActive((PairTrigger) parentElement, (PairTrigger) subElement);
            }
          }
        }

        // only sbuElements trigger with subElements
        // thus parentElement not need to be any PairTrigger
        if (parentElement instanceof TriggerSub) {
          for (Element<?, ?> sube1 : parentElement.getSubElementList()) {
            for (Element<?, ?> sube2 : parentElement.getSubElementList()) {
              // check if sube1 and sube2 is PairTrigger
              if (sube1 instanceof PairTrigger && sube2 instanceof PairTrigger) {
                TriggerController.pairTriggerActive((PairTrigger) sube1, (PairTrigger) sube2);
              }
            }
          }
        }
      }
    }
  }
}
