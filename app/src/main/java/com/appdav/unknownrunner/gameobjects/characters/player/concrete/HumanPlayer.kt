package com.appdav.unknownrunner.gameobjects.characters.player.concrete

import com.appdav.unknownrunner.gameobjects.characters.Character
import com.appdav.unknownrunner.gameobjects.characters.player.Controllable
import com.appdav.unknownrunner.gameobjects.characters.player.PlayerBase
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource

class HumanPlayer(character: Character) : PlayerBase(character), Controllable {

    override fun onSwipeLeft() {}

    override fun onSwipeUp() {}

    override fun onLeftSideClick() {
        character.makeMove(Character.Move.JUMP)
    }

    override fun onSwipeRight() {}

    override fun onRightSideClick() {
        character.makeMove(Character.Move.ATTACK)
    }

    override fun onSwipeBottom() {
        if (character.isInAir) character.makeMove(Character.Move.DROP_DOWN)
    }

    override fun nextMove() {}
    override fun handleCollision(collisionSource: CollisionSource) {}

}