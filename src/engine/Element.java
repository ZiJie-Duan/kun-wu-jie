package engine;

import engine.spread.Spread;
import java.util.List;
import bagel.Input;

/* Element public{
  this class is a Generic class that is used to create a game element
  SI means spread in
  SO means spread out
  parentElement's spread out (SO) is subElements's spread in (SI)
  subElements's spread in (SI) is parentElement's spread out (SO)
}*/
public abstract class Element<SI extends Spread, SO extends Spread> {
  private Class<SI> spreadInClass;
  private Class<SO> spreadOutClass;
  private Element<?, ?> parentElement;
  private ObjLinkMap<Element<?, ?>> subElements = new ObjLinkMap<Element<?, ?>>();
  private ObjLinkMap<Element<?, ?>> dieList = new ObjLinkMap<Element<?, ?>>();
  protected SI sI = null; // Spread In
  protected SO sO = null; // Spread Out

  // Constructor
  public Element(Class<SI> spreadInClass, Class<SO> spreadOutClass) {
    this.spreadInClass = spreadInClass;
    this.spreadOutClass = spreadOutClass;
  }

  public Class<SI> getSpreadInClass() {
    return this.spreadInClass;
  }

  public Class<SO> getSpreadOutClass() {
    return this.spreadOutClass;
  }

  public void suicide() {
    this.parentElement.addElementToDieList(this);
  }

  public void killSubElements() {
    for (Element<?, ?> subElement : dieList.getObjList()) {
      subElements.remove(subElement);
    }
    dieList.clear();
  }

  public void addElementToDieList(Element<?, ?> subElement) {
    this.dieList.add(subElement);
  }

  public void addParentElement(Element<?, ?> parentElement) {
    this.parentElement = parentElement;
  }

  public void addSubElement(Element<?, ?> subElement) {
    subElement.addParentElement(this);
    this.subElements.add(subElement);
  }

  public void addSubElement(String key, Element<?, ?> subElement) {
    subElement.addParentElement(this);
    this.subElements.add(key, subElement);
  }

  public Element<?, ?> getSubElement(String key) {
    return (Element<?, ?>) this.subElements.get(key);
  }

  public Element<?, ?> getParentElement() {
    return (Element<?, ?>) this.parentElement;
  }

  public List<Element<?, ?>> getSubElementList() {
    return this.subElements.getObjList();
  }

  public boolean isSubElement(Element<?, ?> subElement) {
    return this.subElements.isExist(subElement);
  }

  public void setSpreadIn(Spread spreadIn) {
    this.sI = (SI) spreadIn;
  }

  public Spread getSpreadOut() {
    return this.sO;
  }

  public void clearSpreadIn() {
    this.sI = null;
  }

  public abstract void ctrlIn(Input input);

  public abstract void update();

  public abstract void render();

}
