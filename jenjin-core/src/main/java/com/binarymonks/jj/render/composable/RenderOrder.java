package com.binarymonks.jj.render.composable;

import com.binarymonks.jj.interfaces.Cloneable;

/**
 * Created by lwillmore on 30/01/17.
 */
public class RenderOrder implements Cloneable<RenderOrder> {

    public int layer;
    public int thingPriority;


    public RenderOrder setLayer(int layer) {
        this.layer = layer;
        return this;
    }

    public RenderOrder setPriority(int thingPriority) {
        this.thingPriority = thingPriority;
        return this;
    }

    @Override
    public RenderOrder clone() {
        return new RenderOrder().setLayer(layer).setPriority(thingPriority);
    }
}
