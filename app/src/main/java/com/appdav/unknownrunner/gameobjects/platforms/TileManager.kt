package com.appdav.unknownrunner.gameobjects.platforms

import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.gameobjects.GameDrawable

interface TileManager : GameDrawable {
    fun getCollidables(): List<Collidable>
}