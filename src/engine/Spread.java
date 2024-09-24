package engine;

/* core information share Interfase
 * to impliment Spread to allow share information
 * between different level of Elements.
 * */
public interface Spread {
  // inTarget input a Element Object
  // User need to impliment inTarget
  // manualy check it is the Element which you want to share info or not.
  // if not impliment it, just share with any type of SubElement
  default public boolean inTarget(Element<?, ?> obj) {
    return true;
  };
}
