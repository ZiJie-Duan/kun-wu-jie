import java.util.Properties;
import engine.EasyMap;

/* Please check my README file .*/
public class Status extends EasyMap {

  private static final Status instance = new Status(
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

  public Properties gameProps;
  public Properties messageProps;
  public String[][] gamePlayObjs;
  public String[][] gameScore;

  private Status(Properties gameProps, Properties messageProps,
      String[][] gamePlayObjs, String[][] gameScore) {
    this.gameProps = gameProps;
    this.messageProps = messageProps;
    this.gamePlayObjs = gamePlayObjs;
    this.gameScore = gameScore;

  }

  public static Status getSt() {
    return instance;
  }

  @Override
  public void init() {
    super.init();
  }
}
