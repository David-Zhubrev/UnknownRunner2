package com.appdav.unknownrunner.gameobjects.collision

import android.graphics.Rect
import com.appdav.unknownrunner.gameobjects.characters.Character

class CollisionHandler() {
    fun checkCollisions(collidables: List<Collidable>) {

        var isChar: Boolean
        outerLoop@ for (i in 0..collidables.lastIndex) {
            if (collidables[i] is Character){
                isChar = true // used for debug
            }
            for (j in i + 1..collidables.lastIndex) {
                if (collidables[j] is Character){
                    isChar = true //used for debug
                }
                val rect1 = collidables[i].getHitbox() ?: continue@outerLoop
                val rect2 = collidables[j].getHitbox() ?: continue
                if (Rect.intersects(rect1, rect2)) {
                    val intersection = Rect(rect1).apply { intersect(rect2) }
                    val sourceVerticalPosition: CollisionSource.SourcePosition
                    val sourceHorizontalPosition: CollisionSource.SourcePosition
                    sourceHorizontalPosition = when (intersection.left) {
                        rect1.left -> CollisionSource.SourcePosition.ON_LEFT
                        rect2.left -> CollisionSource.SourcePosition.ON_RIGHT
                        else -> {
                            if (intersection.left > rect1.left && intersection.right < rect1.right) {
                                CollisionSource.SourcePosition.ON_CENTER
                            } else return
                        }
                    }
                    sourceVerticalPosition = when (intersection.top) {
                        rect1.top -> CollisionSource.SourcePosition.ATOP
                        rect2.top -> CollisionSource.SourcePosition.ON_BOTTOM
                        else -> {
                            if (intersection.top > rect1.top &&
                                intersection.bottom < rect1.bottom
                            ) {
                                CollisionSource.SourcePosition.ON_CENTER
                            } else return
                        }
                    }
                    collidables[i].onCollision(
                        CollisionSource(
                            collidables[j],
                            sourceVerticalPosition,
                            sourceHorizontalPosition,
                            intersection.width().toFloat() / rect1.width().toFloat(),
                            intersection.height().toFloat() / rect1.height().toFloat()
                        )
                    )
                    collidables[j].onCollision(
                        CollisionSource(
                            collidables[i],
                            sourceVerticalPosition.opposite(),
                            sourceHorizontalPosition.opposite(),
                            intersection.width().toFloat() / rect2.width().toFloat(),
                            intersection.height().toFloat() / rect2.height().toFloat()
                        )
                    )
                }
            }
        }
    }


}