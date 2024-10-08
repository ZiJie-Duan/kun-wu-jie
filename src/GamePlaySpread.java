import engine.spread.Spread;

public class GamePlaySpread implements Spread {

  public boolean isRaining = false;
  public int taxiSpeed = 5;

  public boolean taxiMoveing = false;
  public boolean passengerInTaxi = false;
  public boolean passengerComming = false;

  public boolean driverInTaxi = true;

  public int taxiHealth;
}
