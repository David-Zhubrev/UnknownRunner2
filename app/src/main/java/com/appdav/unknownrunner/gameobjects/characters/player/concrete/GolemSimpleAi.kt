package com.appdav.unknownrunner.gameobjects.characters.player.concrete

import com.appdav.unknownrunner.gameobjects.characters.Character
import com.appdav.unknownrunner.gameobjects.characters.Enemy
import com.appdav.unknownrunner.gameobjects.characters.concrete.MainCharacter
import com.appdav.unknownrunner.gameobjects.characters.player.EnemyAi
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource
import com.appdav.unknownrunner.gameobjects.platforms.Platform

class GolemSimpleAi(character: Enemy) : EnemyAi(character) {

    private var isGapAhead = true

    override fun handleCollision(collisionSource: CollisionSource) {
        character as Enemy
        if (collisionSource.horizontalPosition == CollisionSource.SourcePosition.ON_LEFT
            && collisionSource.horizontalImpact > 0.5 && character.direction == Enemy.Direction.LEFT
        ) {
            character.makeMove(Character.Move.TURN_AROUND)
        }
        if (collisionSource.verticalPosition == CollisionSource.SourcePosition.ATOP &&
            collisionSource.source is MainCharacter
        ) {
            character.makeMove(Character.Move.DIE)
        }
    }

    override fun onObjectInSight(source: CollisionSource) {
        if (source.source is MainCharacter) {
            this.character as Enemy
            character.onEnemyDetected(source)
        }
        if (source.source is Platform && source.verticalImpact > 0.2) {
            isGapAhead = false
        }
    }

    override fun nextMove() {
        character as Enemy
        if (isGapAhead) character.turnAround()
        isGapAhead = true
        if (character.direction == Enemy.Direction.LEFT) character.makeMove(Character.Move.MOVE_LEFT)
        else if (character.direction == Enemy.Direction.RIGHT) character.makeMove(Character.Move.MOVE_RIGHT)
    }
}