import bagel.*;
import engine.Element;
import engine.spread.SpreadNull;
import engine.*;
import dependencies.MiscUtils;
import dependencies.General;

/* Please check my README file .*/
public class PlayerInfoPage extends Page<GameMainSpread, SpreadNull> {
  private final FormatedText command1;
  private final FormatedText command2;
  private final FormatedText command3;
  private final FormatedImg whiteBox;
  private final IntelligentText userName;

  public PlayerInfoPage() {
    super(GameMainSpread.class, SpreadNull.class);
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
            General.getMidXlocForScreenWithFixWide(
                command1.getTextWidth(),
                st.getInt("window_width")),
            Integer.parseInt(st.gameProps.getProperty("playerInfo.playerName.y"))));

    command2 = new FormatedText(
        st.gameProps.getProperty("font"),
        fontsize,
        lines[0]);
    command2.addLoc(
        new Loc(
            General.getMidXlocForScreenWithFixWide(
                command2.getTextWidth(),
                st.getInt("window_width")),
            Integer.parseInt(st.gameProps.getProperty("playerInfo.start.y"))));

    command3 = new FormatedText(
        st.gameProps.getProperty("font"),
        fontsize,
        lines[1]);
    command3.addLoc(
        new Loc(
            General.getMidXlocForScreenWithFixWide(
                command3.getTextWidth(),
                st.getInt("window_width")),
            Integer.parseInt(st.gameProps.getProperty("playerInfo.start.y")) + fontsize));

    DrawOptions userNameOpt = new DrawOptions();
    userNameOpt.setBlendColour(0, 0, 0);

    userName = new IntelligentText(
        st.gameProps.getProperty("font"),
        fontsize,
        "",
        new Loc(
            0, // just any random number, If enable AutoX, it will not use this value anymore.
            Integer.parseInt(st.gameProps.getProperty("playerInfo.playerNameInput.y"))),
        userNameOpt);

    userName.enable_AutoX(st.getInt("window_width"));
  }

  @Override
  public void ctrlIn(Input input) {
    if (input.wasPressed(Keys.BACKSPACE) || input.wasPressed(Keys.DELETE)) {
      if (!this.sI.playerName.isEmpty()) {
        this.sI.playerName = this.sI.playerName.substring(0, this.sI.playerName.length() - 1);
      }
    } else {
      String newStr = MiscUtils.getKeyPress(input);
      if (newStr != null) {
        this.sI.playerName += newStr;
      }
    }

    if (input.wasPressed(Keys.ENTER)) {
      this.sI.pageIndex += 1;
      this.sI.pageChange = true;
      suicide();
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
    userName.drawWithString(sI.playerName);
  }
}
