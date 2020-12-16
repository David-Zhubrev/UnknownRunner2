package com.appdav.unknownrunner.gameobjects.characters

import android.content.res.Resources
import android.graphics.Rect
import com.appdav.unknownrunner.gameobjects.GravitableGameObject
import com.appdav.unknownrunner.gameobjects.characters.player.Player
import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.gameobjects.collision.CollidableContainer
import com.appdav.unknownrunner.gameobjects.level.Speed

abstract class Character(
    res: Resources,
    downScale: Float,
    speed: Speed
) : GravitableGameObject(res, downScale, speed), CollidableContainer {

    override var isInAir: Boolean = true

    protected open var isDead = false

    override val mass: Float = 100f

    abstract val player: Player

    override fun update() {
        super.update()
        mHitbox = null
        player.nextMove()
        y += vy
        x += vx
    }

    enum class Move {
        MOVE_LEFT,
        MOVE_RIGHT,
        SHIFT_LEFT,
        SHIFT_RIGHT,
        STOP,
        ATTACK,
        MOVE_UP,
        MOVE_DOWN,
        DIE,
        JUMP,
        DROP_DOWN,
        TURN_AROUND
    }

    abstract fun makeMove(move: Move)

//    open fun makeMove() {
//        x += vx
//        y += move.dy
//    }
//
//    open fun undoMove() {
//        x -= move.dx
//        y -= move.dy
//    }

    override fun undoUpdate() {
        x -= vx
        y -= vy
    }

    override fun getHitbox(): Rect? {
        if (isDead) return null
        return super.getHitbox()
    }


    override fun getCollidables(): List<Collidable> {
        if (isDead) return emptyList()
        return listOf(this)
    }
}