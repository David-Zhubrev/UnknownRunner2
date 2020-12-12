package com.appdav.unknownrunner.gameobjects

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.DrawableRes
import com.appdav.unknownrunner.gameobjects.platforms.Speed
import com.appdav.unknownrunner.tools.Screen

abstract class GameObject(
    protected val res: Resources,
    protected val downScale: Float,
    protected val speed: Speed
) : GameDrawable {

    var x: Float = 0f
    var y: Float = 0f

    //TODO: add speed
    val width: Int by lazy { currentFrameManager?.getFrameWidth() ?: 0 }
    val height: Int by lazy { currentFrameManager?.getFrameHeight() ?: 0 }

    protected var mDestroyed = false
    protected abstract var currentFrameManager: FrameManager?

    protected val frameManagers: ArrayList<FrameManager?> = ArrayList<FrameManager?>().also {
        it.add(currentFrameManager)
    }

    final override fun isDestroyed(): Boolean = mDestroyed

    override fun destroy() {
        //TODO: add basic destruction behaviour
        currentFrameManager = null
        for (i in 0..frameManagers.lastIndex) {
            frameManagers[i] = null
        }
    }

    override fun update() {
        if (x < -Screen.screenWidth * 0.5 || y > Screen.screenHeigth * 2) destroy()

    }

    fun createBitmap(
        @DrawableRes resId: Int,
        useFilter: Boolean
    ): Bitmap {
        BitmapFactory.decodeResource(res, resId).also {
            return Bitmap.createScaledBitmap(
                it,
                (it.width / downScale).toInt(),
                (it.height / downScale).toInt(),
                useFilter
            )
        }
    }

    abstract class BitmapContainer {
        abstract var bitmaps: List<Bitmap>?
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        if (mDestroyed) return
        currentFrameManager?.let {
            canvas.drawBitmap(it.nextFrame(), x, y, paint)
        }
    }

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