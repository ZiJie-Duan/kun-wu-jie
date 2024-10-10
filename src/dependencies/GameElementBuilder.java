package dependencies;

import components.car.OtherCar;
import components.car.Taxi;
import components.item.Coin;
import components.person.Driver;
import components.person.Passenger;
import engine.Element;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

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

    this.otherCarBuilder();
  }

  private void gameObjsBuilder(List<String> args) {

    if (args.get(0).equals("TAXI")) {
      this.taxiBuilder(args.get(1), args.get(2));

    } else if (args.get(0).equals("COIN")) {
      this.coinBuilder(args.get(1), args.get(2));

    } else if (args.get(0).equals("PASSENGER")) {
      this.passengerBuilder(args.get(1), args.get(2), args.get(3), args.get(4), args.get(5));
      
    } else if (args.get(0).equals("DRIVER")) {
      this.driverBuilder(args.get(1), args.get(2));
    }
  }

  private void otherCarBuilder() {
    // gnerate new taxi via random location
    Random random = new Random();

    if (((random.nextInt(1000) + 1) % 200) == 0) {
      int new_y = random.nextInt(2);
      if (new_y == 0) {
        new_y = -50;
      } else {
        new_y = 768;
      }

      int new_x;
      int new_lane = random.nextInt(3);
      if (new_lane == 0) {
        new_x = Integer.parseInt(st.gameProps.getProperty("roadLaneCenter1"));
      } else if (new_lane == 1) {
        new_x = Integer.parseInt(st.gameProps.getProperty("roadLaneCenter2"));
      } else {
        new_x = Integer.parseInt(st.gameProps.getProperty("roadLaneCenter3"));
      }

      this.trackedElement.deferAddSubElement(new OtherCar(new_x, new_y, random.nextInt(2) + 1));
    }
  }

  private void taxiBuilder(String x, String y) {
    Taxi taxi = new Taxi(
        Integer.parseInt(x),
        this.distance + Integer.parseInt(y));
    this.trackedElement.deferAddSubElement(taxi);
  }

  private void coinBuilder(String x, String y) {
    Coin coin = new Coin(
        Integer.parseInt(x),
        this.distance + Integer.parseInt(y));
    this.trackedElement.deferAddSubElement(coin);
  }

  private void driverBuilder(String x, String y) {
    Driver driver = new Driver(
        Integer.parseInt(x),
        this.distance + Integer.parseInt(y));
    this.trackedElement.deferAddSubElement(driver);
  }

  private void passengerBuilder(String x, String y, String piority, String endx, String highy) {
    Passenger passenger = new Passenger(
        Integer.parseInt(x),
        this.distance + Integer.parseInt(y),
        Integer.parseInt(piority),
        Integer.parseInt(endx),
        this.distance - Integer.parseInt(highy) + Integer.parseInt(y));

    this.trackedElement.deferAddSubElement(passenger);
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
