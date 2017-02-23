package com.binarymonks.jj.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.api.JJInput;

import java.util.function.Supplier;

public class BaseInputProcessor implements JJInput, InputProcessor {

    ObjectMap<Integer, ObjectMap<Actions.Key, Supplier<Boolean>>> keyToActionToFunctionMap = new ObjectMap<>();
    ObjectMap<Integer, KeyHandler> keyToHandlerMap = new ObjectMap<>();

    @Override
    public void map(int keyCode, Actions.Key keyAction, Supplier<Boolean> function) {
        if (!keyToActionToFunctionMap.containsKey(keyCode)) {
            keyToActionToFunctionMap.put(keyCode, new ObjectMap<>());
        }
        keyToActionToFunctionMap.get(keyCode).put(keyAction, function);
    }

    @Override
    public void map(int keyCode, KeyHandler keyHandler) {
        keyToHandlerMap.put(keyCode,keyHandler);
    }


    @Override
    public boolean keyDown(int keycode) {
        return handleKey(keycode, Actions.Key.PRESSED);
    }

    private boolean handleKey(int keycode, Actions.Key action) {
        boolean handled = false;
        if (keyToActionToFunctionMap.containsKey(keycode)) {
            ObjectMap<Actions.Key, Supplier<Boolean>> actionToFunction = keyToActionToFunctionMap.get(keycode);
            if (actionToFunction.containsKey(action)) {
                actionToFunction.get(action).get();
                return true;
            }
        }
        if(!handled && keyToHandlerMap.containsKey(keycode)){
            handled=keyToHandlerMap.get(keycode).handle(action);
        }
        return handled;
    }

    @Override
    public boolean keyUp(int keycode) {
        return handleKey(keycode, Actions.Key.RELEASED);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
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
}
