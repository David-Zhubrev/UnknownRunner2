package com.appdav.unknownrunner.gameobjects.collision

import com.appdav.unknownrunner.gameobjects.level.Level

class CollidableListManager(val level: Level) {

    fun getCollidables(): List<Collidable> {
        return ArrayList<Collidable>().apply {
            addAll(level.tileManager.getCollidables())
            addAll(level.mainCharacter.getCollidables())
            addAll(level.enemyManager.getCollidables())
        }
    }
}