package com.binarymonks.jj.physics;

/**
 * This is needed to get the collision groups of your game objects. This will determine what collides with what.
 * @author Laurence
 *
 */
public abstract class CollisionGroups {
	
	public abstract CollisionGroupData getGroupData(String group);
	
	public static class CollisionGroupData {
		public short category;
		public short mask;

		public CollisionGroupData(short category, short mask) {
			super();
			this.category = category;
			this.mask = mask;
		}
	}

}
