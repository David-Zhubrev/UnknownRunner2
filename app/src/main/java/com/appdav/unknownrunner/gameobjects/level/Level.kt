package com.appdav.unknownrunner.gameobjects.level

import android.graphics.Canvas
import android.graphics.Paint
import com.appdav.unknownrunner.gameobjects.backgrounds.Background
import com.appdav.unknownrunner.gameobjects.GameDrawable
import com.appdav.unknownrunner.gameobjects.characters.concrete.MainCharacter
import com.appdav.unknownrunner.gameobjects.characters.player.Controllable
import com.appdav.unknownrunner.gameobjects.collision.CollidableListManager
import com.appdav.unknownrunner.gameobjects.collision.CollisionHandler

abstract class Level : GameDrawable {

    abstract val background: Background
    abstract val controllable: Controllable
    abstract val mainCharacter: MainCharacter
    abstract val collidableListManager: CollidableListManager
    abstract val levelContentManager: LevelContentManager

    abstract val speed: Speed

    private val collisionHandler by lazy { CollisionHandler() }

    protected open var mDestroyed = false


    override fun draw(canvas: Canvas, paint: Paint) {
        background.draw(canvas, paint)
        levelContentManager.draw(canvas, paint)
        mainCharacter.draw(canvas, paint)
    }

    override fun update() {
        background.update()
        levelContentManager.update()
        mainCharacter.update()
        collisionHandler.checkCollisions(
            collidableListManager.getCollidables()
        )
        if (!speed.isStopped && speed.value < 15f) speed.value += 3
    }

    override fun undoUpdate() {
        background.undoUpdate()
        levelContentManager.undoUpdate()
        mainCharacter.undoUpdate()
    }

    override fun destroy() {
        background.destroy()
        levelContentManager.destroy()
        mainCharacter.destroy()
        mDestroyed = true
    }

    override fun isDestroyed(): Boolean = mDestroyed

}