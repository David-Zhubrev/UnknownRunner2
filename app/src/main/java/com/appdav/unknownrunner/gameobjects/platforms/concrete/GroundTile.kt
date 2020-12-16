package com.appdav.unknownrunner.gameobjects.platforms.concrete

import android.content.res.Resources
import android.graphics.Bitmap
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.GroundTileBitmapContainer
import com.appdav.unknownrunner.gameobjects.platforms.Platform
import com.appdav.unknownrunner.gameobjects.level.Speed

class GroundTile(res: Resources, speed: Speed) : Platform(res, speed) {

    override var currentFrameManager: FrameManager? = FrameManager(bitmaps ?: emptyList())

    init {
        if (bitmaps == null) {
            bitmaps = listOf(
                GroundTileBitmapContainer.bitmap!!
            )
            currentFrameManager = FrameManager(
                bitmaps!!
            )
        }
    }

    companion object {
        var bitmaps: List<Bitmap>? = null
    }


}