package com.appdav.unknownrunner.gameobjects.collectables

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.CoinBitmapContainer
import com.appdav.unknownrunner.gameobjects.characters.concrete.MainCharacter
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource
import com.appdav.unknownrunner.gameobjects.level.Speed
import com.appdav.unknownrunner.tools.Score

class Coin(res: Resources, speed: Speed) :
    Collectable(res, CoinBitmapContainer.COIN_DOWN_SCALE, speed) {

    override var currentFrameManager: FrameManager? =
        FrameManager(CoinBitmapContainer.idleBitmaps!!)

    override fun onDraw() {}
    override fun undoUpdate() {}

    override var isInAir: Boolean = false

    override fun update() {
        super.update()
        if (!isInAir) landingCallback?.invoke()
        y += vy
    }

    private var landingCallback: (() -> Unit)? = null

    override fun onCollision(collision: CollisionSource) {
        super.onCollision(collision)
        if (collision.source is MainCharacter) {
            isTaken = true
            Score.gold++
            landingCallback = ::destroy
            jump()
        }
    }

    fun jump() {
        vy = -20f
        isInAir = true
    }

}