package engine;

import bagel.Input;
import engine.spread.*;

public abstract class Page<SI extends Spread, SO extends Spread> extends Element<SI, SO>{

  private Boundary bd;
  private boolean enableBoundaryKiller = false;

  public Page(Class<SI> sIClass, Class<SO> sOClass, Boundary bd) {
    super(sIClass, sOClass);
    this.bd = bd;
    // default enable boundary killer in this constructor
    this.enableBoundaryKiller = true;
  }

  public Page(Class<SI> sIClass, Class<SO> sOClass) {
    super(sIClass, sOClass);
    this.bd = null;
  }

  // careful, this method will kill the subElement if it is out of boundary,
  // and you need to manually call this method
  // and this boundaryKiller only works for Locatable Objects
  public void boundaryKiller() {
    if (this.enableBoundaryKiller) {
      for (Element<?, ?> subElement : this.getSubElementList()) {
        if (subElement instanceof Locatable &&
                !this.bd.isInBoundary(((Locatable)subElement).getLoc())) {
          subElement.suicide();
        }
      }
    }
  }

  @Override
  public void ctrlIn(Input input) {

  }

  @Override
  public void update() {

  }

  @Override
  public void render() {

  }

}
