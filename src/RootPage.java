import bagel.Image;
import bagel.Input;
import engine.Page;
import engine.spread.SpreadNull;
import spread.PagesSpread;

class RootPage extends Page<SpreadNull, PagesSpread> {

    private Image BACKGROUND_IMAGE;
    private boolean init = false;
    private final Status st = Status.getSt();

    public RootPage() {
        super();
    }

    private void init() {
        BACKGROUND_IMAGE = new Image(st.gameProps.getProperty("backgroundImage.home"));
        this.sO = new PagesSpread();
    }

    private void buildPage(){
        if (this.sO.pageIndex == 1) {
            this.addSubElement("home_page", new HomePage());
        } else if (this.sO.pageIndex == 2){
            System.out.println("???");
            this.addSubElement("home_page2", new HomePage2());
        }
    }

    @Override
    public void ctrlIn(Input input) {
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