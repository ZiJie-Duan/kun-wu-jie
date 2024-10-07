import engine.GameCore;
import engine.spread.*;

public class GameMainSpread implements Spread {

    public int pageIndex = 1; // one for the homePage
    public boolean pageChange = true; //init status is True
    // thus it will activate the build-page function

    public String playerName = "";
    public int driveDistance = 0;

    public double playerScore = 0; // initial is 0
    public double targetScore = Double.parseDouble(Status.getSt().gameProps.getProperty("gamePlay.target")); // just for test
    public int maxFrame = Integer.parseInt(Status.getSt().gameProps.getProperty("gamePlay.maxFrames"));
    public int runningFrame = 0;

    public GameMainSpread(){

    }

    public GameMainSpread(boolean pageChange){
        this.pageChange = pageChange;
    }
}
