package engine;

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
  private Element<?, SI> parentElement;
  private ObjLinkMap<Element<SO, ?>> subElements = new ObjLinkMap<Element<SO, ?>>();
  private ObjLinkMap<Element<SO, ?>> dieList = new ObjLinkMap<Element<SO, ?>>();
  private SI spreadIn = null;
  private SO spreadOut = null;

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
    for (Element<SO, ?> subElement : dieList.getObjList()) {
      subElements.remove(subElement);
    }
    dieList.clear();
  }

  public void addElementToDieList(Element<SO, ?> subElement) {
    this.dieList.add(subElement);
  }

  public void addParentElement(Element<?, SI> parentElement) {
    this.parentElement = parentElement;
  }

  public void addSubElement(Element<SO, ?> subElement) {
    subElement.addParentElement(this);
    this.subElements.add(subElement);
  }

  public void addSubElement(String key, Element<SO, ?> subElement) {
    subElement.addParentElement(this);
    this.subElements.add(key, subElement);
  }

  public Element<SO, ?> getSubElement(String key) {
    return (Element<SO, ?>) this.subElements.get(key);
  }

  public Element<?, SI> getParentElement() {
    return (Element<?, SI>) this.parentElement;
  }

  public List<Element<SO, ?>> getSubElementList() {
    return this.subElements.getObjList();
  }

  public boolean isSubElement(Element<SO, ?> subElement) {
    return this.subElements.isExist(subElement);
  }

  public void setSpreadIn(Spread spreadIn) {
    this.spreadIn = (SI) spreadIn;
  }

  public Spread getSpreadOut() {
    return this.spreadOut;
  }

  public abstract void ctrlIn(Input input);

  public abstract void update();

  public abstract void render();

}
