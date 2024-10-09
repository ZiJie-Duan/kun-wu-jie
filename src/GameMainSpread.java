import engine.GameCore;
import engine.spread.*;

public class GameMainSpread implements Spread {

    // for change different page in the Game
    public int pageIndex = 1; // one for the homePage
    public boolean pageChange = true; //init status is True
    // thus it will activate the build-page function

    // shared info in different pages
    public String playerName = "";
    public double targetScore = Double.parseDouble(Status.getSt().gameProps.getProperty("gamePlay.target"));
    public double playerScore = 0;

    public GameMainSpread(){

    }

    public GameMainSpread(boolean pageChange){
        this.pageChange = pageChange;
    }
}
