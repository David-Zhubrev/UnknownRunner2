package com.appdav.unknownrunner.gameobjects.platforms.concrete

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.GroundTileBitmapContainer
import com.appdav.unknownrunner.gameobjects.level.Speed
import com.appdav.unknownrunner.gameobjects.platforms.BackgroundObject

class Gap(res: Resources, speed: Speed) : BackgroundObject(res, speed) {

    override var currentFrameManager: FrameManager? =
        FrameManager(GroundTileBitmapContainer.regular!!)


    override fun draw(canvas: Canvas, paint: Paint) {}
}