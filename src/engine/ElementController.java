package engine;

import java.util.List;
import java.util.Stack;
import engine.trigger.*;

/* ElementController class 
* is the Core Class for whole engine.
* it used to control all components update and render.
*/
public class ElementController {
  Element<?, ?> rootElement;
  List<Element<?, ?>> elementList; // List of all elements
  // this list engerated in each frame
  Stack<Element<?, ?>> elementStack; // Stack of all elements
  // used to engerate elementList

  private void updateElementList() {
    // generate elementList for next behavior
    // brath first spread, thus spread allow to spread deeper
    this.elementList.clear();
    this.elementStack.clear();
    this.elementStack.push(this.rootElement);
    while (!this.elementStack.isEmpty()) {
      Element<?, ?> nodeElement = this.elementStack.pop();
      for (Element<?, ?> element : nodeElement.getSubElementList()) {
        this.elementList.add(element);
      }
    }
  }

  public void spread() {
    for (Element<?, ?> parentElement : this.elementList) {
      Spread spreadOut = parentElement.getSpreadOut();
      // if the parentElement's spreadOut is not null
      // and if the subElement's spreadInClass is the same as the parentElement's
      // spreadOut it means they are matched
      // so we need to spread in to the subElement
      if (spreadOut != null) {
        for (Element<?, ?> subElement : parentElement.getSubElementList()) {
          if (subElement.getSpreadInClass().isInstance(spreadOut)) {
            subElement.setSpreadIn(spreadOut);
          }
        }
      }
      // important note!!!! spreatOut might use a defual type extends from Spread
      // and might have more acurate target, not sure yet.
    }
  }

  public void trigger() {
    for (Element<?, ?> parentElement : this.elementList) {
      if (parentElement instanceof TriggerBehaviorType) {
        if (parentElement instanceof TriggerAll || parentElement instanceof TriggerWithSub) {
          for (Element<?, ?> subElement : parentElement.getSubElementList()) {
          }
        }
      }
    }
  }

  public void ctrlIn() {
  }

  public void update() {
  }

  public void render() {
  }

  public void cleanSpread() {
  }

  public void killElement() {
  }

  public void run() {
    this.updateElementList();
    this.killElement();
    this.spread();
    this.trigger();
    this.ctrlIn();
    this.update();
    this.render();
    this.cleanSpread();
  }
}
