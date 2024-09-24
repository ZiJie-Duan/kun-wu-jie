package engine;

import java.util.HashMap;
import java.util.Map;

/* Please check my README file .*/
/*easy Map is a multy type Hash Map */
public class EasyMap {

  private Map<String, String> strMap;
  private Map<String, Integer> intMap;
  private Map<String, Double> doubleMap;

  public EasyMap() {
    strMap = new HashMap<String, String>();
    intMap = new HashMap<String, Integer>();
    doubleMap = new HashMap<String, Double>();
  }

  public void set(String key, String value) {
    strMap.put(key, value);
  }

  public void set(String key, int value) {
    intMap.put(key, value);
  }

  public void set(String key, double value) {
    doubleMap.put(key, value);
  }

  public String getStr(String key) {
    return strMap.get(key);
  }

  public int getInt(String key) {
    return intMap.get(key);
  }

  public double getDouble(String key) {
    return doubleMap.get(key);
  }

  public int accumulate(String key, int value) {
    intMap.put(key, intMap.get(key) + value);
    return intMap.get(key);
  }

  public double accumulate(String key, double value) {
    doubleMap.put(key, doubleMap.get(key) + value);
    return doubleMap.get(key);
  }

  public void init() {
    strMap.clear();
    intMap.clear();
    doubleMap.clear();
  }

  public boolean isExist(String key) {
    return intMap.containsKey(key);
  }
}
