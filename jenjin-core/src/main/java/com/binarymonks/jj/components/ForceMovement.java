package com.binarymonks.jj.components;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.utils.LinearAlgebra;

/**
 * Think of this component as an engine and steering wheel.
 * It will apply forces to the body at a constant rate.
 * <p>
 * The direction and magnitude of the force is controlled through the
 * parameters of the engine, as well as the throttle and direction of the {@link Steer}
 */
public class ForceMovement extends Component {

    public static float FREQUENCY_HERTZ = 30;

    public float breakForce = 10;
    public float moveForce = 20;

    private Vector2 currentForce = N.ew(Vector2.class);
    private boolean stop = false;

    private int impulseScheduleID;
    private boolean compensateForOppositeVelocity=true;

    public void setSteer(Steer steer) {
        stop = false;
        currentForce.set(steer.directionVector).scl(moveForce * steer.throttle);
        Re.cycle(steer);
    }

    public void stop() {
        currentForce.set(0, 0);
        stop = true;
    }

    @Override
    public void doWork() {
    }

    @Override
    public void tearDown() {
        currentForce.set(0, 0);
        JJ.time.cancelScheduled(impulseScheduleID);
    }

    @Override
    public void getReady() {
        impulseScheduleID = JJ.time.scheduleInSeconds(this::applyForce, 1f / FREQUENCY_HERTZ, true);
    }

    public ForceMovement setBreakForce(float breakForce) {
        this.breakForce = breakForce;
        return this;
    }

    public ForceMovement setMoveForce(float moveForce) {
        this.moveForce = moveForce;
        return this;
    }

    private void applyForce() {
        if (stop) {
            Vector2 opposite = parent.physicsroot.getB2DBody().getLinearVelocity().nor().scl(-1 * breakForce);
            parent.physicsroot.getB2DBody().applyForce(opposite, Vector2.Zero, true);
        } else {
            Vector2 actualForce = N.ew(Vector2.class).set(currentForce);
            if(compensateForOppositeVelocity){
                Vector2 currentVelocity = parent.physicsroot.getB2DBody().getLinearVelocity();
                float negativeComponent = LinearAlgebra.componentOfAinB(currentVelocity,actualForce);
                actualForce.setLength(actualForce.len()+Math.max(negativeComponent,0));
            }
            parent.physicsroot.getB2DBody().applyForce(actualForce, Vector2.Zero, true);
            Re.cycle(actualForce);
        }
    }

    @Override
    public Component clone() {
        ForceMovement clone = new ForceMovement();
        clone.moveForce = moveForce;
        clone.breakForce = breakForce;
        return clone;
    }

    public static class Steer {
        public Vector2 directionVector = N.ew(Vector2.class);
        public float throttle;

        private Steer() {
        }


        public Steer set(Vector2 directionVector, float throttle) {
            this.directionVector.set(directionVector).nor();
            this.throttle = throttle;
            return this;
        }

        public static Steer New() {
            return N.ew(Steer.class);
        }
    }

    public static class SteerPoolManager implements PoolManager<Steer> {

        @Override
        public void reset(Steer steer) {
            steer.directionVector.set(0, 0);
            steer.throttle = 0;
        }

        @Override
        public Steer create_new() {
            return new Steer();
        }

        @Override
        public void dispose(Steer steer) {

        }
    }
}
