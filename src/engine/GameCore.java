package engine;

import bagel.*;

import java.lang.reflect.InvocationTargetException;

/**
 * GameEngine Core class
 * 
 * @author Zijie Duan
 */
public abstract class GameCore extends AbstractGame {

  private ElementController elementController;
  private boolean init = false;
  private Class<?> rootElementClass;

  public GameCore(int width, int height, String title, Class<?> rootElementClass) {
    super(width, height, title);
    this.rootElementClass = rootElementClass;
  }

  private void initGame() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Object instance = rootElementClass.getDeclaredConstructor().newInstance();
    this.elementController = new ElementController((Element<?, ?>) instance);
  }

  /**
   * Render the relevant screens and game objects based on the keyboard input
   * given by the user and the status of the game play.
   * 
   * @param input The current mouse/keyboard input.
   */
  @Override
  protected void update(Input input) {
    if (!init){
        try {
            this.initGame();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        this.init = true;
    } else {
      this.elementController.runOneFrame(input);
    }
  }
}
