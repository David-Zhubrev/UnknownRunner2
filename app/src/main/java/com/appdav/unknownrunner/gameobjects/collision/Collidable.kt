package com.appdav.unknownrunner.gameobjects.collision

import android.graphics.Rect

interface Collidable {

    fun onCollision(collision: CollisionSource)
    fun getHitbox(): Rect?

}