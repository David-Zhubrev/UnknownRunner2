package com.appdav.unknownrunner.gameobjects.backgrounds

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.DrawableRes
import com.appdav.unknownrunner.gameobjects.GameDrawable
import com.appdav.unknownrunner.gameobjects.GameObject
import com.appdav.unknownrunner.gameobjects.level.Speed
import com.appdav.unknownrunner.tools.Screen.Companion.screenHeigth
import com.appdav.unknownrunner.tools.Screen.Companion.screenWidth

abstract class Background(
    res: Resources,
    speed: Speed
) : GameObject(res, 1f, speed) {

    protected abstract val layers: List<Layer>
    override var currentFrameManager: FrameManager? = null

    override fun draw(canvas: Canvas, paint: Paint) {
        for (layer in layers) {
            layer.draw(canvas, paint)
        }
    }

    override fun onDraw() {
    }

    override fun destroy() {
        layers.forEach { it.destroy() }
        mDestroyed = true
    }

    override fun update() = layers.forEach { it.update() }

    override fun undoUpdate() = layers.forEach { it.undoUpdate() }

    inner class Layer(
        @DrawableRes val resId: Int,
        val divider: Float = 0f,
        useFilter: Boolean = false
    ) : GameDrawable {

        private var xFirst: Float = 0f
        private var y: Float = 0f

        private var xSecond: Float = xFirst + screenWidth
        private var xThird: Float = xSecond + screenWidth

        private var mDestroyed = false
        override fun isDestroyed(): Boolean = mDestroyed

        private val layerSpeed = speed

        private val bitmap: Bitmap = run {
            val tmp = BitmapFactory.decodeResource(res, resId)
            Bitmap.createScaledBitmap(tmp, screenWidth, screenHeigth, useFilter)
        }

        override fun draw(canvas: Canvas, paint: Paint) {
            canvas.run {
                if (x < screenWidth * 1.2) drawBitmap(bitmap, xFirst, y, paint)
                if (x < screenWidth * 1.2) drawBitmap(bitmap, xSecond, y, paint)
                if (x < screenWidth * 1.2 && divider != 0f) drawBitmap(bitmap, xThird, y, paint)
            }
            frameMoved = false
        }

        private var frameMoved = false

        override fun update() {
            if (divider != 0f) {
                xFirst -= this.layerSpeed.value / divider
                xSecond -= this.layerSpeed.value / divider
                xThird -= this.layerSpeed.value / divider
                if (xFirst + screenWidth < 0) {
                    xFirst = xThird + screenWidth
                    frameMoved = true
                }
                if (xSecond + screenWidth < 0) {
                    xSecond = xFirst + screenWidth
                    frameMoved = true
                }
                if (xThird + screenWidth < 0) {
                    xThird = xSecond + screenWidth
                    frameMoved = true
                }
            }
        }

        override fun undoUpdate() {
            if (divider != 0f) {
                xFirst += this.layerSpeed.value / divider
                xSecond += this.layerSpeed.value / divider
                xThird += this.layerSpeed.value / divider
                if (frameMoved) {
                    if (xFirst > xSecond && xFirst > xThird) xFirst = xThird - screenWidth
                    else if (xSecond > xThird) xSecond = xFirst - screenWidth
                    else xThird = xFirst - screenWidth
                }
            }
        }

        override fun destroy() {
            mDestroyed = true
        }

    }
}