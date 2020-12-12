package com.appdav.unknownrunner.gameobjects.level

import android.graphics.Canvas
import android.graphics.Paint
import com.appdav.unknownrunner.gameobjects.backgrounds.Background
import com.appdav.unknownrunner.gameobjects.GameDrawable
import com.appdav.unknownrunner.gameobjects.platforms.Speed
import com.appdav.unknownrunner.gameobjects.platforms.TileManager

abstract class Level() : GameDrawable {

    abstract val background: Background
    abstract val tileManager: TileManager

    abstract val speed: Speed

    var mDestroyed = false

    override fun draw(canvas: Canvas, paint: Paint) {
        background.draw(canvas, paint)
        tileManager.draw(canvas, paint)
    }

    override fun update() {
        background.update()
        tileManager.update()
    }

    override fun undoUpdate() {
        background.undoUpdate()
        tileManager.undoUpdate()
    }

    override fun destroy() {
        background.destroy()
        tileManager.destroy()
        mDestroyed = true
    }

    override fun isDestroyed(): Boolean = mDestroyed
}