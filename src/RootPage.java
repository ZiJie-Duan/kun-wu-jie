import bagel.Image;
import bagel.Input;
import bagel.Keys;
import engine.Page;
import engine.spread.SpreadNull;

class RootPage extends Page<SpreadNull, GameMainSpread> {

  private Image BACKGROUND_IMAGE;
  private boolean init = false;
  private final Status st = Status.getSt();

  public RootPage() {
    super();
  }

  private void init() {
    BACKGROUND_IMAGE = new Image(st.gameProps.getProperty("backgroundImage.home"));
    this.sO = new GameMainSpread();
  }

  private void buildPage() {
    if (this.sO.pageIndex == 1) {
      this.sO = new GameMainSpread();
      this.addSubElement("home_page", new HomePage());
    } else if (this.sO.pageIndex == 2) {
      this.addSubElement("player_info_page", new PlayerInfoPage());
    } else if (this.sO.pageIndex == 3){
      this.addSubElement("game_play_page", new GamePlayPage());
    } else if (this.sO.pageIndex == 4){
      this.addSubElement("game_end_page", new EndPage());
    }
  }

  @Override
  public void ctrlIn(Input input) {
    if (input.wasPressed(Keys.ESCAPE)) {
      suicide();
    }
  }

  @Override
  public void update() {
    if (!init) {
      init = true;
      this.init();
    }

    if (this.sO.pageChange) {
      this.sO.pageChange = false;
      this.buildPage();
    }
  }

  @Override
  public void render() {
    BACKGROUND_IMAGE.draw(st.getInt("x_mid"), st.getInt("y_mid"));
  }
}
