package com.appdav.unknownrunner.gameobjects.platforms

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.CollidableGameObject
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource
import com.appdav.unknownrunner.gameobjects.level.Speed

abstract class Platform(res: Resources, speed: Speed) :
    CollidableGameObject(res, downScaleConst, speed) {

    companion object{
        val downScaleConst = 3f
    }

    override fun update() {
        super.update()
        x -= speed.value
        mHitbox = null
    }

    override fun undoUpdate() {
        x += speed.value
    }

    override fun onCollision(collision: CollisionSource) {}

    override fun onDraw() {}
}