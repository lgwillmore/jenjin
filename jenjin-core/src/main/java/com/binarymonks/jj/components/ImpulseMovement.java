package com.binarymonks.jj.components;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.utils.LinearAlgebra;

/**
 * Think of this component as an engine and steering wheel.
 * It will apply linear impulses at a constant rate.
 */
public class ImpulseMovement extends Component {

    public static float FREQUENCY_HERTZ = 30;
    public float damping=10;
    public float maxForce =100;

    private Vector2 impulse = N.ew(Vector2.class);
    private boolean stop=false;

    private int impulseScheduleID;

    public void setImpulseVector(Direction direction) {
        stop=false;
        Vector2 currentLinearVelocity = parent.physicsroot.getB2DBody().getLinearVelocity();
        impulse.set(direction.directionVector);
        impulse.scl(direction.throttle* maxForce);
        Vector2 oppositeDirection = N.ew(Vector2.class).set(impulse).scl(-1);
        float badComponentOfVelocity = LinearAlgebra.componentOfAinB(currentLinearVelocity,oppositeDirection);
        if(badComponentOfVelocity>0){
            impulse.add(currentLinearVelocity.scl(-1));
        }
        Re.cycle(oppositeDirection,direction);
    }

    public void stop(){
        impulse.set(0,0);
        stop=true;
    }

    @Override
    public void doWork() {
        if(stop){
            Vector2 opposite = parent.physicsroot.getB2DBody().getLinearVelocity().scl(-1*damping);
            parent.physicsroot.getB2DBody().applyLinearImpulse(opposite, Vector2.Zero, true);
        }
    }

    @Override
    public void tearDown() {
        impulse.set(0,0);
        JJ.time.cancelScheduled(impulseScheduleID);
    }

    @Override
    public void getReady() {
        impulseScheduleID = JJ.time.scheduleInSeconds(this::applyImpulse, 1f / FREQUENCY_HERTZ, true);
    }

    private void applyImpulse() {
        parent.physicsroot.getB2DBody().applyLinearImpulse(impulse, Vector2.Zero, true);
    }

    @Override
    public Component clone() {
        ImpulseMovement clone = new ImpulseMovement();
        return clone;
    }

    public static class Direction {
        public Vector2 directionVector = N.ew(Vector2.class);
        public float throttle;

        private Direction() {
        }


        public Direction set(Vector2 normalisedDirectionVector, float throttle) {
            this.directionVector.set(normalisedDirectionVector).nor();
            this.throttle = throttle;
            return this;
        }

        public static Direction New() {
            return N.ew(Direction.class);
        }
    }

    public static class DirectionPoolManager implements PoolManager<Direction> {

        @Override
        public void reset(Direction direction) {
            direction.directionVector.set(0, 0);
            direction.throttle = 0;
        }

        @Override
        public Direction create_new() {
            return new Direction();
        }

        @Override
        public void dispose(Direction direction) {

        }
    }
}
