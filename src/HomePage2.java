import bagel.Input;
import bagel.Keys;
import dependencies.General;
import engine.FormatedText;
import engine.Loc;
import engine.Page;
import engine.spread.SpreadNull;
import spread.PagesSpread;

public class HomePage2 extends Page<PagesSpread, SpreadNull> {

    private final FormatedText title;
    private final FormatedText instruction;
    private final Status st = Status.getSt();

    public HomePage2() {
        super();
        title = new FormatedText(
                st.getStr("font"),
                Integer.parseInt(st.gameProps.getProperty("home.title.fontSize")),
                "test2");

        title.addLoc(
                new Loc(
                        General.getMidXlocForScreenWithFixWide(
                                title.getTextWidth(),
                                st.getInt("window_width")),
                        Integer.parseInt(
                                st.gameProps.getProperty("home.title.y")
                        )
                )
        );

        instruction = new FormatedText(
                st.gameProps.getProperty("font"),
                Integer.parseInt(st.gameProps.getProperty("home.instruction.fontSize")),
                st.messageProps.getProperty("home.instruction"));

        instruction.addLoc(
                new Loc(
                        General.getMidXlocForScreenWithFixWide(
                                instruction.getTextWidth(),
                                st.getInt("window_width")),
                        Integer.parseInt(
                                st.gameProps.getProperty("home.instruction.y")
                        )
                )
        );

    }

    @Override
    public void ctrlIn(Input input) {
        if (input.wasPressed(Keys.ENTER)){
            this.sI.pageChange = true;
            this.sI.pageIndex += 1;
            this.suicide();
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        System.out.println("????");
        this.title.draw();
        this.instruction.draw();
    }
}
