package com.appdav.unknownrunner.gameobjects.characters.concrete

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.Golem1BitmapContainer
import com.appdav.unknownrunner.gameobjects.characters.Enemy
import com.appdav.unknownrunner.gameobjects.characters.SpawnedHitbox
import com.appdav.unknownrunner.gameobjects.characters.player.EnemyAi
import com.appdav.unknownrunner.gameobjects.characters.player.GolemSimpleAi
import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource
import com.appdav.unknownrunner.gameobjects.level.Speed

class Golem1(
    res: Resources,
    speed: Speed
) : Enemy(res, DOWNSCALE, speed) {

    companion object {
        const val DOWNSCALE = 5f
        const val BASIC_MOVE_SPEED = 5f
    }

    override val hitboxThresholdBottom: Int = Golem1BitmapContainer.HITBOX_MARGIN_BOTTOM
    override val hitboxThresholdLeft: Int = Golem1BitmapContainer.HITBOX_MARGIN_LEFT
    override val hitboxThresholdTop: Int = Golem1BitmapContainer.HITBOX_MARGIN_TOP
    override val hitboxThresholdRight: Int = Golem1BitmapContainer.HITBOX_MARGIN_RIGHT

    val runningLeftFrameManager = FrameManager(Golem1BitmapContainer.runningBitmapsMirrored!!)
    val runningRightFrameManager = FrameManager(Golem1BitmapContainer.runningBitmaps!!)

    //val idleFrameManager =
    val dieFrameManager = FrameManager(Golem1BitmapContainer.dyingBitmaps!!, ::destroy)
    val attackFrameManager = FrameManager(Golem1BitmapContainer.attackBitmaps!!, ::onAttackFinished)

    override var player: EnemyAi = GolemSimpleAi(this)

    override var currentFrameManager: FrameManager? = runningLeftFrameManager
    override var visionSight: SpawnedHitbox = SpawnedHitbox(
        this,
        15, player::onMainPlayerInSight
    )

    override fun turnAround() {
        if (direction == Direction.LEFT) {
            direction = Direction.RIGHT
            currentFrameManager = runningRightFrameManager
        } else
            if (direction == Direction.RIGHT) {
                direction = Direction.LEFT
                currentFrameManager = runningLeftFrameManager
            }
    }

    override fun die() {
        //TODO
        vx = 0f
        isDead = true
        currentFrameManager = dieFrameManager
    }

    fun onEnemyDetected(source: CollisionSource) {
        attack()
    }

    override fun getCollidables(): List<Collidable> {
        return listOf(
            this,
            visionSight
        )
    }

    override fun attack() {
        currentFrameManager = attackFrameManager
    }

    override fun makeMove(move: Move) {
        when (move) {
            Move.MOVE_LEFT -> vx = BASIC_MOVE_SPEED * -1
            Move.MOVE_RIGHT -> vx = BASIC_MOVE_SPEED
            Move.SHIFT_LEFT -> x -= speed.value
            Move.SHIFT_RIGHT -> x += speed.value
//            Move.STOP -> stop()
            Move.ATTACK -> attack()
            Move.MOVE_UP -> {
            }
            Move.MOVE_DOWN -> {
            }
            Move.DIE -> die()
            Move.JUMP -> {
            }
            Move.DROP_DOWN -> {
            }
            Move.TURN_AROUND -> turnAround()
            else -> return
        }
    }

//    fun stop(){
//        vx = 0f
//        currentFrameManager = idleFrameManager
//    }

    fun onAttackFinished() {
        currentFrameManager =
            if (direction == Direction.LEFT) runningLeftFrameManager else runningRightFrameManager
    }
}