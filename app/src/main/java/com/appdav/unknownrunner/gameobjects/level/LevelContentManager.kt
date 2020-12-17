package com.appdav.unknownrunner.gameobjects.level

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import com.appdav.unknownrunner.gameobjects.GameDrawable
import com.appdav.unknownrunner.gameobjects.characters.Enemy
import com.appdav.unknownrunner.gameobjects.collectables.Collectable
import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.gameobjects.platforms.Platform
import com.appdav.unknownrunner.tools.Screen

abstract class LevelContentManager(val res: Resources, val speed: Speed) : GameDrawable {

    protected val tiles = ArrayList<Platform>()
    protected val enemies = ArrayList<Enemy>()
    protected val collectibles = ArrayList<Collectable>()

    private var mDestroyed = false

    fun getCollidables(): List<Collidable> {
        return ArrayList<Collidable>().apply {
            addAll(tiles)
            addAll(collectibles)
            enemies.forEach { addAll(it.getCollidables()) }
        }
    }

    abstract fun createTiles(initialX: Float)

    override fun draw(canvas: Canvas, paint: Paint) {
        tiles.forEach { it.draw(canvas, paint) }
        enemies.forEach { it.draw(canvas, paint) }
        collectibles.forEach { it.draw(canvas, paint) }
    }

    override fun update() {
        updateEach(tiles)
        updateEach(enemies)
        updateEach(collectibles)
        if ((tiles.last().x) < Screen.screenWidth * 1.5) {
            createTiles(tiles.last().x)
        }
    }

    private fun updateEach(list: ArrayList<out GameDrawable>) {
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val elem = iterator.next()
            if (elem.isDestroyed()) iterator.remove()
            else elem.update()
        }
    }

    override fun undoUpdate() {
        tiles.forEach { it.undoUpdate() }
        enemies.forEach { it.undoUpdate() }
        collectibles.forEach { it.undoUpdate() }
    }

    override fun destroy() {
        collectibles.forEach { it.destroy() }
        tiles.forEach { it.destroy() }
        enemies.forEach { it.destroy() }
        mDestroyed = true
    }

    override fun isDestroyed(): Boolean = mDestroyed

}