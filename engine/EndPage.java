import java.util.Properties;
import bagel.Input;
/* Please check my README file .*/
public class EndPage extends GameElement {
  private Status st;
  private FormatedImg bgImg;
  private int startY;
  private FormatedText[] scores = new FormatedText[5];
  private int scoreNum = 0;
  private FormatedText message1;
  private FormatedText messageWin;
  private FormatedText messageLost;

  private String[][] GetBiggestScore(String[][] gameScore) {
    // sort the score and get the biggest 5
    String[][] biggestScore = new String[5][2];
    for (int i = 0; i < 5; i++) {
      int biggest_index = -1;
      double biggest = -1;
      for (int j = 0; j < gameScore.length; j++) {
        if (Double.parseDouble(gameScore[j][1]) > biggest) {
          biggest = Double.parseDouble(gameScore[j][1]);
          biggest_index = j;
        }
      }
      if (biggest == -1) {
        break;
      }
      biggestScore[i][0] = gameScore[biggest_index][0];
      biggestScore[i][1] = gameScore[biggest_index][1];
      gameScore[biggest_index][1] = "-1";
      scoreNum++;
    }
    return biggestScore;
  }

  public EndPage() {
    super(false);
    st = Status.getSt();
    this.startY = Integer.parseInt(st.gameProps.getProperty("gameEnd.scores.y"));

    bgImg = new FormatedImg(st.gameProps.getProperty("backgroundImage.gameEnd"));

    String[][] biggestScore = GetBiggestScore(st.gameScore);

    for (int i = 0; i < scoreNum; i++) {
      scores[i] = new FormatedText(
          st.gameProps.getProperty("font"),
          Integer.parseInt(st.gameProps.getProperty("gameEnd.scores.fontSize")),
          biggestScore[i][0] + " - " + biggestScore[i][1]);
      scores[i].setY(startY + (i + 1) * 40);
      scores[i].autoSetXaxisMid();
    }

    message1 = new FormatedText(st.gameProps.getProperty("font"),
        Integer.parseInt(st.gameProps.getProperty("gameEnd.scores.fontSize")),
        st.messageProps.getProperty("gameEnd.highestScores"));
    message1.setY(startY);
    message1.autoSetXaxisMid();

    messageWin = new FormatedText(st.gameProps.getProperty("font"),
        Integer.parseInt(st.gameProps.getProperty("gameEnd.status.fontSize")),
        st.messageProps.getProperty("gameEnd.won"));
    messageWin.setY(Integer.parseInt(st.gameProps.getProperty("gameEnd.status.y")));
    messageWin.autoSetXaxisMid();

    messageLost = new FormatedText(st.gameProps.getProperty("font"),
        Integer.parseInt(st.gameProps.getProperty("gameEnd.status.fontSize")),
        st.messageProps.getProperty("gameEnd.lost"));
    messageLost.setY(Integer.parseInt(st.gameProps.getProperty("gameEnd.status.y")));
    messageLost.autoSetXaxisMid();
  }

  @Override
  public void spreadIn(EasyMap spread) {
  };

  @Override
  public void ctrlIn(Input input) {
  };

  @Override
  public void update(Input input) {
    bgImg.draw();
    for (int i = 0; i < scoreNum; i++) {
      scores[i].draw();
    }

    if (st.getDouble("FinalScore") > st.getDouble("TargetScore")) {
      messageWin.draw();
    } else {
      messageLost.draw();
    }
    message1.draw();
    super.update(input);
  }
}
