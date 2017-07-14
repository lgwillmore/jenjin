package com.binarymonks.jj.core.input.mapping

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.utils.ObjectMap
import com.binarymonks.jj.core.api.InputAPI


class InputMapper : InputAPI, InputProcessor {

    internal var keyToActionToFunctionMap = ObjectMap<Int, ObjectMap<Actions.Key, GeneralHandler>>()
    internal var keyToHandlerMap = ObjectMap<Int, KeyHandler>()

    override fun map(keyCode: Int, keyAction: Actions.Key, function: GeneralHandler) {
        if (!keyToActionToFunctionMap.containsKey(keyCode)) {
            keyToActionToFunctionMap.put(keyCode, ObjectMap<Actions.Key, GeneralHandler>())
        }
        keyToActionToFunctionMap.get(keyCode).put(keyAction, function)
    }

    override fun map(keyCode: Int, keyHandler: KeyHandler) {
        keyToHandlerMap.put(keyCode, keyHandler)
    }


    override fun keyDown(keycode: Int): Boolean {
        return handleKey(keycode, Actions.Key.PRESSED)
    }

    private fun handleKey(keycode: Int, action: Actions.Key): Boolean {
        var handled = false
        if (keyToActionToFunctionMap.containsKey(keycode)) {
            val actionToFunction = keyToActionToFunctionMap.get(keycode)
            if (actionToFunction.containsKey(action)) {
                return actionToFunction.get(action)()
            }
        }
        if (!handled && keyToHandlerMap.containsKey(keycode)) {
            handled = keyToHandlerMap.get(keycode)(action)
        }
        return handled
    }

    override fun keyUp(keycode: Int): Boolean {
        return handleKey(keycode, Actions.Key.RELEASED)
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }
}
