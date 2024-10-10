package engine;

import bagel.*;
import engine.spread.*;
import java.util.List;

import engine.trigger.*;
import engine.trigger.pairTrigger.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/* ElementController class 
* is the Core Class for whole engine.
* it used to control all components update and render.
*/
public class ElementController {
  Element<?, ?> rootElement = null;
  List<Element<?, ?>> elementList = new ArrayList<Element<?, ?>>(); // List of all elements
  // this list engerated in each frame
  // used to engerate elementList
  Queue<Element<?, ?>> elementQueue = new LinkedList<Element<?, ?>>();

  public ElementController(Element<?, ?> rootElement) {
    this.rootElement = rootElement;
  }

  private void updateElementList() {
    // generate elementList for next behavior
    // brath first spread, thus spread allow to spread deeper
    this.elementList.clear();
    this.elementQueue.clear();
    this.elementQueue.offer(this.rootElement);
    this.elementList.add(this.rootElement);
    while (!this.elementQueue.isEmpty()) {
      Element<?, ?> nodeElement = this.elementQueue.poll();
      for (Element<?, ?> element : nodeElement.getSubElementList()) {
        this.elementList.add(element);
        this.elementQueue.offer(element);
      }
    }

    // use breadth first search to sort elementList
    // then use renderPriority to sort elementList again
    // stable sort to keep the order of the same renderPriority
    this.elementList.sort(null);
    //System.out.println(this.elementList.size());
  }

  public void spread() {
    for (Element<?, ?> parentElement : this.elementList) {
      Spread spreadOut = parentElement.getSpreadOut();
      // if the parentElement's spreadOut is not SpreadNull
      // and if the subElement's spreadInClass is the same as the parentElement's
      // spreadOut it means they are matched
      // so we need to spread in to the subElement
      if (!(spreadOut instanceof SpreadNull)) {
        for (Element<?, ?> subElement : parentElement.getSubElementList()) {
          if (subElement.getSpreadInClass() == parentElement.getSpreadOutClass()) {
            subElement.setSpreadIn(spreadOut);
          }
        }
      }
    }
  }

  public void trigger() {
    for (Element<?, ?> parentElement : this.elementList) {
      if (parentElement instanceof PairTriggerBehaviorType) {
        TriggerController.pairTrigger(parentElement);
      }
    }
  }

  public void ctrlIn(Input input) {
    for (Element<?, ?> element : this.elementList) {
      element.ctrlIn(input);
    }
  }

  public void update() {
    for (Element<?, ?> element : this.elementList) {
      element.update();
    }
  }

  public void render() {
    for (Element<?, ?> element : this.elementList) {
      element.render();
    }
  }

  public void cleanSpread() {
    for (Element<?, ?> element : this.elementList) {
      element.clearSpreadIn();
    }
  }

  public void killElement() {
    for (Element<?, ?> element : this.elementList) {
      element.killSubElements();
    }
  }

  public void addElement() {
    for (Element<?, ?> element : this.elementList) {
      element.addSubElements();
    }
  }

  public void runOneFrame(Input input) {
    this.updateElementList();
    this.spread();
    this.trigger();
    this.ctrlIn(input);
    this.update();
    this.render();
    this.cleanSpread();
    this.killElement();
    this.addElement();
  }
}
