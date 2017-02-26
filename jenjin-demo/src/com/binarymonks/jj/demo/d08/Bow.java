package com.binarymonks.jj.demo.d08;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.components.Component;

public class Bow extends Component {

    Arrow notchedArrow;

    public void notchArrow(Arrow arrow){
        notchedArrow=arrow;
        notchedArrow.notchIn(this);
    }

    @Override
    public Component clone() {
        return new Bow();
    }

    @Override
    public void doWork() {

    }

    @Override
    public void tearDown() {
        notchedArrow=null;
    }

    @Override
    public void getReady() {

    }

    public void release(){
        Vector2 direction = parent.physicsroot.position().sub(notchedArrow.getParent().physicsroot.position());
        notchedArrow.getParent().physicsroot.getB2DBody().applyLinearImpulse(direction.scl(100),Vector2.Zero,true);
    }

    public Vector2 restrictArrow(Vector2 position) {
        return position;
    }
}
