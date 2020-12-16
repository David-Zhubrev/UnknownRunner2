package com.appdav.unknownrunner.gameobjects.characters.player

import android.util.Log
import com.appdav.unknownrunner.gameobjects.characters.Character
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource
import com.appdav.unknownrunner.tools.Constant.LOG_TAG

class HumanPlayer(character: Character) : PlayerBase(character), Controllable {

    override fun onSwipeLeft() {
        //TODO
        Log.d(LOG_TAG, "onSwipeLeft")
    }

    override fun onSwipeUp() {
        Log.d(LOG_TAG, "onSwipeUp")
        character.makeMove(Character.Move.JUMP)
    }

    override fun onLeftSideClick() {
        character.makeMove(Character.Move.STOP)
    }

    override fun onSwipeRight() {
        character.makeMove(Character.Move.ATTACK)
    }

    override fun onSwipeBottom() {
        if (character.isInAir) character.makeMove(Character.Move.DROP_DOWN)
    }

    override fun nextMove() {}
    override fun handleCollision(collisionSource: CollisionSource) {}

}