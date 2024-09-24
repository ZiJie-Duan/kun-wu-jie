import bagel.Input;
import bagel.Window;
/* Please check my README file .*/
public abstract class GameElement extends Loc {
  private GameElement parentElement;
  private ObjLinkMap<GameElement> subElements = new ObjLinkMap<GameElement>();
  private ObjLinkMap<GameElement> dieList = new ObjLinkMap<GameElement>();
  protected EasyMap spread = new EasyMap();
  private boolean boundaryKill = false;

  public GameElement() {
    super();
  }

  public GameElement(boolean enableLocSystem) {
    super(enableLocSystem);
  }

  public void selfTriggerWithSubElements() {
    for (GameElement subElement : subElements.getObjList())
      TriggerController.triggerPair(this, subElement);
  }

  public void subElementsTriggerThemSelf() {
    for (int i = 0; i < subElements.size() - 1; i++) {
      for (int j = i + 1; j < subElements.size(); j++) {
        TriggerController.triggerPair(subElements.get(i), subElements.get(j));
      }
    }
  }

  public void triggerAll() {
    this.selfTriggerWithSubElements();
    this.subElementsTriggerThemSelf();
  }

  public void suicide() {
    this.parentElement.addDieList(this);
  }

  public void kill() {
    for (GameElement subElement : dieList.getObjList()) {
      subElements.remove(subElement);
    }
    dieList.clear();
  }

  public void addDieList(GameElement subElement) {
    this.dieList.add(subElement);
  }

  public void addParentElement(GameElement parentElement) {
    this.parentElement = parentElement;
  }

  public void delSubElement(GameElement subElement) {
    this.subElements.remove(subElement);
  }

  public void addSubElement(GameElement subElement) {
    subElement.addParentElement(this);
    this.subElements.add(subElement);
  }

  public void addSubElement(String key, GameElement subElement) {
    subElement.addParentElement(this);
    this.subElements.add(key, subElement);
  }

  public GameElement getSubElement(String key) {
    return (GameElement) this.subElements.get(key);
  }

  public GameElement getParentElement() {
    return (GameElement) this.parentElement;
  }

  public abstract void spreadIn(EasyMap spread);

  public abstract void ctrlIn(Input input);

  public void update(Input input) {
    if (boundaryKill == true) {
      boundaryKiller();
    }

    kill();

    for (GameElement subElement : subElements.getObjList()) {
      // spread not share cross two deep class
      subElement.spreadIn(this.spread);
    }

    for (GameElement subElement : subElements.getObjList()) {
      subElement.ctrlIn(input);
    }

    for (GameElement subElement : subElements.getObjList()) {

      subElement.update(input);
    }
  }

  public boolean isSubElement(GameElement subElement) {
    return this.subElements.isExist(subElement);
  }

  private void boundaryKiller() {
    for (GameElement subElement : subElements.getObjList()) {
      if (!this.inBoundary(subElement)) {
        addDieList(subElement);
      }
    }
  }

  public void setBoundaryKiller(boolean enable) {
    this.boundaryKill = enable;
  }
}
