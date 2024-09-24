import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
/* Please check my README file .*/
public class GameElementBuilder {

  private List<List<String>> gameObjsSource;
  private int distance;
  private GameElement selfElement;
  private Status st;

  public GameElementBuilder(GameElement selfElement) {
    this.st = Status.getSt();
    this.selfElement = selfElement;
    this.gameObjsSource = this.transferGameObjsSource(st.gamePlayObjs);
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
    Taxi taxi = new Taxi(st.gameProps.getProperty("gameObjects.taxi.image"),
        Integer.parseInt(x),
        this.distance + Integer.parseInt(y));
    this.selfElement.addSubElement(taxi);
  }

  private void coinBuilder(String x, String y) {
    Coin coin = new Coin(st.gameProps.getProperty("gameObjects.coin.image"),
        Integer.parseInt(x),
        this.distance + Integer.parseInt(y));
    this.selfElement.addSubElement(coin);
  }

  private void passengerBuilder(String x, String y, String piority, String endx, String highy) {
    Passenger passenger = new Passenger(
        st.gameProps.getProperty("gameObjects.passenger.image"),
        Integer.parseInt(x),
        this.distance + Integer.parseInt(y),
        Integer.parseInt(piority),
        Integer.parseInt(endx),
        this.distance - Integer.parseInt(highy) + Integer.parseInt(y));

    this.selfElement.addSubElement(passenger);
  }

  private List<List<String>> transferGameObjsSource(String[][] gameObjsSource) {
    List<List<String>> gameObjs = new ArrayList<List<String>>();

    for (int i = 0; i < gameObjsSource.length; i++) {
      List<String> gameObj = new ArrayList<String>();

      for (int j = 0; j < gameObjsSource[i].length; j++) {
        gameObj.add(gameObjsSource[i][j]);
      }
      gameObjs.add(gameObj);
    }
    return gameObjs;
  }
}
