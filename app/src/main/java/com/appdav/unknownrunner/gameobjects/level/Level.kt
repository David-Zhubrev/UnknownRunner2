package com.appdav.unknownrunner.gameobjects.level

import android.graphics.Canvas
import android.graphics.Paint
import com.appdav.unknownrunner.gameobjects.backgrounds.Background
import com.appdav.unknownrunner.gameobjects.GameDrawable
import com.appdav.unknownrunner.gameobjects.characters.EnemyManager
import com.appdav.unknownrunner.gameobjects.characters.concrete.MainCharacter
import com.appdav.unknownrunner.gameobjects.characters.player.Controllable
import com.appdav.unknownrunner.gameobjects.collision.CollidableListManager
import com.appdav.unknownrunner.gameobjects.collision.CollisionHandler
import com.appdav.unknownrunner.gameobjects.platforms.TileManager

abstract class Level() : GameDrawable {

    abstract val background: Background
    abstract val tileManager: TileManager
    abstract val controllable: Controllable
    abstract val mainCharacter: MainCharacter
    abstract val collidableListManager: CollidableListManager
    abstract val enemyManager: EnemyManager

    abstract val speed: Speed

    protected val collisionHandler by lazy { CollisionHandler() }

    protected open var mDestroyed = false

    override fun draw(canvas: Canvas, paint: Paint) {
        background.draw(canvas, paint)
        tileManager.draw(canvas, paint)
        mainCharacter.draw(canvas, paint)
        enemyManager.draw(canvas, paint)
    }

    override fun update() {
        background.update()
        tileManager.update()
        mainCharacter.update()
        enemyManager.update()
        collisionHandler.checkCollisions(
            collidableListManager.getCollidables()
        )
    }

    override fun undoUpdate() {
        background.undoUpdate()
        tileManager.undoUpdate()
        mainCharacter.undoUpdate()
    }

    override fun destroy() {
        background.destroy()
        tileManager.destroy()
        mainCharacter.destroy()
        mDestroyed = true
    }

    override fun isDestroyed(): Boolean = mDestroyed

}