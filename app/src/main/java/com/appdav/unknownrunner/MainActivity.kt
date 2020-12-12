package com.appdav.unknownrunner

import android.graphics.Point
import android.os.Bundle
import com.appdav.unknownrunner.tools.Screen

class MainActivity : FullscreenActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        Screen.initialize(point)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


}