package com.appdav.unknownrunner.gameobjects.characters.player

import com.appdav.unknownrunner.gameobjects.characters.Character
import com.appdav.unknownrunner.gameobjects.characters.Enemy
import com.appdav.unknownrunner.gameobjects.characters.concrete.MainCharacter
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource

class GolemSimpleAi(character: Enemy) : EnemyAi(character) {


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

    override fun onMainPlayerInSight(character: CollisionSource) {
        if (character.source is MainCharacter) {
            this.character.makeMove(Character.Move.ATTACK)
        }
    }

    override fun nextMove() {
        character as Enemy
        if (character.direction == Enemy.Direction.LEFT) character.makeMove(Character.Move.MOVE_LEFT)
        else if (character.direction == Enemy.Direction.RIGHT) character.makeMove(Character.Move.MOVE_RIGHT)
    }
}