package components.person;

import bagel.Image;
import engine.Loc;
import engine.Sprite;
import engine.spread.SpreadNull;
import spread.GamePlaySpread;
import triggers.AttackBothTrigger;
import triggers.AttackTargetTrigger;

public abstract class Person extends Sprite<GamePlaySpread, SpreadNull> implements AttackTargetTrigger {

    public Person(double x, double y, String imgPath) {
        super(GamePlaySpread.class, SpreadNull.class);
        Loc loc = new Loc(x, y);
        Image img = new Image(imgPath);
        init(loc, null, img, false);
    }

}

