import dependencies.Status;
import engine.*;
import pages.RootPage;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2024
 * Please enter your name below
 *
 * @author Zijie Duan
 */
public class ShadowTaxi extends GameCore {

    // init game engine
    public ShadowTaxi(int width, int height, String title) {
        super(width, height, title, RootPage.class);
    }

    public static void main(String[] args) {
        Status st = Status.getSt();
        ShadowTaxi game = new ShadowTaxi(
                st.getInt("window_width"),
                st.getInt("window_height"),
                st.messageProps.getProperty("home.title")
        );
        game.run();
    }
}