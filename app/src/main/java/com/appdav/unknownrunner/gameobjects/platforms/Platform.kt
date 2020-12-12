package com.appdav.unknownrunner.gameobjects.platforms

import android.content.res.Resources
import android.graphics.Rect
import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.gameobjects.collision.Collision
import com.appdav.unknownrunner.gameobjects.GameObject

abstract class Platform(res: Resources, speed: Speed) :
    GameObject(res, 4f, speed),
    Collidable {

    private var mHitbox: Rect? = null

    override fun update() {
        x -= speed.value
        mHitbox = null
    }

    override fun undoUpdate() {
        x += speed.value
    }

    override fun getHitbox(): Rect? {
        mHitbox = mHitbox ?: Rect(
            x.toInt(), y.toInt(),
            (x + width).toInt(), (y + height).toInt()
        )
        return mHitbox
    }

    override fun onCollision(collision: Collision) {}
}