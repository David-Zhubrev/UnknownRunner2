package com.appdav.unknownrunner.gameobjects.platforms

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.tools.Screen

abstract class TileManagerBase(val res: Resources, val speed: Speed) : TileManager {

    protected abstract val tiles: ArrayList<Platform>
    var mDestroyed = false

    override fun draw(canvas: Canvas, paint: Paint) = tiles.forEach { it.draw(canvas, paint) }

    override fun update() {
        val iterator = tiles.iterator()
        while (iterator.hasNext()) {
            val platform = iterator.next()
            if (platform.isDestroyed()) iterator.remove()
            else platform.update()
        }
        if ((tiles.last().x) < Screen.screenWidth * 1.5) {
            tiles.addAll(createTiles(tiles.last().x))
        }
    }

    override fun undoUpdate() = tiles.forEach { it.undoUpdate() }


    override fun destroy() {
        tiles.forEach { it.destroy() }
        tiles.clear()
        mDestroyed = true
    }

    override fun isDestroyed(): Boolean = mDestroyed

    protected fun createTiles(initialX: Float = 0f): List<Platform> {
        val result = ArrayList<Platform>()
        var currentX = initialX
        while (currentX < initialX + Screen.screenWidth * 1.5) {
            result.add(GroundTile(res, speed).apply {
                x = currentX
                y = (Screen.screenHeigth - height).toFloat()
                currentX += width
            })
        }
        return result
    }

    override fun getCollidables(): List<Collidable> = tiles
}