package com.appdav.unknownrunner.gameobjects.characters

import android.graphics.Rect
import com.appdav.unknownrunner.gameobjects.characters.concrete.MainCharacter
import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource

class SpawnedHitbox(
    val source: Character,
    val distance: Int,
    val detectionCallback: (source: CollisionSource) -> Unit = {}
) : Collidable {

    var mHitbox: Rect? = null


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
                        (source.y + source.height).toInt()
                    )
                    Enemy.Direction.RIGHT -> Rect(
                        (source.x + source.width).toInt(),
                        source.y.toInt(),
                        (source.x + source.width + distance).toInt(),
                        (source.y + source.height).toInt()
                    )
                    else -> null //TODO
                }
            }
            if (source is MainCharacter) {
                mHitbox = Rect(
                    (source.x + source.width).toInt(),
                    source.y.toInt(),
                    (source.x + source.width + distance).toInt(),
                    (source.y + source.height).toInt()
                )
            }
        }
        return mHitbox
    }
}