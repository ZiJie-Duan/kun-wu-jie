import bagel.Input;
import bagel.Keys;
import engine.*;
import engine.Element;
import engine.spread.SpreadNull;

import static dependencies.General.getMidXlocForScreenWithFixWide;

/* Please check my README file .*/
public class EndPage extends Element<GameMainSpread, SpreadNull> {
    private Status st;
    private int startY;
    private int scoreNum = 0;
    private FormatedText[] scores = new FormatedText[5];
    private FormatedText message;
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
        st = Status.getSt();
        this.startY = Integer.parseInt(st.gameProps.getProperty("gameEnd.scores.y"));

        String[][] biggestScore = GetBiggestScore(st.gameScore);

        for (int i = 0; i < scoreNum; i++) {
            scores[i] = new FormatedText(
                    st.gameProps.getProperty("font"),
                    Integer.parseInt(st.gameProps.getProperty("gameEnd.scores.fontSize")),
                    biggestScore[i][0] + " - " + biggestScore[i][1]);
            scores[i].addLoc(
                    new Loc(
                            getMidXlocForScreenWithFixWide(
                                    scores[i].getTextWidth(),
                                    st.getInt("window_width")
                            ),
                            startY + (i + 1) * 40
                    )
            );
        }

        message = new FormatedText(st.gameProps.getProperty("font"),
                Integer.parseInt(st.gameProps.getProperty("gameEnd.scores.fontSize")),
                st.messageProps.getProperty("gameEnd.highestScores"));
        message.addLoc(
                new Loc(
                        getMidXlocForScreenWithFixWide(
                                message.getTextWidth(),
                                st.getInt("window_width")
                        ),
                        startY
                )
        );

        messageWin = new FormatedText(st.gameProps.getProperty("font"),
                Integer.parseInt(st.gameProps.getProperty("gameEnd.status.fontSize")),
                st.messageProps.getProperty("gameEnd.won"));
        messageWin.addLoc(
                new Loc(
                        getMidXlocForScreenWithFixWide(
                                messageWin.getTextWidth(),
                                st.getInt("window_width")
                        ),
                        Integer.parseInt(st.gameProps.getProperty("gameEnd.status.y"))
                )
        );

        messageLost = new FormatedText(st.gameProps.getProperty("font"),
                Integer.parseInt(st.gameProps.getProperty("gameEnd.status.fontSize")),
                st.messageProps.getProperty("gameEnd.lost"));
        messageLost.addLoc(
                new Loc(
                        getMidXlocForScreenWithFixWide(
                                messageLost.getTextWidth(),
                                st.getInt("window_width")
                        ),
                        Integer.parseInt(st.gameProps.getProperty("gameEnd.status.y"))
                )
        );
    }

    @Override
    public void ctrlIn(Input input) {
        if (input.wasPressed(Keys.SPACE)){
            this.sI.pageIndex = 1;
            this.sI.pageChange = true;
            suicide();
        }
    }

    @Override
    public void update() {
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
