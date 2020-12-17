package com.appdav.unknownrunner.gameobjects.characters

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource
import com.appdav.unknownrunner.gameobjects.level.Speed

abstract class Enemy(
    res: Resources,
    downScale: Float,
    speed: Speed
) : Character(res, downScale, speed) {

    open var direction: Direction = Direction.LEFT

    abstract var visionSight: CompanionHitbox?

    enum class Direction {
        LEFT,
        RIGHT,
    }

    override fun update() {
        super.update()
        visionSight?.update()
        x = x - speed.value + vx

    }

    override fun onCollision(collision: CollisionSource) {
        super.onCollision(collision)
        player.handleCollision(collision)
    }

    abstract fun turnAround()
    abstract fun die()
    abstract fun attack()
    abstract fun onEnemyDetected(source: CollisionSource)
}