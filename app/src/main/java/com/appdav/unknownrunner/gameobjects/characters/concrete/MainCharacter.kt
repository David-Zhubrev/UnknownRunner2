package com.appdav.unknownrunner.gameobjects.characters.concrete

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.FallenAngel3BitmapContainer
import com.appdav.unknownrunner.gameobjects.characters.Character
import com.appdav.unknownrunner.gameobjects.characters.Enemy
import com.appdav.unknownrunner.gameobjects.characters.CompanionHitbox
import com.appdav.unknownrunner.gameobjects.characters.player.Player
import com.appdav.unknownrunner.gameobjects.collectables.Coin
import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource
import com.appdav.unknownrunner.gameobjects.level.Speed
import com.appdav.unknownrunner.gameobjects.platforms.Platform
import com.appdav.unknownrunner.tools.Screen

class MainCharacter(
    res: Resources,
    speed: Speed,
    private val gameOverCallback: () -> Unit
) : Character(res, downScaleConst, speed) {

    override var mDestroyed = false

    private var isAttacking = false
    private var jumpCounter = 0

    override var isInAir: Boolean = true
        get() = super.isInAir
        set(value) {
            if (field && !value)
                currentFrameManager = runFrameManager
            field = value
            jumpCounter = 0
        }

    private val runFrameManager = FrameManager(FallenAngel3BitmapContainer.runningBitmaps!!)
    private val dieFrameManager =
        FrameManager(FallenAngel3BitmapContainer.runningBitmaps!!, ::gameOver)
    private val attackFrameManager =
        FrameManager(FallenAngel3BitmapContainer.attackBitmaps!!) {
            isAttacking = false
            currentFrameManager = runFrameManager
        }
    private val airborneFrameManager = FrameManager(FallenAngel3BitmapContainer.airborneBitmaps!!)
    private val jumpStartFrameManager =
        FrameManager(FallenAngel3BitmapContainer.jumpStartBitmaps!!) {
            currentFrameManager = airborneFrameManager
        }

    override var currentFrameManager: FrameManager? = runFrameManager
        set(value) {
            field = value
            if (value == attackFrameManager) {
                isAttacking = true
            } else {
                isAttacking = false
                attackFrameManager.reset()
            }

        }
    override lateinit var player: Player

    private var viewSight: CompanionHitbox? = CompanionHitbox(
        this,
        width / 2,
        ::onViewSightCollision
    )

    private var isGapAhead = true

    private fun onViewSightCollision(source: CollisionSource) {
        if (source.source is Platform
            && source.verticalImpact > 0.2
        ) isGapAhead = false
        if (source.source is Enemy && isAttacking)
            source.source.die()
    }

    override val hitboxThresholdLeft: Int = FallenAngel3BitmapContainer.HITBOX_MARGIN_LEFT
    override val hitboxThresholdRight: Int = FallenAngel3BitmapContainer.HITBOX_MARGIN_RIGHT
    override val hitboxThresholdTop: Int = FallenAngel3BitmapContainer.HITBOX_MARGIN_TOP
    override val hitboxThresholdBottom: Int = FallenAngel3BitmapContainer.HITBOX_MARGIN_BOTTOM

    init {
        x = INITIAL_X
        y = (Screen.screenHeigth - height).toFloat()
    }


    companion object {
        const val downScaleConst = 10f
        const val BASIC_JUMP_SPEED = 35f
        const val BASIC_DROP_SPEED = 50f
        val INITIAL_X = Screen.screenWidth / 4f
    }

    override fun makeMove(move: Move) {
        when (move) {
            Move.SHIFT_LEFT -> x -= speed.value
            Move.SHIFT_RIGHT -> x += speed.value
            Move.STOP -> {
                if (speed.value >= 7) speed.value -= 7 else if (speed.value > 0) speed.value = 0f
            }
            Move.ATTACK -> attack()
            Move.DIE -> isDead = true
            Move.JUMP -> jump()
            Move.DROP_DOWN -> vy = BASIC_DROP_SPEED
            else -> return
        }
    }


    override fun getCollidables(): List<Collidable> {
        return when {
            isDead -> emptyList()
            viewSight == null -> listOf(this)
            else -> listOf(this, viewSight!!)
        }
    }

    private fun attack() {
        isAttacking = true
        currentFrameManager = attackFrameManager
    }

    override fun update() {
        vx = 0f
        if (isDead) {
            if (currentFrameManager !== dieFrameManager) currentFrameManager = dieFrameManager
            speed.isStopped = true
            speed.value = 0f
        }
        if (!isInAir && (currentFrameManager === airborneFrameManager || currentFrameManager === jumpStartFrameManager))
            currentFrameManager = runFrameManager
        super.update()
        x = INITIAL_X
        viewSight?.update()
        if (y > Screen.screenHeigth * 2) gameOver()
        if (isGapAhead) isInAir = true
        isGapAhead = true
    }

    private fun jump(ignoreCounter: Boolean = false) {
        if (jumpCounter < 2 || ignoreCounter) {
            jumpCounter++
            vy = BASIC_JUMP_SPEED * -1
            if (currentFrameManager == runFrameManager) currentFrameManager = jumpStartFrameManager
            isInAir = true
        }
    }


    private fun gameOver() {
        gameOverCallback.invoke()
    }

    override fun onCollision(collision: CollisionSource) {
        super.onCollision(collision)
        if (collision.source is Enemy) {
            if (collision.verticalPosition == CollisionSource.SourcePosition.ON_BOTTOM
                && collision.verticalImpact > 0.2
            ) {
                collision.source.die()
                jump(true)
            } else if (isAttacking)
                collision.source.die()
            else if (collision.verticalImpact > 0.2 || collision.horizontalImpact > 0.2) this.isDead =
                true
        }
        if (collision.source is Coin) {
            collision.source.jump()
        }
    }
}