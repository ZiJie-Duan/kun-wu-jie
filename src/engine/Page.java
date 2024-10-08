package engine;

import engine.spread.*;

public abstract class Page<SI extends Spread, SO extends Spread> extends Element<SI, SO> {

  private Boundary bd;

  public Page(Class<SI> sIClass, Class<SO> sOClass, Boundary bd) {
    super(sIClass, sOClass);
    this.bd = bd;
  }

  public Page(Class<SI> sIClass, Class<SO> sOClass) {
    super(sIClass, sOClass);
    this.bd = null;
  }

}
