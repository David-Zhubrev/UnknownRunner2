package com.appdav.unknownrunner.gameobjects.characters.concrete

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.Golem1BitmapContainer
import com.appdav.unknownrunner.gameobjects.characters.Enemy
import com.appdav.unknownrunner.gameobjects.characters.CompanionHitbox
import com.appdav.unknownrunner.gameobjects.characters.player.EnemyAi
import com.appdav.unknownrunner.gameobjects.characters.player.concrete.GolemSimpleAi
import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource
import com.appdav.unknownrunner.gameobjects.level.Speed

class Golem1(
    res: Resources,
    speed: Speed
) : Enemy(res, DOWNSCALE, speed) {

    companion object {
        const val DOWNSCALE = 5f
        const val BASIC_MOVE_SPEED = 10f
    }

    override val hitboxThresholdBottom: Int = Golem1BitmapContainer.HITBOX_MARGIN_BOTTOM
    override val hitboxThresholdLeft: Int = Golem1BitmapContainer.HITBOX_MARGIN_LEFT
    override val hitboxThresholdTop: Int = Golem1BitmapContainer.HITBOX_MARGIN_TOP
    override val hitboxThresholdRight: Int = Golem1BitmapContainer.HITBOX_MARGIN_RIGHT

    private val runningLeftFrameManager =
        FrameManager(Golem1BitmapContainer.runningBitmapsMirrored!!)
    private val runningRightFrameManager = FrameManager(Golem1BitmapContainer.runningBitmaps!!)
    private val dieFrameManager = FrameManager(Golem1BitmapContainer.dyingBitmaps!!, ::destroy)
    private val attackFrameManager =
        FrameManager(Golem1BitmapContainer.attackBitmaps!!, ::onAttackFinished)

    override var player: EnemyAi = GolemSimpleAi(this)

    override var currentFrameManager: FrameManager? = runningLeftFrameManager
    override var visionSight: CompanionHitbox? = CompanionHitbox(
        this,
        width / 2, player::onObjectInSight
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
        player = Dummy(this)
        vx = 0f
        isDead = true
        visionSight = null
        currentFrameManager = dieFrameManager
    }

    override fun onEnemyDetected(source: CollisionSource) {
        attack()
    }


    override fun getCollidables(): List<Collidable> {
        return when {
            isDead -> emptyList()
            visionSight == null -> listOf(this)
            else -> listOf(
                this,
                visionSight!!
            )
        }
    }


    override fun attack() {
        vx = 0f
        currentFrameManager = attackFrameManager
    }

    override fun makeMove(move: Move) {
        when (move) {
            Move.MOVE_LEFT -> vx = BASIC_MOVE_SPEED * -1
            Move.MOVE_RIGHT -> vx = BASIC_MOVE_SPEED
            Move.SHIFT_LEFT -> x -= speed.value
            Move.SHIFT_RIGHT -> x += speed.value
            Move.ATTACK -> attack()
            Move.DIE -> die()
            Move.TURN_AROUND -> turnAround()
            else -> return
        }
    }

    private fun onAttackFinished() {
        currentFrameManager =
            if (direction == Direction.LEFT) runningLeftFrameManager.also {
                vx = BASIC_MOVE_SPEED * -1
            } else runningRightFrameManager.also { vx = BASIC_MOVE_SPEED }
        attackFrameManager.reset()
    }
}