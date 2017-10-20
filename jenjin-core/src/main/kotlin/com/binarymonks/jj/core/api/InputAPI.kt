package com.binarymonks.jj.core.api

import com.binarymonks.jj.core.input.mapping.Actions
import com.binarymonks.jj.core.input.mapping.GeneralHandler
import com.binarymonks.jj.core.input.mapping.KeyHandler


interface InputAPI {
    fun map(keyCode: Int, keyAction: Actions.Key, handler: GeneralHandler)
    fun map(keyCode: Int, keyHandler: KeyHandler)
    fun mapToAction(keyCode: Int, keyAction: Actions.Key, actionName: String)
    fun bindToAction(handler: GeneralHandler, actionName: String)
}