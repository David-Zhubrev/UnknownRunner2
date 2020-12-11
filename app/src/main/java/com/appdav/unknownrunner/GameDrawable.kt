package com.appdav.unknownrunner

import android.graphics.Canvas
import android.graphics.Paint

interface GameDrawable {

    fun draw(canvas: Canvas, paint: Paint)
    fun update()
    fun destroy()

    fun isDestroyed(): Boolean
}