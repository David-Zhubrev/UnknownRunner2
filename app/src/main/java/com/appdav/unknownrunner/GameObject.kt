package com.appdav.unknownrunner

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

abstract class GameObject(
    protected final val res: Resources,
    protected final val downScale: Int
) : GameDrawable {

    protected var x: Float = 0f
    protected var y: Float = 0f
    protected val width: Int
    protected val height: Int

    protected var mDestroyed = false
    protected var currentFrameManager: FrameManager?

    protected val frameManagers: ArrayList<FrameManager?>

    init {
        currentFrameManager = createMainFrameManager().also {
            frameManagers = ArrayList()
            frameManagers.add(it)
        }
        width = currentFrameManager?.getFrameWidth() ?: 0
        height = currentFrameManager?.getFrameHeight() ?: 0
    }

    override fun isDestroyed(): Boolean = mDestroyed

    override fun destroy() {
        currentFrameManager = null
        for (i in 0..frameManagers.lastIndex) {
            frameManagers[i] = null
        }
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        if (mDestroyed) return
        currentFrameManager?.let {
            canvas.drawBitmap(it.nextFrame(), x, y, paint)
        }
    }

    protected abstract fun createMainFrameManager(): FrameManager


    protected class FrameManager(
        private val frames: List<Bitmap>,
        val listener: OnLastFrameShownListener? = null

    ) {

        fun getFrameWidth() = frames[0].width
        fun getFrameHeight() = frames[0].height


        private var currentFrame = 0
        private val lastFrameIndex = frames.lastIndex

        fun nextFrame(): Bitmap {
            return if (currentFrame == lastFrameIndex) {
                currentFrame = 0
                listener?.onLastFrameShown()
                frames[lastFrameIndex]
            } else frames[currentFrame]
        }

        interface OnLastFrameShownListener {
            fun onLastFrameShown()
        }
    }

}