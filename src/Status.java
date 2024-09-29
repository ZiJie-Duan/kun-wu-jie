import java.util.Properties;

import dependencies.IOUtils;
import engine.EasyMap;

/* Please check my README file .*/
public class Status extends EasyMap {

  private static final Status instance = new Status();

  public Properties gameProps;
  public Properties messageProps;
  public String[][] gamePlayObjs;
  public String[][] gameScore;

  private void initSelf(Properties gameProps, Properties messageProps,
                    String[][] gamePlayObjs, String[][] gameScore){
    super.init();
    this.gameProps = gameProps;
    this.messageProps = messageProps;
    this.gamePlayObjs = gamePlayObjs;
    this.gameScore = gameScore;

    this.set("x_mid", (int) (Integer.parseInt(this.gameProps.getProperty("window.width")) / 2));
    this.set("y_mid", (int) (Integer.parseInt(this.gameProps.getProperty("window.height")) / 2));
    this.set("font", (String) this.gameProps.getProperty("font"));
    this.set("window_width", (int) Integer.parseInt(this.gameProps.getProperty("window.width")));
    this.set("window_height", (int) Integer.parseInt(this.gameProps.getProperty("window.height")));
  }

  private Status() {
    this.init();
  }

  @Override
  public void init(){
    this.initSelf(
            IOUtils.readPropertiesFile("res/app.properties"),
            IOUtils.readPropertiesFile("res/message_en.properties"),
            IOUtils
                    .readCommaSeparatedFile(
                            IOUtils.readPropertiesFile("res/app.properties")
                                    .getProperty("gamePlay.objectsFile")),
            IOUtils
                    .readCommaSeparatedFile(
                            IOUtils.readPropertiesFile("res/app.properties")
                                    .getProperty("gameEnd.scoresFile")));
  }

  public static Status getSt() {
    return instance;
  }
}
