package com.binarymonks.jj.core.async

class UnitBond {
    private var callback: () -> Unit = { Unit }

    fun then(callback: () -> Unit) {
        this.callback = callback
    }

    fun succeed() {
        callback.invoke()
    }
}


class Bond<RESULT> {

    private var callback: (RESULT) -> Unit = { result: RESULT -> Unit }

    fun then(callback: (RESULT) -> Unit) {
        this.callback = callback
    }

    fun succeed(result: RESULT) {
        callback.invoke(result)
    }

}