package com.binarymonks.jj.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.things.Thing;

public class CollisionResolver {
	
	protected Thing self;
	protected Array<CollisionFunction> initialBegins = new Array<CollisionFunction>();
	protected Array<CollisionFunction> finalBegins = new Array<CollisionFunction>();
	protected Array<CollisionFunction> endList = new Array<CollisionFunction>();
	protected int collisionCount = 0;
	

	public Thing getSelf() {
		return self;
	}

	public void setSelf(Thing self) {
		this.self = self;
	}

	public void initialBeginContact(Thing otherObject, Fixture otherFixture, Contact contact, Fixture myFixture){
		collisionCount++;
		for (CollisionFunction function : initialBegins) {
			function.performCollision(self, myFixture, otherObject, otherFixture, contact);
		}
	}

	public void finalBeginContact(Thing otherObject, Fixture otherFixture, Contact contact, Fixture myFixture){
		for (CollisionFunction function : finalBegins) {
			function.performCollision(self, myFixture, otherObject, otherFixture, contact);
		}
	}
	
	public void endContact(Thing otherObject, Fixture otherFixture, Contact contact, Fixture myFixture) {
		for (CollisionFunction function : endList) {
			function.performCollision(self, myFixture, otherObject, otherFixture, contact);
		}
	}

	public void addInitialBegin(CollisionFunction collision) {
		initialBegins.add(collision);
		collision.setResolver(this); 
	}

	public void addFinalBegin(CollisionFunction collision) {
		finalBegins.add(collision);
		collision.setResolver(this);
	}

	public Array<CollisionFunction> getInitialBegins() {
		return initialBegins;
	}

	public void setInitialBegins(Array<CollisionFunction> beginList) {
		this.initialBegins = beginList;
	}

	public Array<CollisionFunction> getFinalBegins() {
		return finalBegins;
	}

	public void setFinalBegins(Array<CollisionFunction> endList) {
		this.finalBegins = endList;
	}

	public int getCollisionCount() {
		return collisionCount;
	}

	

	public Array<CollisionFunction> getEndList() {
		return endList;
	}

	public void setEndList(Array<CollisionFunction> endList) {
		this.endList = endList;
	}
	
	public void addEnd(CollisionFunction collision) {
		endList.add(collision);
		collision.setResolver(this);
	}
	
	
	/**
	 * Will disable every {@link CollisionFunction}.
	 */
	public void disableCurrentCollisions(){
		for (CollisionFunction CollisionFunction : endList) {
			CollisionFunction.disable();
		}
		for (CollisionFunction CollisionFunction : finalBegins) {
			CollisionFunction.disable();
		}
		for (CollisionFunction CollisionFunction : initialBegins) {
			CollisionFunction.disable();
		}
	}
	
	
	/**
	 * Will enable every {@link CollisionFunction}.
	 */
	public void enableCurrentCollisions(){
		for (CollisionFunction CollisionFunction : endList) {
			CollisionFunction.enable();
		}
		for (CollisionFunction CollisionFunction : finalBegins) {
			CollisionFunction.enable();
		}
		for (CollisionFunction CollisionFunction : initialBegins) {
			CollisionFunction.enable();
		}
	}

	public void removeAllInitialBegin(Array<CollisionFunction> collisions) {
			initialBegins.removeAll(collisions, true);
	}
	
	
	
	

}
