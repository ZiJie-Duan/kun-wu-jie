/* Please check my README file .*/
public interface TriggerController {

  private static void disTrigger(Object obj1, Object obj2) {
    if (obj1 instanceof DisTrigger) {
      DisTrigger t1 = (DisTrigger) obj1;
      t1.disTrigger((Loc) obj2);
    }
    if (obj2 instanceof DisTrigger) {
      DisTrigger t2 = (DisTrigger) obj2;
      t2.disTrigger((Loc) obj1);
    }
  }

  public static void triggerPair(Object obj1, Object obj2) {
    TriggerController.disTrigger(obj1, obj2);
  }
}
