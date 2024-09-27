package engine;

public abstract class Page<SI extends Spread, SO extends Spread> extends Element<SI, SO> {

  private Boundary bd;

  public Page(Boundary bd) {
    this.bd = bd;
  }
}
