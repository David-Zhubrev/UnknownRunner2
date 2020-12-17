package com.appdav.unknownrunner.gameobjects.characters

import android.graphics.Rect
import com.appdav.unknownrunner.gameobjects.characters.concrete.MainCharacter
import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource

class CompanionHitbox(
    val source: Character,
    private val distance: Int,
    private val detectionCallback: (source: CollisionSource) -> Unit = {}
) : Collidable {

    private var mHitbox: Rect? = null

    override fun onCollision(collision: CollisionSource) {
        detectionCallback.invoke(collision)
    }

    fun update() {
        mHitbox = null
    }

    override fun getHitbox(): Rect? {
        if (mHitbox == null) {
            if (source is Enemy) {
                mHitbox = when (source.direction) {
                    Enemy.Direction.LEFT -> Rect(
                        (source.x - distance).toInt(),
                        source.y.toInt(),
                        source.x.toInt(),
                        (source.y + source.height * 1.5).toInt()
                    )
                    Enemy.Direction.RIGHT -> Rect(
                        (source.x + source.width).toInt(),
                        source.y.toInt(),
                        (source.x + source.width + distance).toInt(),
                        (source.y + source.height).toInt()
                    )
                }
            }
            if (source is MainCharacter) {
                mHitbox = Rect(
                    (source.x + source.width).toInt(),
                    source.y.toInt(),
                    (source.x + source.width + distance).toInt(),
                    (source.y + source.height * 2).toInt()
                )
            }
        }
        return mHitbox
    }
}