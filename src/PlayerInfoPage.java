import bagel.*;
import engine.Element;
import engine.spread.SpreadNull;
import spread.PagesSpread;
import engine.*;
import dependencies.MiscUtils;

/* Please check my README file .*/
public class PlayerInfoPage extends Element<PagesSpread, SpreadNull> {
    private FormatedText command1;
    private FormatedText command2;
    private FormatedText command3;
    private FormatedImg whiteBox;
    private FormatedText userName;
    private DrawOptions userNameOpt;
    private String userInput;

    public PlayerInfoPage() {
        Status st = Status.getSt();

        String lines[] = st.messageProps.getProperty("playerInfo.start").split("\n");
        int fontsize = Integer.parseInt(st.gameProps.getProperty("playerInfo.fontSize"));

        whiteBox = new FormatedImg(
                st.gameProps.getProperty("backgroundImage.playerInfo"),
                new Loc(
                        st.getInt("x_mid"),
                        Integer.parseInt(st.gameProps.getProperty("playerInfo.playerNameInput.y"))

                ));

        command1 = new FormatedText(
                st.gameProps.getProperty("font"),
                fontsize,
                st.messageProps.getProperty("playerInfo.playerName"));
        command1.addLoc(
                new Loc(
                        st.getInt("x_mid"),
                        Integer.parseInt(st.gameProps.getProperty("playerInfo.playerName.y"))
                )
        );

        command2 = new FormatedText(
                st.gameProps.getProperty("font"),
                fontsize,
                lines[0]);
        command2.addLoc(
                new Loc(
                        st.getInt("x_mid"),
                        Integer.parseInt(st.gameProps.getProperty("playerInfo.start.y"))
                )
        );

        command3 = new FormatedText(
                st.gameProps.getProperty("font"),
                fontsize,
                lines[1]);
        command3.addLoc(
                new Loc(
                        st.getInt("x_mid"),
                        Integer.parseInt(st.gameProps.getProperty("playerInfo.start.y") + fontsize)
                )
        );


        userNameOpt = new DrawOptions();
        userNameOpt.setBlendColour(0, 0, 0);

        userInput = "";

        userName = new IntelligentText(
                st.gameProps.getProperty("font"),
                fontsize,
                "",
                new Loc(
                        st.getInt("x_mid"),
                        Integer.parseInt(st.gameProps.getProperty("playerInfo.playerNameInput.y"))
                ),
                userNameOpt);
    }

    @Override
    public void ctrlIn(Input input) {
        if (input.wasPressed(Keys.BACKSPACE) || input.wasPressed(Keys.DELETE)) {
            if (!this.userInput.equals("")) {
                this.userInput = this.userInput.substring(0, this.userInput.length() - 1);
            }
        } else {
            String newstr = MiscUtils.getKeyPress(input);
            if (newstr != null) {
                this.userInput += newstr;
            }

        }
    }

    @Override
    public void update() {
    }


    @Override
    public void render() {
        whiteBox.draw();
        command1.draw();
        command2.draw();
        command3.draw();
        userName.autoXDraw(userInput, userNameOpt);
    }
}

