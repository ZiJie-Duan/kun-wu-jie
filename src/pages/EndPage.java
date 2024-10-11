package pages;

import bagel.Input;
import bagel.Keys;
import dependencies.IOUtils;
import dependencies.MiscUtils;
import dependencies.Status;
import engine.*;
import engine.spread.SpreadNull;
import spread.GameMainSpread;

import java.util.ArrayList;
import java.util.List;

import static dependencies.General.getMidXlocForScreenWithFixWide;

/* Please check my README file .*/
public class EndPage extends Page<GameMainSpread, SpreadNull> {
  private Status st;
  private int startY;
  private int scoreNum = 0;
  private FormatedText[] scores = new FormatedText[5];
  private FormatedText message;
  private FormatedText messageWin;
  private FormatedText messageLost;

  private boolean init = false;

  private void store(List<List<String>> gameScore){

    StringBuilder csvBuilder = new StringBuilder();
    for (List<String> row : gameScore) {
      csvBuilder.append(String.join(",", row));
      csvBuilder.append("\n");
    }

    String csvString = csvBuilder.toString();

    IOUtils.writeScoreToFile(
            IOUtils.readPropertiesFile("res/app.properties").getProperty("gameEnd.scoresFile"),
            csvString

    );
  }

  private List<List<String>> sortScore(String[][] gameScore) {
    // sort the score 
    List<List<String>> scoreList = new ArrayList<List<String>>();

    for (int i =0; i < gameScore.length; i ++){
      if (gameScore[i].length < 2){
        break;
      }
      List<String> sub = new ArrayList<>();
      sub.add(gameScore[i][0]);
      sub.add(gameScore[i][1]);
      scoreList.add(sub);
    }

    List<String> sub = new ArrayList<>();
    sub.add(this.sI.playerName);
    sub.add(String.format("%.2f", this.sI.playerScore));
    scoreList.add(sub);

    scoreList.sort((a, b) -> Double.compare(Double.parseDouble(b.get(1)), Double.parseDouble(a.get(1))));

    if (scoreList.size() >= 5){
      this.scoreNum = 5;
    } else {
      this.scoreNum = scoreList.size();
    }
    return scoreList;
  }

  public EndPage() {
    super(GameMainSpread.class, SpreadNull.class);

    st = Status.getSt();
    this.startY = Integer.parseInt(st.gameProps.getProperty("gameEnd.scores.y"));
  }

  private void initText(){
    List<List<String>> scoreList = sortScore(st.gameScore);

    for (int i = 0; i < scoreNum; i++) {
      scores[i] = new FormatedText(
              st.gameProps.getProperty("font"),
              Integer.parseInt(st.gameProps.getProperty("gameEnd.scores.fontSize")),
              scoreList.get(i).get(0) + " - " + scoreList.get(i).get(1));
      scores[i].addLoc(
              new Loc(
                      getMidXlocForScreenWithFixWide(
                              scores[i].getTextWidth(),
                              st.getInt("window_width")),
                      startY + (i + 1) * 40));
    }

    message = new FormatedText(st.gameProps.getProperty("font"),
            Integer.parseInt(st.gameProps.getProperty("gameEnd.scores.fontSize")),
            st.messageProps.getProperty("gameEnd.highestScores"));
    message.addLoc(
            new Loc(
                    getMidXlocForScreenWithFixWide(
                            message.getTextWidth(),
                            st.getInt("window_width")),
                    startY));

    messageWin = new FormatedText(st.gameProps.getProperty("font"),
            Integer.parseInt(st.gameProps.getProperty("gameEnd.status.fontSize")),
            st.messageProps.getProperty("gameEnd.won"));
    messageWin.addLoc(
            new Loc(
                    getMidXlocForScreenWithFixWide(
                            messageWin.getTextWidth(),
                            st.getInt("window_width")),
                    Integer.parseInt(st.gameProps.getProperty("gameEnd.status.y"))));

    messageLost = new FormatedText(st.gameProps.getProperty("font"),
            Integer.parseInt(st.gameProps.getProperty("gameEnd.status.fontSize")),
            st.messageProps.getProperty("gameEnd.lost"));
    messageLost.addLoc(
            new Loc(
                    getMidXlocForScreenWithFixWide(
                            messageLost.getTextWidth(),
                            st.getInt("window_width")),
                    Integer.parseInt(st.gameProps.getProperty("gameEnd.status.y"))));
    this.store(scoreList);
  }

  @Override
  public void ctrlIn(Input input) {
    if (input.wasPressed(Keys.SPACE)) {
      this.sI.pageIndex = 1;
      this.sI.pageChange = true;
      suicide();
    }
  }

  @Override
  public void update() {
    if (!init){
      this.initText();
      init = true;
    }
  }

  @Override
  public void render() {
    for (int i = 0; i < scoreNum; i++) {
      scores[i].draw();
    }

    if (sI.playerScore >= sI.targetScore) {
      messageWin.draw();
    } else {
      messageLost.draw();
    }
    message.draw();
  }

}
