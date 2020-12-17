package com.appdav.unknownrunner.gameobjects

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource
import com.appdav.unknownrunner.gameobjects.platforms.Platform
import com.appdav.unknownrunner.gameobjects.level.Speed

abstract class GravitableGameObject(
    res: Resources,
    downScale: Float,
    speed: Speed
) : CollidableGameObject(res, downScale, speed), Gravitable {

    protected var vx: Float = 0f
    protected var vy: Float = 0f
    abstract var isInAir: Boolean
    var lastFrameTimeStamp: Long = System.currentTimeMillis()

    private var shouldFall = false

    override fun onDraw() {
        val currentTimeStamp = System.currentTimeMillis()
        val realTime = (currentTimeStamp - lastFrameTimeStamp) / 1_000f
        if (!shouldFall && vy >= 0) isInAir = false
        lastFrameTimeStamp = currentTimeStamp
        if (!shouldFall) {
            vy = 0f
            return
        } else {
            vy += 50 * (realTime)
//            y += vy * realTime
//            val dy = vy * realTime
//            fall(15f)
        }
    }

    override fun update() {
        super.update()
        shouldFall = true
    }

    override fun onCollision(collision: CollisionSource) {
        if (collision.source is Platform) {
            if (collision.verticalPosition == CollisionSource.SourcePosition.ON_BOTTOM) {
                if (collision.verticalImpact > 0.15 && vy >= 0) {
                    shouldFall = false
                    if (y + height - hitboxThresholdBottom > collision.source.y) {
                        y = collision.source.y - height + hitboxThresholdBottom
                    }
                }
//            } else if (collision.verticalPosition == CollisionSource.SourcePosition.ATOP) {
//                if (collision.verticalImpact > 0.10) {
//                    vy = 0f
//                    if (y < collision.source.y + collision.source.height) {
//                        y = collision.source.y + collision.source.height
//                    }
//                }
            }
        }
    }

}