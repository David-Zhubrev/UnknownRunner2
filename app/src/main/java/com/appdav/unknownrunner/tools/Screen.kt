package com.appdav.unknownrunner.tools

import android.graphics.Point
import kotlin.properties.Delegates

class Screen private constructor(private val point: Point) {

    companion object {

        //Test device screen size
        const val targetScreenHeight = 720f
        const val targetScreenWidth = 1560f

        var screenWidth by Delegates.notNull<Int>()
        var screenHeigth by Delegates.notNull<Int>()
        var ratioX by Delegates.notNull<Float>()
        var ratioY by Delegates.notNull<Float>()

        fun initialize(point: Point) {
            Screen(point).point.let {
                screenWidth = it.x
                screenHeigth = it.y
                ratioX = targetScreenWidth / screenWidth
                ratioY = targetScreenHeight / screenHeigth
            }
        }
    }
}