package com.appdav.unknownrunner.gameobjects.collectables

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.GravitableGameObject
import com.appdav.unknownrunner.gameobjects.level.Speed

abstract class Collectable(
    res: Resources,
    downScale: Float,
    speed: Speed
) : GravitableGameObject(res, downScale, speed) {

    protected var isTaken = false

    override fun update() {
        super.update()
        x = x - speed.value + vx
    }

}