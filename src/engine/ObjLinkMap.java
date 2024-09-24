
package engine;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/* Please check my README file .*/
public class ObjLinkMap<T> {
  private int counter;
  private Map<String, T> objMap;
  private Map<T, String> objInvMap;
  private List<T> objList;

  public ObjLinkMap() {
    counter = 0;
    objMap = new HashMap<String, T>();
    objInvMap = new HashMap<T, String>();
    objList = new ArrayList<T>();
  }

  public void add(T obj) {
    counter++;
    objMap.put(String.valueOf(counter), obj);
    objInvMap.put(obj, String.valueOf(counter));
    objList.add(obj);
  }

  public void add(String key, T obj) {
    counter++;
    objMap.put(key, obj);
    objInvMap.put(obj, key);
    objList.add(obj);
  }

  public Object get(String key) {
    return objMap.get(key);
  }

  public Object get(int index) {
    return objList.get(index);
  }

  public String getKey(T obj) {
    return objInvMap.get(obj);
  }

  public int getIndex(T obj) {
    return objList.indexOf(obj);
  }

  public void remove(String key) {
    Object obj = objMap.get(key);
    objMap.remove(key);
    objInvMap.remove(obj);
    objList.remove(obj);
  }

  public void remove(int index) {
    Object obj = objList.get(index);
    objList.remove(index);
    String key = objInvMap.get(obj);
    objInvMap.remove(obj);
    objMap.remove(key);
  }

  public void remove(T obj) {
    String key = objInvMap.get(obj);
    objList.remove(obj);
    objMap.remove(key);
    objInvMap.remove(obj);
  }

  public void clear() {
    objMap.clear();
    objList.clear();
    objInvMap.clear();
  }

  public boolean isExist(String key) {
    return objMap.containsKey(key);
  }

  public boolean isExist(T obj) {
    return objMap.containsValue(obj);
  }

  public List<T> getObjList() {
    return objList;
  }

  public int size() {
    return objList.size();
  }
}
