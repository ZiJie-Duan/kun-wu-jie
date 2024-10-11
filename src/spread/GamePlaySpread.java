package spread;

import components.person.Passenger;
import dependencies.Status;
import engine.Loc;
import engine.spread.Spread;

public class GamePlaySpread implements Spread {

  // game runtime dynamic values
  public int maxFrame = Integer.parseInt(Status.getSt().gameProps.getProperty("gamePlay.maxFrames"));
  public double playerScore = 0; // initial is 0
  public int runningFrame = 0;
  public int driveDistance = 0;

  public int levelUpFrame = 0;
  public boolean alreadyLevelUP = false;
  public int invinciblePowerFrame = 0;

  public int priority;
  public double estimateCost;

  public double taxiHealth;
  public double driverHealth;
  public double passengerHealth;

  public Loc driverLoc;

  // game runtime flags
  public boolean taxiMoveing = false;
  public boolean passengerInTrip = false;
  public Passenger passengerComming = null;
  public boolean driverInTaxi = false;
  public boolean isRaining = false;

  // game shared argument
  public double gameGlobalSpeed = 5;

  public boolean gameOver = false;

}
