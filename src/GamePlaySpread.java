import engine.spread.Spread;

public class GamePlaySpread implements Spread {

  // game runtime dynamic values
  public int maxFrame = Integer.parseInt(Status.getSt().gameProps.getProperty("gamePlay.maxFrames"));
  public double playerScore = 0; // initial is 0
  public int runningFrame = 0;
  public int driveDistance = 0;

  private int priority;
  private double estimateCost;

  public int taxiHealth;
  public int driverHealth;
  public int passengerHealth;

  // game runtime flags
  public boolean taxiMoveing = false;
  public boolean passengerInTrip = false;
  public boolean passengerComming = false;
  public boolean driverInTaxi = false;
  public boolean isRaining = false;

  // game shared argument
  public int gameGlobalSpeed = 5;


}
