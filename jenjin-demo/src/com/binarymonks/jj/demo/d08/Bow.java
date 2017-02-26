package com.binarymonks.jj.demo.d08;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Transform;
import com.binarymonks.jj.components.Component;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Re;

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
        Body arrowBody = notchedArrow.getParent().physicsroot.getB2DBody();
        arrowBody.setLinearVelocity(0,0);
        arrowBody.setAngularVelocity(0);
        Vector2 direction = parent.physicsroot.position().sub(notchedArrow.getParent().physicsroot.position());
        arrowBody.setLinearVelocity(direction.scl(10));
    }


    public void updateDraw(Vector2 position) {
        Vector2 direction = parent.physicsroot.position().sub(position);
        notchedArrow.getParent().physicsroot.setRotationR(direction.angleRad());
        notchedArrow.getParent().physicsroot.setPosition(position);
    }
}
