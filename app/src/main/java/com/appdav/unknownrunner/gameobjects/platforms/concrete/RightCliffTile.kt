package com.appdav.unknownrunner.gameobjects.platforms.concrete

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.GroundTileBitmapContainer
import com.appdav.unknownrunner.gameobjects.level.Speed
import com.appdav.unknownrunner.gameobjects.platforms.BackgroundObject

class RightCliffTile(res: Resources, speed: Speed) : BackgroundObject(res, speed) {
    override var currentFrameManager: FrameManager? = mng

    companion object {
        val mng = FrameManager(GroundTileBitmapContainer.rightCliff!!)
    }
}