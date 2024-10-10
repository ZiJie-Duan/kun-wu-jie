package pages;

import bagel.Input;
import bagel.Keys;
import components.Road;
import dependencies.GameElementBuilder;
import dependencies.Status;
import engine.Boundary;
import engine.Loc;
import engine.Page;
import engine.trigger.pairTrigger.*;
import engine.IntelligentText;
import spread.GameMainSpread;
import spread.GamePlaySpread;

public class GamePlayPage
    extends Page<GameMainSpread, GamePlaySpread>
    implements TriggerSub {

  private final Status st = Status.getSt();
  private GameElementBuilder geBuilder;

  private final IntelligentText taxiHealthText;

  // private final FormatedText textPay;
  // private final FormatedText textTarget;
  // private final FormatedText textFrame;
  // private final FormatedText tripTitleText;
  // private final FormatedText estimateFeeText;
  // private final FormatedText piorityText;
  // private final FormatedText lastTripTitleText;
  // private final FormatedText lastTripPenaltyText;
  //
  // private final dependencies.GameElementBuilder gameElementBuilder;

  public GamePlayPage() {
    super(GameMainSpread.class, GamePlaySpread.class,
        new Boundary(
            new Loc(-20, -130),
            new Loc(1000, 900)));

    this.renderPriority = 1;
    this.sO = new GamePlaySpread();

    geBuilder = new GameElementBuilder(this);
    int gameInfoSize = Integer.parseInt(st.gameProps.getProperty("gamePlay.info.fontSize"));

    this.deferAddSubElement(
        "road",
        new Road());

    this.taxiHealthText = new IntelligentText(
        st.gameProps.getProperty("font"),
        gameInfoSize,
        st.messageProps.getProperty("gamePlay.taxiHealth"),
        new Loc(
            Integer.parseInt(st.gameProps.getProperty("gamePlay.taxiHealth.x")),
            Integer.parseInt(st.gameProps.getProperty("gamePlay.taxiHealth.y"))));

  }
  // this.addSubElement(
  // "components.car.Taxi",
  // new components.car.Taxi(150, 105));
  // this.addSubElement(
  // "fireball",
  // new components.item.Fireball(150, 500));

  // this.addSubElement(new components.effect.Blood(300,300));
  // this.addSubElement(new components.effect.Fire(350,350));
  // this.addSubElement(new components.effect.Smoke(400,400));

  // this.addSubElement(new components.person.Driver(200,300));

  // this.gameElementBuilder = new dependencies.GameElementBuilder(this);
  //
  // int gameInfoSize =
  // Integer.parseInt(st.gameProps.getProperty("gameplay.info.fontSize"));
  // String gameInfoFont = st.gameProps.getProperty("font");
  //
  // bgImg1 = new FormatedImg(st.gameProps.getProperty("backgroundImage"));
  // bgImg2 = new FormatedImg(st.gameProps.getProperty("backgroundImage"));
  // bgImg1.setLoc(512, 348);
  // bgImg2.setLoc(512, -348);
  //
  // textPay = new FormatedText(gameInfoFont, gameInfoSize,
  // st.messageProps.getProperty("gamePlay.earnings"));
  //
  // textTarget = new FormatedText(gameInfoFont, gameInfoSize,
  // st.messageProps.getProperty("gamePlay.target"));
  //
  // textFrame = new FormatedText(gameInfoFont, gameInfoSize,
  // st.messageProps.getProperty("gamePlay.remFrames"));
  //
  // tripTitleText = new FormatedText(gameInfoFont, gameInfoSize,
  // st.messageProps.getProperty("gamePlay.onGoingTrip.title"));
  //
  // estimateFeeText = new FormatedText(gameInfoFont, gameInfoSize,
  // st.messageProps.getProperty("gamePlay.trip.expectedEarning"));
  //
  // piorityText = new FormatedText(gameInfoFont, gameInfoSize,
  // st.messageProps.getProperty("gamePlay.trip.priority"));
  //
  // lastTripTitleText = new FormatedText(gameInfoFont, gameInfoSize,
  // st.messageProps.getProperty("gamePlay.completedTrip.title"));
  //
  // lastTripPenaltyText = new FormatedText(gameInfoFont, gameInfoSize,
  // st.messageProps.getProperty("gamePlay.trip.penalty"));
  //
  // textPay.setLoc(
  // Integer.parseInt(st.gameProps.getProperty("gameplay.earnings.x")),
  // Integer.parseInt(st.gameProps.getProperty("gameplay.earnings.y")));
  //
  // textTarget.setLoc(
  // Integer.parseInt(st.gameProps.getProperty("gameplay.target.x")),
  // Integer.parseInt(st.gameProps.getProperty("gameplay.target.y")));
  //
  // textFrame.setLoc(
  // Integer.parseInt(st.gameProps.getProperty("gameplay.maxFrames.x")),
  // Integer.parseInt(st.gameProps.getProperty("gameplay.maxFrames.y")));
  //
  // tripTitleText.setLoc(
  // Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.x")),
  // Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.y")));
  //
  // estimateFeeText.setLoc(
  // Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.x")),
  // Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.y")) + 30);
  //
  // piorityText.setLoc(
  // Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.x")),
  // Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.y")) + 60);
  //
  // lastTripTitleText.setLoc(
  // Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.x")),
  // Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.y")));
  //
  // lastTripPenaltyText.setLoc(
  // Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.x")),
  // Integer.parseInt(st.gameProps.getProperty("gameplay.tripInfo.y")) + 90);
  //
  // spread.set("taxiSpeed",
  // Integer.parseInt(st.gameProps.getProperty("gameObjects.taxi.speedY")));
  // spread.set("forwardDistance", 0);
  // spread.set("maxFrames",
  // Integer.parseInt(st.gameProps.getProperty("gamePlay.maxFrames")));
  // spread.set("getPay", (Double) 0.0);
  // spread.set("targetScore",
  // Double.parseDouble(st.gameProps.getProperty("gamePlay.target")));
  // spread.set("currentFee", (Double) 0.0);
  // spread.set("currentPriority", 0);
  // spread.set("currentPenalty", (Double) 0.0);
  // spread.set("tripStatus", 0);
  // spread.set("finishTrip", 0);

  // 0 never trip 1 on trip 2 not in trip
  // }

  // private void drawText() {
  // textPay.drawWithExtraDouble(this.spread.getDouble("getPay"));
  // textTarget.drawWithExtraDouble(this.spread.getDouble("targetScore"));
  // textFrame.drawWithExtraInt(this.spread.getInt("maxFrames"));
  //
  // if (this.spread.getInt("tripStatus") == 1) {
  // estimateFeeText.drawWithExtraDouble(this.spread.getDouble("currentFee"));
  // piorityText.drawWithExtraInt(this.spread.getInt("currentPriority"));
  // tripTitleText.draw();
  //
  // } else if (this.spread.getInt("tripStatus") == 2) {
  // estimateFeeText.drawWithExtraDouble(this.spread.getDouble("currentFee"));
  // piorityText.drawWithExtraInt(this.spread.getInt("currentPriority"));
  // lastTripTitleText.draw();
  // lastTripPenaltyText.drawWithExtraDouble(this.spread.getDouble("currentPenalty"));
  // }
  // }
  //
  // public void updateScore() {
  // if (spread.getInt("finishTrip") == 1) {
  // spread.accumulate("getPay", spread.getDouble("currentFee"));
  // spread.set("finishTrip", 0);
  // }
  // }

  @Override
  public void ctrlIn(Input input) {
    if (input.isDown(Keys.UP)) {
      sO.driveDistance += sO.gameGlobalSpeed;
    }
  }

  @Override
  public void update() {
    sO.runningFrame += 1;

    if (sO.runningFrame > sO.maxFrame) {
      this.sI.pageIndex += 1;
      this.sI.pageChange = true;
      suicide();
    }

    this.geBuilder.buildInRange(sO.driveDistance);
    if (this.geBuilder.isRainning(sO.runningFrame)) {
      this.sO.isRaining = true;
    } else {
      this.sO.isRaining = false;
    }

    // syn player score
    this.sI.playerScore = this.sO.playerScore;

    this.boundaryKiller();
  }

  @Override
  public void render() {
    this.taxiHealthText.drawWithDouble(this.sO.taxiHealth);
  }

  // public void updat2e() {
  // this.subElementsTriggerThemSelf();
  // spread.accumulate("maxFrames", -1);
  // if (spread.getInt("maxFrames") <= 0) {
  // st.set("FinalScore", spread.getDouble("getPay"));
  // st.set("TargetScore", spread.getDouble("targetScore"));
  // st.accumulate("PageIndex", 1);
  // }
  // this.updateScore();
  // this.gameElementBuilder.buildInRange(spread.getInt("forwardDistance"));
  // bgImg1.draw();
  // bgImg2.draw();
  // this.drawText();
  // }
}
