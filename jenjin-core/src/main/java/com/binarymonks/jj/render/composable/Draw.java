package com.binarymonks.jj.render.composable;

import com.badlogic.gdx.graphics.Color;
import com.binarymonks.jj.interfaces.Cloneable;

/**
 * Created by lwillmore on 30/01/17.
 */
public class Draw implements Cloneable<Draw> {

    public Color color = Color.YELLOW;
    public boolean fill = true;


    public Draw setColor(Color color) {
        this.color = color;
        return this;
    }

    public Draw setFill(boolean fill) {
        this.fill = fill;
        return this;
    }

    @Override
    public Draw clone() {
        return new Draw().setFill(fill).setColor(new Color(color));
    }
}
