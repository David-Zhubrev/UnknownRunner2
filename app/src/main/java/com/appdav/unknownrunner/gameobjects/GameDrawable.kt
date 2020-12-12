package com.appdav.unknownrunner.gameobjects

import android.graphics.Canvas
import android.graphics.Paint

interface GameDrawable {

    fun draw(canvas: Canvas, paint: Paint)
    fun update()
    fun undoUpdate()
    fun destroy()

    fun isDestroyed(): Boolean
}