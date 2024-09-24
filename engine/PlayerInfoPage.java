import bagel.*;
import java.util.Properties;
/* Please check my README file .*/
public class PlayerInfoPage extends GameElement {
  private FormatedText command1;
  private FormatedText command2;
  private FormatedText command3;
  private FormatedImg whiteBox;
  private FormatedText userName;
  private DrawOptions userNameOpt;
  private String userInput;

  public PlayerInfoPage() {
    super(false);
    Status st = Status.getSt();

    String lines[] = st.messageProps.getProperty("playerInfo.start").split("\n");
    int fontsize = Integer.parseInt(st.gameProps.getProperty("playerInfo.fontSize"));

    whiteBox = new FormatedImg(
        st.gameProps.getProperty("backgroundImage.playerInfo"));
    whiteBox.setY(Integer.parseInt(st.gameProps.getProperty("playerInfo.playerNameInput.y")));
    whiteBox.autoSetXaxisMid();

    command1 = new FormatedText(
        st.gameProps.getProperty("font"),
        fontsize,
        st.messageProps.getProperty("playerInfo.playerName"));
    command1.setY(Integer.parseInt(st.gameProps.getProperty("playerInfo.playerName.y")));
    command1.autoSetXaxisMid();

    command2 = new FormatedText(
        st.gameProps.getProperty("font"),
        fontsize,
        lines[0]);
    command2.setY(Integer.parseInt(st.gameProps.getProperty("playerInfo.start.y")));
    command2.autoSetXaxisMid();

    command3 = new FormatedText(
        st.gameProps.getProperty("font"),
        fontsize,
        lines[1]);
    command3.setY(Integer.parseInt(st.gameProps.getProperty("playerInfo.start.y")) + fontsize);
    command3.autoSetXaxisMid();

    userName = new FormatedText(
        st.gameProps.getProperty("font"),
        fontsize);
    userName.setY(Integer.parseInt(st.gameProps.getProperty("playerInfo.playerNameInput.y")));

    userNameOpt = new DrawOptions();
    userNameOpt.setBlendColour(0, 0, 0);
    userInput = "";
  }

  @Override
  public void spreadIn(EasyMap spread) {
  };

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
  public void update(Input input) {
    Window.getWidth();
    whiteBox.draw();
    command1.draw();
    command2.draw();
    command3.draw();
    userName.autoXDraw(userInput, userNameOpt);
    super.update(input);
  }
}
