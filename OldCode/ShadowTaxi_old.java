import bagel.*;
import java.util.Properties;
/* Please check my README file .*/
/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2024
 * Please enter your name below
 * 
 * @author
 */
public class ShadowTaxi extends AbstractGame {

  private Status st;
  private Image BACKGROUND_IMAGE;
  private GameElement[] gamePage;

  public ShadowTaxi(Properties gameProps, Properties messageProps) {
    super(Integer.parseInt(gameProps.getProperty("window.width")),
        Integer.parseInt(gameProps.getProperty("window.height")),
        messageProps.getProperty("home.title"));

    st = Status.getSt();
    init();
  }

  private void init() {
    st.set("PageIndex", 0);

    BACKGROUND_IMAGE = new Image(st.gameProps.getProperty("backgroundImage.home"));

    gamePage = new GameElement[] {
        new HomePage(),
        new PlayerInfoPage(),
        new GamePage(),
        new EndPage()
    };

    // enable the boundary killer for the game page
    // auto recyle the game elements that are out of the boundary
    gamePage[2].setBoundary(0, -130, Window.getWidth(), Window.getHeight() + 10);
    gamePage[2].setBoundaryKiller(true);
  }

  /**
   * Render the relevant screens and game objects based on the keyboard input
   * given by the user and the status of the game play.
   * 
   * @param input The current mouse/keyboard input.
   */
  @Override
  protected void update(Input input) {

    if (input.wasPressed(Keys.ESCAPE)) {
      Window.close();
    }
    if (input.wasPressed(Keys.ENTER) && st.getInt("PageIndex") <= 1) {
      st.accumulate("PageIndex", 1);
    }
    if (input.wasPressed(Keys.SPACE) && st.getInt("PageIndex") == 3) {
      init();
    }

    // this is given as an example, you may move/delete this line as you wish
    BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

    this.gamePage[st.getInt("PageIndex")].ctrlIn(input);
    this.gamePage[st.getInt("PageIndex")].update(input);
  }

  public static void main(String[] args) {
    Status st = Status.getSt();
    ShadowTaxi game = new ShadowTaxi(st.gameProps, st.messageProps);
    game.run();
  }
}
