package dependencies;

import java.util.Properties;

import engine.EasyMap;

/* Please check my README file .*/
public class Status extends EasyMap {

  private static final Status instance = new Status();

  public Properties gameProps;
  public Properties messageProps;
  public String[][] gamePlayObjs;
  public String[][] gameWeather;
  public String[][] gameScore;

  private void initSelf(Properties gameProps, Properties messageProps,
      String[][] gamePlayObjs, String[][] gamePlayWeather, String[][] gameScore) {
    super.init();
    this.gameProps = gameProps;
    this.messageProps = messageProps;
    this.gamePlayObjs = gamePlayObjs;
    this.gameWeather = gamePlayWeather;
    this.gameScore = gameScore;

    this.set("x_mid", (int) (Integer.parseInt(this.gameProps.getProperty("window.width")) / 2));
    this.set("y_mid", (int) (Integer.parseInt(this.gameProps.getProperty("window.height")) / 2));
    this.set("font", (String) this.gameProps.getProperty("font"));
    this.set("window_width", (int) Integer.parseInt(this.gameProps.getProperty("window.width")));
    this.set("window_height", (int) Integer.parseInt(this.gameProps.getProperty("window.height")));
    this.set("crush_invincible_time", (int) 200);
    this.set("freez_time", (int) 10);

  }

  private Status() {
    this.init();
  }

  @Override
  public void init() {
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
                                .getProperty("gamePlay.weatherFile")),
        IOUtils
            .readCommaSeparatedFile(
                IOUtils.readPropertiesFile("res/app.properties")
                    .getProperty("gameEnd.scoresFile")));

  }

  public static Status getSt() {
    return instance;
  }
}
