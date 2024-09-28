package engine;

import bagel.*;

/**
 * GameEngine Core class
 * 
 * @author Zijie Duan
 */
public abstract class GameCore extends AbstractGame {

  private final ElementController elementController;

  public GameCore(int width, int height, String title, Element<?, ?> rootElement) {
    super(width, height, title);
    this.elementController = new ElementController(rootElement);
  }

  /**
   * Render the relevant screens and game objects based on the keyboard input
   * given by the user and the status of the game play.
   * 
   * @param input The current mouse/keyboard input.
   */
  @Override
  protected void update(Input input) {
    this.elementController.runOneFrame(input);
  }

}
