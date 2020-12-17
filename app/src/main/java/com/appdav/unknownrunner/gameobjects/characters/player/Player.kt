package com.appdav.unknownrunner.gameobjects.characters.player

import com.appdav.unknownrunner.gameobjects.collision.CollisionSource

interface Player {

    fun nextMove()

    fun handleCollision(collisionSource: CollisionSource)

}