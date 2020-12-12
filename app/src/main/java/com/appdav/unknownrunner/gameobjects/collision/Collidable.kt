package com.appdav.unknownrunner.gameobjects.collision

import android.graphics.Rect

interface Collidable {

    fun onCollision(collision: Collision)
    fun getHitbox(): Rect?

}