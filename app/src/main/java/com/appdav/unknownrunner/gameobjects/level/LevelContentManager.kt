package com.appdav.unknownrunner.gameobjects.level

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import com.appdav.unknownrunner.gameobjects.GameDrawable
import com.appdav.unknownrunner.gameobjects.characters.Enemy
import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.gameobjects.platforms.Platform
import com.appdav.unknownrunner.tools.Screen

abstract class LevelContentManager(res: Resources, speed: Speed) : GameDrawable {

    protected val tiles = ArrayList<Platform>()
    protected val enemies = ArrayList<Enemy>()

    private var mDestroyed = false

    fun getCollidables(): List<Collidable> {
        return ArrayList<Collidable>().apply {
            addAll(tiles)
            enemies.forEach { addAll(it.getCollidables()) }
        }
    }

    fun handleTiles() {
        if (tiles.isEmpty() || tiles.last().x < Screen.screenWidth * 1.5) createTiles()
    }

    abstract fun createTiles()

    override fun draw(canvas: Canvas, paint: Paint) {
        tiles.forEach { it.draw(canvas, paint) }
        enemies.forEach { it.draw(canvas, paint) }
    }

    override fun update() {
        tiles.forEach { it.update() }
        enemies.forEach { it.update() }
    }

    override fun undoUpdate() {
        tiles.forEach { it.undoUpdate() }
        enemies.forEach { it.undoUpdate() }
    }

    override fun destroy() {
        tiles.forEach { it.destroy() }
        enemies.forEach { it.destroy() }
        mDestroyed = true
    }

    override fun isDestroyed(): Boolean = mDestroyed

    enum class Element{
        TILE,
        GAP,
        ENEMY_ON_GROUND,
        ENEMY_FALLING
    }
}