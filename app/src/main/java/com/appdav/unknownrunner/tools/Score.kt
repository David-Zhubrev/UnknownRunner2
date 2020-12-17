package com.appdav.unknownrunner.tools

class Score(private val callback: () -> Unit) {

    init {
        callback.let{
            callbacks.add(it)
        }

    }

    companion object {
        val callbacks = ArrayList<()->Unit>()
        var gold = 0
            set(value) {
                field = value
                callbacks.forEach { it.invoke() }
            }
    }
}