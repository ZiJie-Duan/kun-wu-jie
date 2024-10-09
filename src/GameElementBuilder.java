import engine.Element;

import java.util.List;
import java.util.ArrayList;

/* Please check my README file .*/
public class GameElementBuilder {

  private List<List<String>> gameObjsSource;
  private List<List<String>> gameWeather;
  private int distance;
  private Element<?,?> trackedElement;
  private Status st;

  public GameElementBuilder(Element<?,?> trackedElement) {
    this.st = Status.getSt();
    this.trackedElement = trackedElement;
    this.gameObjsSource = this.transferToList(st.gamePlayObjs);
    this.gameWeather = this.transferToList(st.gameWeather);
  }

  public boolean isRainning(int frame){
    while (true){
      if ((Integer.parseInt(this.gameWeather.get(0).get(1)) <= frame)
              && (Integer.parseInt(this.gameWeather.get(0).get(2)) >= frame)) {
        if (this.gameWeather.get(0).get(0).equals("SUNNY")) {
          return false;
        } else {
          return true;
        }
      }
      this.gameWeather.remove(0);
    }
  }

  public void buildInRange(int distance) {
    this.distance = distance;

    List<Integer> alreadyDraw = new ArrayList<Integer>();

    for (int i = 0; i < gameObjsSource.size(); i++) {
      if (distance + Integer.parseInt(gameObjsSource.get(i).get(2)) >= -120) {
        this.gameObjsBuilder(gameObjsSource.get(i));
        alreadyDraw.add(i);
      }
    }
    for (int j = alreadyDraw.size() - 1; j >= 0; j--) {
      this.gameObjsSource.remove((int) alreadyDraw.get(j));
    }
  }

  private void gameObjsBuilder(List<String> args) {

    if (args.get(0).equals("TAXI")) {
      this.taxiBuilder(args.get(1), args.get(2));

    } else if (args.get(0).equals("COIN")) {
      this.coinBuilder(args.get(1), args.get(2));

    } else if (args.get(0).equals("PASSENGER")) {
      this.passengerBuilder(args.get(1), args.get(2), args.get(3), args.get(4), args.get(5));
    }
  }

  private void taxiBuilder(String x, String y) {
    Taxi taxi = new Taxi(
        Integer.parseInt(x),
        this.distance + Integer.parseInt(y));
    this.trackedElement.addSubElement(taxi);
  }

  private void coinBuilder(String x, String y) {
    Coin coin = new Coin(
        Integer.parseInt(x),
        this.distance + Integer.parseInt(y));
    this.trackedElement.addSubElement(coin);
  }

  private void passengerBuilder(String x, String y, String piority, String endx, String highy) {
    Passenger passenger = new Passenger(
        Integer.parseInt(x),
        this.distance + Integer.parseInt(y),
        Integer.parseInt(piority),
        Integer.parseInt(endx),
        this.distance - Integer.parseInt(highy) + Integer.parseInt(y));

    this.trackedElement.addSubElement(passenger);
  }

  private List<List<String>> transferToList(String[][] stringArray) {
    List<List<String>> gameObjs = new ArrayList<List<String>>();

    for (int i = 0; i < stringArray.length; i++) {
      List<String> gameObj = new ArrayList<String>();

      for (int j = 0; j < stringArray[i].length; j++) {
        gameObj.add(stringArray[i][j]);
      }
      gameObjs.add(gameObj);
    }
    return gameObjs;
  }
}
