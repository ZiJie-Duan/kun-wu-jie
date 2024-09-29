import bagel.*;

import engine.*;
import engine.spread.SpreadNull;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2024
 * Please enter your name below
 *
 * @author Zijie Duan
 */
public class ShadowTaxi extends GameCore {

    public ShadowTaxi(int width, int height, String title) {
        super(width, height, title, new RootPage());
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

// 子元素生成 加入到树中，由自己生成
// 子元素换父节点 炮弹发射 后 ？？？
// 触发器 的子类 包含由不同 角色的信息 来实现不同的激活逻辑