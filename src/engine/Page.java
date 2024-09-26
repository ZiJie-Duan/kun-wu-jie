package engine;

public abstract class Page<SI, SO> extends Element<SI, SO> {

  private Boundary bd;

  public Page(Boundary bd) {
    this.bd = bd;
  }
}
