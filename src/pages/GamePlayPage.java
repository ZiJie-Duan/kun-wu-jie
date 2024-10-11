package pages;

import bagel.Input;
import bagel.Keys;
import components.Road;
import components.car.Taxi;
import dependencies.GameElementBuilder;
import dependencies.Status;
import engine.*;
import engine.trigger.pairTrigger.*;
import spread.GameMainSpread;
import spread.GamePlaySpread;

public class GamePlayPage
    extends Page<GameMainSpread, GamePlaySpread>
    implements TriggerSub {

  private final Status st = Status.getSt();
  private GameElementBuilder geBuilder;

  private final IntelligentText taxiHealthText;
  private final IntelligentText driverHealthText;
  private final IntelligentText passengerHealthText;

  private final IntelligentText textPay;
  private final IntelligentText textTarget;
  private final IntelligentText textFrame;

   private final IntelligentText tripTitleText;
   private final IntelligentText estimateFeeText;
   private final IntelligentText piorityText;
   private final IntelligentText lastTripTitleText;
   private final IntelligentText lastTripPenaltyText;
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

    this.driverHealthText = new IntelligentText(
            st.gameProps.getProperty("font"),
            gameInfoSize,
            st.messageProps.getProperty("gamePlay.driverHealth"),
            new Loc(
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.driverHealth.x")),
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.driverHealth.y"))));

    this.passengerHealthText = new IntelligentText(
      st.gameProps.getProperty("font"),
      gameInfoSize,
      st.messageProps.getProperty("gamePlay.passengerHealth"),
      new Loc(
          Integer.parseInt(st.gameProps.getProperty("gamePlay.passengerHealth.x")),
          Integer.parseInt(st.gameProps.getProperty("gamePlay.passengerHealth.y"))));

    this.textTarget = new IntelligentText(
        st.gameProps.getProperty("font"),
        gameInfoSize,
        st.messageProps.getProperty("gamePlay.target"),
        new Loc(
                Integer.parseInt(st.gameProps.getProperty("gamePlay.target.x")),
                Integer.parseInt(st.gameProps.getProperty("gamePlay.target.y"))));

    this.textPay = new IntelligentText(
            st.gameProps.getProperty("font"),
            gameInfoSize,
            st.messageProps.getProperty("gamePlay.earnings"),
            new Loc(
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.earnings.x")),
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.earnings.y"))));

    this.textFrame = new IntelligentText(
            st.gameProps.getProperty("font"),
            gameInfoSize,
            st.messageProps.getProperty("gamePlay.remFrames"),
            new Loc(
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.maxFrames.x")),
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.maxFrames.y"))));


    this.tripTitleText = new IntelligentText(
            st.gameProps.getProperty("font"),
            gameInfoSize,
            st.messageProps.getProperty("gamePlay.onGoingTrip.title"),
            new Loc(
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.tripInfo.x")),
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.tripInfo.y"))));
    this.estimateFeeText = new IntelligentText(
            st.gameProps.getProperty("font"),
            gameInfoSize,
            st.messageProps.getProperty("gamePlay.trip.expectedEarning"),
            new Loc(
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.tripInfo.x")),
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.tripInfo.y"))+ 30));
    this.piorityText = new IntelligentText(
            st.gameProps.getProperty("font"),
            gameInfoSize,
            st.messageProps.getProperty("gamePlay.trip.priority"),
            new Loc(
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.tripInfo.x")),
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.tripInfo.y"))+ 60));
    this.lastTripTitleText = new IntelligentText(
            st.gameProps.getProperty("font"),
            gameInfoSize,
            st.messageProps.getProperty("gamePlay.completedTrip.title"),
            new Loc(
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.tripInfo.x")),
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.tripInfo.y"))));
    this.lastTripPenaltyText = new IntelligentText(
            st.gameProps.getProperty("font"),
            gameInfoSize,
            st.messageProps.getProperty("gamePlay.trip.penalty"),
            new Loc(
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.tripInfo.x")),
                    Integer.parseInt(st.gameProps.getProperty("gamePlay.tripInfo.y"))+ 90));

  }

  @Override
  public void ctrlIn(Input input) {
    if (input.isDown(Keys.UP)) {
      sO.driveDistance += sO.gameGlobalSpeed;
    }
  }

  @Override
  public void update() {

    sO.runningFrame += 1;

    if ((this.sO.gameOver)||(sO.runningFrame > sO.maxFrame)) {
      this.sI.pageIndex += 1;
      this.sI.pageChange = true;
      suicide();
    }

    if (sO.runningFrame > 1){
      boolean taxiInGame = false;
      for (Element<?,?> element : this.getSubElementList()){
        if (element instanceof Taxi) {
          taxiInGame = true;
        }
      }
      if (!taxiInGame){
        this.sI.pageIndex += 1;
        this.sI.pageChange = true;
        suicide();
      }
    }


    this.geBuilder.buildInRange(sO.driveDistance);
    if (this.geBuilder.isRainning(sO.runningFrame)) {
      this.sO.isRaining = true;
    } else {
      this.sO.isRaining = false;
    }

    // syn player score to Page Level
    this.sI.playerScore = this.sO.playerScore;

    this.boundaryKiller();
  }

  @Override
  public void render() {
    this.taxiHealthText.drawWithDouble(this.sO.taxiHealth);
    this.driverHealthText.drawWithDouble(this.sO.driverHealth);
    this.passengerHealthText.drawWithDouble(this.sO.passengerHealth);
    this.textPay.drawWithDouble(this.sO.playerScore);
    this.textTarget.drawWithDouble(Double.parseDouble(st.gameProps.getProperty("gamePlay.target")));
    this.textFrame.drawWithInt(this.sO.maxFrame - this.sO.runningFrame);

    if (this.sO.passengerInTrip){
      this.tripTitleText.draw();
    } else {
      this.lastTripTitleText.draw();
    }
    this.estimateFeeText.drawWithDouble(this.sO.estimateCost);
    this.piorityText.drawWithInt(this.sO.priority);
    //private final IntelligentText lastTripPenaltyText;
  }
}
