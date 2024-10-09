import engine.spread.Spread;

public class GamePlaySpread implements Spread {

  // game runtime dynamic info
  public int maxFrame = Integer.parseInt(Status.getSt().gameProps.getProperty("gamePlay.maxFrames"));
  public double playerScore = 0; // initial is 0
  public int runningFrame = 0;
  public int driveDistance = 0;
  public int taxiHealth;

  // game runtime flags
  public boolean taxiMoveing = false;
  public boolean passengerInTaxi = false;
  public boolean passengerComming = false;
  public boolean driverInTaxi = true;
  public boolean isRaining = false;

  // game shared argument
  public int taxiSpeed = 5;
}
