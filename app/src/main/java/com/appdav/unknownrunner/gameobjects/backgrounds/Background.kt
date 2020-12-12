package com.appdav.unknownrunner.gameobjects.backgrounds

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.DrawableRes
import com.appdav.unknownrunner.gameobjects.GameDrawable
import com.appdav.unknownrunner.gameobjects.GameObject
import com.appdav.unknownrunner.gameobjects.platforms.Speed
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

    override fun destroy() {
        layers.forEach { it.destroy() }
        mDestroyed = true
    }

    override fun update() = layers.forEach { it.update() }

    override fun undoUpdate() = layers.forEach { it.undoUpdate() }

    inner class Layer(
        res: Resources,
        @DrawableRes val resId: Int,
        speedModifier: SpeedModifier = SpeedModifier(
            SpeedModifier.MODE.MULT,
            0f
        ),
        useFilter: Boolean = false
    ) : GameDrawable {

        private var xFirst: Float = 0f
        private var y: Float = 0f

        private var xSecond: Float = xFirst + screenWidth
        private var xThird: Float = xSecond + screenWidth

        private var mDestroyed = false
        override fun isDestroyed(): Boolean = mDestroyed

        private val layerSpeed = speedModifier.mode.mathFunction(speed.value, speedModifier.value)

        private val bitmap: Bitmap = run {
            val tmp = BitmapFactory.decodeResource(res, resId)
            Bitmap.createScaledBitmap(tmp, screenWidth, screenHeigth, useFilter)
        }

        override fun draw(canvas: Canvas, paint: Paint) {
            if (this.layerSpeed != 0f) {
                canvas.run {
                    drawBitmap(bitmap, xFirst, y, paint)
                    drawBitmap(bitmap, xSecond, y, paint)
                    drawBitmap(bitmap, xThird, y, paint)
                }
            } else {
                canvas.run {
                    drawBitmap(bitmap, xFirst, y, paint)
                    drawBitmap(bitmap, xSecond, y, paint)
                }
            }
            frameMoved = false
        }

        private var frameMoved = false

        override fun update() {
            xFirst -= this.layerSpeed
            xSecond -= this.layerSpeed
            xThird -= this.layerSpeed
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

        override fun undoUpdate() {
            xFirst += this.layerSpeed
            xSecond += this.layerSpeed
            xThird += this.layerSpeed
            if (frameMoved) {
                if (xFirst > xSecond && xFirst > xThird) xFirst = xThird - screenWidth
                else if (xSecond > xThird) xSecond = xFirst - screenWidth
                else xThird = xFirst - screenWidth
            }
        }

        override fun destroy() {
            mDestroyed = true
        }

    }

    data class SpeedModifier(
        val mode: MODE,
        val value: Float
    ) {
        enum class MODE(val mathFunction: (Float, Float) -> Float) {
            ADD(fun(f1: Float, f2: Float) = f1 + f2),
            SUBS(fun(f1: Float, f2: Float) = f1 - f2),
            MULT(fun(f1: Float, f2: Float) = f1 * f2),
            DIV(fun(f1: Float, f2: Float) = f1 / f2)
        }
    }

}