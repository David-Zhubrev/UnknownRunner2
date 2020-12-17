package com.appdav.unknownrunner.gameobjects.characters.concrete

import com.appdav.unknownrunner.gameobjects.characters.Character
import com.appdav.unknownrunner.gameobjects.characters.player.EnemyAi
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource

class Dummy(character: Character) : EnemyAi(character) {
    override fun onObjectInSight(source: CollisionSource) {}

    override fun nextMove() {}

    override fun handleCollision(collisionSource: CollisionSource) {}
}