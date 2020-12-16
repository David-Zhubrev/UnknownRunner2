package com.appdav.unknownrunner.gameobjects.characters.concrete

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.FallenAngel3BitmapContainer
import com.appdav.unknownrunner.gameobjects.characters.Character
import com.appdav.unknownrunner.gameobjects.characters.Enemy
import com.appdav.unknownrunner.gameobjects.characters.SpawnedHitbox
import com.appdav.unknownrunner.gameobjects.characters.player.Player
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource
import com.appdav.unknownrunner.gameobjects.level.Speed

class MainCharacter(
    res: Resources,
    speed: Speed
//    runningBitmaps: List<Bitmap>,
//    dyingBitmaps: List<Bitmap>,
//    attackBitmaps: List<Bitmap>,
//    airborneBitmaps: List<Bitmap>
) : Character(res, downScaleConst, speed) {

    override var mDestroyed = false

    var isAttacking = false

    var jumpCounter = 0

    override var isInAir: Boolean
        get() = super.isInAir
        set(value) {
            if (!value) {
                jumpCounter = 0
                currentFrameManager = runFrameManager
            }
        }

    val runFrameManager = FrameManager(FallenAngel3BitmapContainer.runningBitmaps!!)
    val dieFrameManager = FrameManager(FallenAngel3BitmapContainer.runningBitmaps!!, ::gameOver)
    val attackFrameManager = FrameManager(FallenAngel3BitmapContainer.runningBitmaps!!) {
        isAttacking = false
        spawnedHitbox = null
        currentFrameManager = runFrameManager
    }
    val airborneFrameManager = FrameManager(FallenAngel3BitmapContainer.airborneBitmaps!!)
    val jumpStartFrameManager = FrameManager(FallenAngel3BitmapContainer.jumpStartBitmaps!!) {
        currentFrameManager = airborneFrameManager
    }
    override var currentFrameManager: FrameManager? = runFrameManager
        set(value) {
            field = value
        }
    override lateinit var player: Player

    var spawnedHitbox: SpawnedHitbox? = null

    override val hitboxThresholdLeft: Int = FallenAngel3BitmapContainer.HITBOX_MARGIN_LEFT
    override val hitboxThresholdRight: Int = FallenAngel3BitmapContainer.HITBOX_MARGIN_RIGHT
    override val hitboxThresholdTop: Int = FallenAngel3BitmapContainer.HITBOX_MARGIN_TOP
    override val hitboxThresholdBottom: Int = FallenAngel3BitmapContainer.HITBOX_MARGIN_BOTTOM

    init {
        x = width * 2f
        y = 0f
    }


    companion object {
        const val downScaleConst = 10f
        const val BASIC_ATTACK_DISTANCE = 15
        const val BASIC_JUMP_SPEED = 35f
        const val BASIC_DROP_SPEED = 50f
    }

    override fun makeMove(move: Move) {
        when (move) {
            Move.MOVE_LEFT -> {
            }
            Move.MOVE_RIGHT -> {
            }
            Move.SHIFT_LEFT -> x -= speed.value
            Move.SHIFT_RIGHT -> x += speed.value
            Move.STOP -> {
                if (speed.value > 3) speed.value -= 3 else if (speed.value > 0) speed.value = 0f
            }
            Move.ATTACK -> attack()
            Move.MOVE_UP -> {
            }
            Move.MOVE_DOWN -> {
            }
            Move.DIE -> die()
            Move.JUMP -> jump()
            Move.DROP_DOWN -> vy = BASIC_DROP_SPEED
            Move.TURN_AROUND -> {
            }
        }
    }

    fun attack() {
        isAttacking = true
        currentFrameManager = attackFrameManager
        spawnedHitbox = SpawnedHitbox(this, BASIC_ATTACK_DISTANCE, ::killEnemy)
    }

    fun killEnemy(enemy: CollisionSource) {
        if (enemy.source is Enemy) {
            enemy.source.die()
        }
    }


    fun jump(ignoreCounter: Boolean = false) {
        if (jumpCounter < 2 || ignoreCounter) {
            jumpCounter++
            vy = BASIC_JUMP_SPEED * -1
            currentFrameManager = jumpStartFrameManager
            isInAir = true
        }
    }

    fun die() {
        isDead = true
        speed.value = 0f
        currentFrameManager = dieFrameManager
    }

    fun gameOver() {
        //TODO
    }

    override fun onCollision(collision: CollisionSource) {
        super.onCollision(collision)
        if (collision.source is Enemy) {
            if (collision.verticalPosition == CollisionSource.SourcePosition.ON_BOTTOM) {
                collision.source.die()
                jump(true)
            } else this.die()
        }
    }
}