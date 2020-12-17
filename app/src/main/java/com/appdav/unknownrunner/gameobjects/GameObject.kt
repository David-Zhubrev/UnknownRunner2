package com.appdav.unknownrunner.gameobjects

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.appdav.unknownrunner.gameobjects.level.Speed
import com.appdav.unknownrunner.tools.Screen

abstract class GameObject(
    protected val res: Resources,
    protected val downScale: Float,
    protected val speed: Speed
) : GameDrawable {

    var x: Float = 0f
    var y: Float = 0f

    open val width: Int by lazy { currentFrameManager?.getFrameWidth() ?: 0 }
    open val height: Int by lazy { currentFrameManager?.getFrameHeight() ?: 0 }

    protected open var mDestroyed = false
    protected abstract var currentFrameManager: FrameManager?

    final override fun isDestroyed(): Boolean = mDestroyed

    override fun destroy() {
        currentFrameManager = null
        mDestroyed = true
    }

    override fun update() {
        if (x < -Screen.screenWidth * 0.5 || y > Screen.screenHeigth * 3 || y < -Screen.screenHeigth) destroy()
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        if (mDestroyed) return
        if (x + width < -15 || x > Screen.screenWidth * 1.2) return
        currentFrameManager?.let {
            canvas.drawBitmap(it.nextFrame(), x, y, paint)
        }
        onDraw()
    }

    abstract fun onDraw()

    class FrameManager(
        private val frames: List<Bitmap>,
        private val lastFrameListener: (() -> Unit)? = null

    ) {

        fun getFrameWidth() = frames[0].width
        fun getFrameHeight() = frames[0].height


        private var currentFrame = 0
        private val lastFrameIndex = frames.lastIndex

        fun nextFrame(): Bitmap {
            return if (currentFrame == lastFrameIndex) {
                currentFrame = 0
                lastFrameListener?.invoke()
                frames[lastFrameIndex]
            } else frames[currentFrame++]
        }

        fun reset() {
            currentFrame = 0
        }

    }

}