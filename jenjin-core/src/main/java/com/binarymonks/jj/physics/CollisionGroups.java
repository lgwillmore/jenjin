package com.binarymonks.jj.physics;

import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;

/**
 * This is needed to check Collision groups of your game objects. This will determine what collides with what.
 */
public abstract class CollisionGroups {

    public static CollisionData EVERYTHING = CollisionData.New(Short.MAX_VALUE, Short.MAX_VALUE);
    public static CollisionData NOTHING = CollisionData.New((short) 0, (short) 0);

    public CollisionData getCollisionData(CollisionDataSpec collisionDataSpec) {
        if (collisionDataSpec.name != null) {
            return getGroupFromName(collisionDataSpec.name);
        }
        return N.ew(CollisionData.class).set(collisionDataSpec.category, collisionDataSpec.mask);
    }

    abstract CollisionData getGroupFromName(String group);

    public static class CollisionData{
        public short category;
        public short mask;

        CollisionData() {
        }

        public CollisionData set(short category, short mask) {
            this.category = category;
            this.mask = mask;
            return this;
        }

        public static CollisionData New(short category, short mask) {
            return N.ew(CollisionData.class).set(category, mask);
        }
    }

    public static class CollisionDataPoolManager implements PoolManager<CollisionData>{

        @Override
        public void reset(CollisionData collisionData) {
            collisionData.mask=0;
            collisionData.category=0;
        }

        @Override
        public CollisionData create_new() {
            return new CollisionData();
        }

        @Override
        public void dispose(CollisionData collisionData) {

        }
    }

}
