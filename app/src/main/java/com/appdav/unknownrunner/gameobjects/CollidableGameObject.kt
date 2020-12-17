package com.appdav.unknownrunner.gameobjects

import android.content.res.Resources
import android.graphics.Rect
import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.gameobjects.level.Speed

abstract class CollidableGameObject(
    res: Resources,
    downScale: Float,
    speed: Speed
) : GameObject(res, downScale, speed), Collidable {

    protected open val hitboxThresholdLeft: Int = 0
    protected open val hitboxThresholdRight: Int = 0
    protected open val hitboxThresholdTop: Int = 0
    protected open val hitboxThresholdBottom: Int = 0
    protected var mHitbox: Rect? = null

    override fun update() {
        super.update()
        mHitbox = null
    }

    override fun getHitbox(): Rect? {
        mHitbox = mHitbox ?: Rect(
            (x + hitboxThresholdLeft).toInt(),
            (y + hitboxThresholdTop).toInt(),
            (x + width - hitboxThresholdRight).toInt(),
            (y + height - hitboxThresholdBottom).toInt()
        )
        return mHitbox
    }
}