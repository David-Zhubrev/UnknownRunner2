package com.appdav.unknownrunner.gameobjects.platforms

import android.content.res.Resources
import android.graphics.Bitmap
import com.appdav.unknownrunner.R

class GroundTile(res: Resources, speed: Speed) : Platform(res, speed) {

    override var currentFrameManager: FrameManager? = FrameManager(bitmaps ?: emptyList())

    init {
        if (bitmaps == null) {
            bitmaps = listOf(
                createBitmap(R.drawable.ground_02, false)
            )
            currentFrameManager = FrameManager(
                bitmaps!!
            )
        }
    }

    companion object : BitmapContainer() {
        override var bitmaps: List<Bitmap>? = null
    }


}