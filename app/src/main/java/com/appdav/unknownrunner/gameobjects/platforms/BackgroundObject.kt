package com.appdav.unknownrunner.gameobjects.platforms

import android.content.res.Resources
import android.graphics.Rect
import com.appdav.unknownrunner.gameobjects.level.Speed

abstract class BackgroundObject(
    res: Resources,
    speed: Speed
) : Platform(res, speed) {

    override fun getHitbox(): Rect? {
        return null
    }
}