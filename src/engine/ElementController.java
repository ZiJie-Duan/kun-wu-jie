
package engine;

public class ElementController {
  Element<?, ?> rootElement;
  SpreadController spreadController;
  TriggerController triggerController;

  public void killElement() {
  }

  public void spread() {
  }

  public void trigger() {
  }

  public void ctrlIn() {
  }

  public void update() {
  }

  public void render() {
  }

  public void cleanSpread() {
  }

  public void run() {
    this.killElement();
    this.spread();
    this.trigger();
    this.ctrlIn();
    this.update();
    this.render();
    this.cleanSpread();
  }
}
