package com.appdav.unknownrunner.gameobjects.platforms.concrete

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.GroundTileBitmapContainer
import com.appdav.unknownrunner.gameobjects.platforms.Platform
import com.appdav.unknownrunner.gameobjects.level.Speed

class GroundTile(res: Resources, speed: Speed) : Platform(res, speed) {

    override var currentFrameManager: FrameManager? = mng

    companion object {
        val mng = FrameManager(GroundTileBitmapContainer.regular!!)
    }

}