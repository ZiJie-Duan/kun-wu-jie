import java.text.Format;
import java.util.Properties;
import bagel.Input;
import bagel.Keys;
import bagel.Window;

import java.util.List;
import java.util.ArrayList;

public class GamePage extends GameElement {

  private final Status st;

  private final FormatedImg bgImg1;
  private final FormatedImg bgImg2;

  private final FormatedText textPay;
  private final FormatedText textTarget;
  private final FormatedText textFrame;
  private final FormatedText tripTitleText;
  private final FormatedText estimateFeeText;
  private final FormatedText piorityText;
  private final FormatedText lastTripTitleText;
  private final FormatedText lastTripPenaltyText;

  private final GameElementBuilder gameElementBuilder;

  public GamePage() {
    super(false);
    this.st = Status.getSt();
    this.gameElementBuilder = new GameElementBuilder(this);

    int gameInfoSize = Integer.parseInt(st.gameProps.getProperty("gameplay.info.fontSize"));
    String gameInfoFont = st.gameProps.getProperty("font");

    bgImg1 = new FormatedImg(st.gameProps.getProperty("backgroundImage"));
    bgImg2 = new FormatedImg(st.gameProps.getProperty("backgroundImage"));
    bgImg1.setLoc(512, 348);
    bgImg2.setLoc(512, -348);

    textPay = new FormatedText(gameInfoFont, gameInfoSize,
        st.messageProps.getProperty("gamePlay.earnings"));

    textTarget = new FormatedText(gameInfoFont, gameInfoSize,
        st.messageProps.getProperty("gamePlay.target"));

    textFrame = new FormatedText(gameInfoFont, gameInfoSize,
        st.messageProps.getProperty("gamePlay.remFrames"));

    tripTitleText = new FormatedText(gameInfoFont, gameInfoSize,
        st.messageProps.getProperty("gamePlay.onGoingTrip.title"));

    estimateFeeText = new FormatedText(gameInfoFont, gameInfoSize,
        st.messageProps.getProperty("gamePlay.trip.expectedEarning"));

    piorityText = new FormatedText(gameInfoFont, gameInfoSize,
        st.messageProps.getProperty("gamePlay.trip.priority"));

    lastTripTitleText = new FormatedText(gameInfoFont, gameInfoSize,
        st.messageProps.getProperty("gamePlay.completedTrip.title"));

    lastTripPenaltyText = new FormatedText(gameInfoFont, gameInfoSize,
        st.messageProps.getProperty("gamePlay.trip.penalty"));

    textPay.setLoc(
        Integer.parseInt(st.gameProps.getProperty("gameplay.earnings.x")),
        Integer.parseInt(st.gameProps.getProperty("gameplay.earnings.y")));

    textTarget.setLoc(
        Integer.parseInt(st.gameProps.getProperty("gameplay.target.x")),
        Integer.parseInt(st.gameProps.getProperty("gameplay.target.y")));

    textFrame.setLoc(
        Integer.parseInt(st.gameProps.getProperty("gameplay.maxFrames.x")),
        Integer.parseInt(st.gameProps.getProperty("gameplay.maxFrames.y")));

    tripTitleText.setLoc(
        Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.x")),
        Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.y")));

    estimateFeeText.setLoc(
        Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.x")),
        Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.y")) + 30);

    piorityText.setLoc(
        Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.x")),
        Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.y")) + 60);

    lastTripTitleText.setLoc(
        Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.x")),
        Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.y")));

    lastTripPenaltyText.setLoc(
        Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.x")),
        Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.y")) + 90);

    spread.set("taxiSpeed", Integer.parseInt(st.gameProps.getProperty("gameObjects.taxi.speedY")));
    spread.set("forwardDistance", 0);
    spread.set("maxFrames", Integer.parseInt(st.gameProps.getProperty("gamePlay.maxFrames")));
    spread.set("getPay", (Double) 0.0);
    spread.set("targetScore", Double.parseDouble(st.gameProps.getProperty("gamePlay.target")));
    spread.set("currentFee", (Double) 0.0);
    spread.set("currentPriority", 0);
    spread.set("currentPenalty", (Double) 0.0);
    spread.set("tripStatus", 0);
    spread.set("finishTrip", 0);

    // 0 never trip 1 on trip 2 not in trip
  }

  private void drawText() {
    textPay.drawWithExtraDouble(this.spread.getDouble("getPay"));
    textTarget.drawWithExtraDouble(this.spread.getDouble("targetScore"));
    textFrame.drawWithExtraInt(this.spread.getInt("maxFrames"));

    if (this.spread.getInt("tripStatus") == 1) {
      estimateFeeText.drawWithExtraDouble(this.spread.getDouble("currentFee"));
      piorityText.drawWithExtraInt(this.spread.getInt("currentPriority"));
      tripTitleText.draw();

    } else if (this.spread.getInt("tripStatus") == 2) {
      estimateFeeText.drawWithExtraDouble(this.spread.getDouble("currentFee"));
      piorityText.drawWithExtraInt(this.spread.getInt("currentPriority"));
      lastTripTitleText.draw();
      lastTripPenaltyText.drawWithExtraDouble(this.spread.getDouble("currentPenalty"));
    }
  }

  public void updateScore() {
    if (spread.getInt("finishTrip") == 1) {
      spread.accumulate("getPay", spread.getDouble("currentFee"));
      spread.set("finishTrip", 0);
    }
  }

  @Override
  public void spreadIn(EasyMap spread) {
  };

  @Override
  public void ctrlIn(Input input) {
    if (input.isDown(Keys.UP)) {
      int taxiSpeed = spread.getInt("taxiSpeed");
      spread.accumulate("forwardDistance", taxiSpeed);
      bgImg1.moveY(taxiSpeed);
      bgImg2.moveY(taxiSpeed);

      if (bgImg1.getY() > 1152 || bgImg2.getY() > 1152) {
        if (bgImg1.getY() > bgImg2.getY()) {
          bgImg1.setY(bgImg2.getY() - Window.getHeight());
        } else {
          bgImg2.setY(bgImg1.getY() - Window.getHeight());
        }
      }
    }
  }

  @Override
  public void update(Input input) {
    this.subElementsTriggerThemSelf();
    spread.accumulate("maxFrames", -1);
    if (spread.getInt("maxFrames") <= 0) {
      st.set("FinalScore", spread.getDouble("getPay"));
      st.set("TargetScore", spread.getDouble("targetScore"));
      st.accumulate("PageIndex", 1);
    }
    this.updateScore();
    this.gameElementBuilder.buildInRange(spread.getInt("forwardDistance"));
    bgImg1.draw();
    bgImg2.draw();
    this.drawText();
    super.update(input);
  }
}
