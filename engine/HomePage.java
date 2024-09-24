import bagel.*;
/* Please check my README file .*/
import java.security.DrbgParameters.Instantiation;
import java.util.Properties;

public class HomePage extends GameElement {
  FormatedText title;
  FormatedText instruction;

  public HomePage() {
    super(false);
    Status st = Status.getSt();

    title = new FormatedText(
        st.gameProps.getProperty("font"),
        Integer.parseInt(st.gameProps.getProperty("home.title.fontSize")),
        st.messageProps.getProperty("home.title"));

    title.setY(Integer.parseInt(st.gameProps.getProperty("home.title.y")));
    title.autoSetXaxisMid();

    instruction = new FormatedText(
        st.gameProps.getProperty("font"),
        Integer.parseInt(st.gameProps.getProperty("home.instruction.fontSize")),
        st.messageProps.getProperty("home.instruction"));

    instruction.setY(Integer.parseInt(st.gameProps.getProperty("home.instruction.y")));
    instruction.autoSetXaxisMid();
  }

  @Override
  public void spreadIn(EasyMap spread) {
  };

  @Override
  public void ctrlIn(Input input) {
  }

  @Override
  public void update(Input input) {
    Window.getWidth();
    this.title.draw();
    this.instruction.draw();
    super.update(input);
  }
}
