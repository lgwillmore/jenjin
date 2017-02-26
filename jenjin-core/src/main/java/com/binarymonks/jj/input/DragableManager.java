package com.binarymonks.jj.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.components.Touchable;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Poolable;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.ThingNode;

public class DragableManager implements InputProcessor {

    OrthographicCamera camera;
    ObjectMap<Integer, Touch> touchTracker = new ObjectMap<>(10);
    Array<Integer> touchRemovals = new Array<>(10);


    Vector3 testPoint = N.ew(Vector3.class);
    Vector2 testPoint2 = N.ew(Vector2.class);
    Vector2 touchOffset = N.ew(Vector2.class);
    ObjectSet<Fixture> possibleBodies = new ObjectSet<>();
    Thing touchedThing = null;

    public DragableManager(OrthographicCamera camera) {
        this.camera = camera;
    }


    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        unproject(x, y);
        checkForDragableThings();
        if (touchedThing != null) {
            touchTracker.put(pointer, N.ew(Touch.class).set(touchedThing, touchOffset));
            touchedThing = null;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (touchTracker.containsKey(pointer)) {
            Touch t = touchTracker.remove(pointer);
            t.touchUp();
            Re.cycle(t);
        }
        return true;
    }

    public void update() {
        for (ObjectMap.Entry<Integer, Touch> touch : touchTracker) {
            if (touch.value.touchedThing.isMarkedForDestruction()) {
                touchRemovals.add(touch.key);
                continue;
            }
            unproject(Gdx.input.getX(touch.key), Gdx.input.getY(touch.key));
            touch.value.move(testPoint2);
        }
        for (Integer touch : touchRemovals) {
            Touch t = touchTracker.remove(touch);
            Re.cycle(t);
        }
        touchRemovals.clear();
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    protected void unproject(int x, int y) {
        camera.unproject(testPoint.set(x, y, 0));
        testPoint2.set(testPoint.x, testPoint.y);
    }

    private void checkForDragableThings() {
        Global.physics.world.QueryAABB(this::reportFixture, testPoint.x - 0.3f, testPoint.y - 0.3f, testPoint.x + 0.3f, testPoint.y + 0.3f);
        selectDragable();
    }

    private void selectDragable() {
        for (Fixture fixture : possibleBodies) {
            ThingNode node = (ThingNode) fixture.getUserData();
            if (node != null) {
                Thing parent = node.parent;
                if (!parent.isMarkedForDestruction()) {
                    Touchable t = parent.getComponent(Touchable.class);
                    if (t!=null) {
                        touchedThing = parent;
                        Vector2 touchLocation = N.ew(Vector2.class).set(testPoint.x,testPoint.y);
                        t.onTouchDown(touchLocation);
                        Re.cycle(touchLocation);
                        Body hitBody = fixture.getBody();
                        Vector2 bodyPosition = N.ew(Vector2.class).set(hitBody.getPosition());
                        if(t.trackTouchOffset()) {
                            touchOffset.set(bodyPosition.sub(testPoint.x, testPoint.y));
                        }
                        else{
                            touchOffset.set(0,0);
                        }
                        Re.cycle(bodyPosition);
                        break;
                    }
                }
            }
        }
        possibleBodies.clear();
    }


    public boolean reportFixture(Fixture fixture) {
        possibleBodies.add(fixture);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    public static class Touch implements Poolable {
        Thing touchedThing;
        Vector2 testPointCache = N.ew(Vector2.class);
        Vector2 touchOffset = N.ew(Vector2.class);

        public Touch set(Thing touchedThing, Vector2 offset) {
            this.touchedThing = touchedThing;
            this.touchOffset.set(offset);
            return this;
        }


        @Override
        public void reset() {
            testPointCache.set(0, 0);
            touchOffset.set(0, 0);
            touchedThing = null;
        }

        public void move(Vector2 newTouchLocation) {
            testPointCache.set(newTouchLocation);
            Vector2 newPosition = N.ew(Vector2.class).set(newTouchLocation).add(touchOffset);
            touchedThing.getComponent(Touchable.class).onTouchMove(newPosition);
            Re.cycle(newPosition);
        }

        public void touchUp() {
            touchedThing.getComponent(Touchable.class).onTouchUp();
        }
    }


}
