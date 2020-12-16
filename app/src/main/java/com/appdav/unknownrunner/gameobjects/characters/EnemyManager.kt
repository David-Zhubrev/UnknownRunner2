package com.appdav.unknownrunner.gameobjects.characters

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import com.appdav.unknownrunner.gameobjects.GameDrawable
import com.appdav.unknownrunner.gameobjects.characters.concrete.Golem1
import com.appdav.unknownrunner.gameobjects.collision.Collidable
import com.appdav.unknownrunner.gameobjects.level.Speed
import com.appdav.unknownrunner.tools.Screen

class EnemyManager(
    val res: Resources,
    val speed: Speed
) : GameDrawable {

    val enemies = ArrayList<Enemy>()

    var mDestroyed = false

    var counter = 0

    override fun update() {
        if (mDestroyed) return
        enemies.forEach { it.update() }
        val iterator = enemies.iterator()
        while (iterator.hasNext()) {
            val enemy = iterator.next()
            if (enemy.isDestroyed()) iterator.remove()
        }
        if (counter < 3) {
            enemies.add(Golem1(res, speed).apply { x = (Screen.screenWidth / 2).toFloat() })
            counter++
        }
    }

    override fun undoUpdate() {
        enemies.forEach { it.undoUpdate() }
    }

    override fun destroy() {
        enemies.forEach { it.destroy() }
        mDestroyed = true
    }

    override fun isDestroyed(): Boolean = mDestroyed

    override fun draw(canvas: Canvas, paint: Paint) {
        if (mDestroyed) return
        enemies.forEach { it.draw(canvas, paint) }
    }

    fun getCollidables(): List<Collidable> {
        return ArrayList<Collidable>().apply {
            enemies.forEach {
                this.addAll(it.getCollidables())
            }
        }
    }
}