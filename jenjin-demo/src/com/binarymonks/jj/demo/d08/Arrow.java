package com.binarymonks.jj.demo.d08;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.components.Component;

public class Arrow extends Component {
    private Bow notchedBow;

    @Override
    public Component clone() {
        return new Arrow();
    }

    @Override
    public void doWork() {

    }

    @Override
    public void tearDown() {
        notchedBow=null;
    }

    @Override
    public void getReady() {

    }

    public void updatePosition(Vector2 position){
        parent.physicsroot.getB2DBody().setAwake(true);
        if(isNotched()){
            parent.physicsroot.setPosition(notchedBow.restrictArrow(position));
        }
        else{
            parent.physicsroot.setPosition(position);
        }
    }

    public boolean isNotched() {
        return notchedBow!=null;
    }

    public void release() {
        if(isNotched()){
            notchedBow.release();
        }
        else{
            parent.markForDestruction();
        }

    }

    public void notchIn(Bow bow) {
        notchedBow=bow;
    }
}
