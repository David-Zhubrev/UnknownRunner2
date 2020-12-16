package com.appdav.unknownrunner.gameobjects

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.DrawableRes
import com.appdav.unknownrunner.gameobjects.level.Speed
import com.appdav.unknownrunner.tools.Screen

abstract class GameObject(
    protected val res: Resources,
    protected val downScale: Float,
    protected val speed: Speed
) : GameDrawable {

    var x: Float = 0f
    var y: Float = 0f

    //TODO: add speed
    open val width: Int by lazy { currentFrameManager?.getFrameWidth() ?: 0 }
    open val height: Int by lazy { currentFrameManager?.getFrameHeight() ?: 0 }

    protected open var mDestroyed = false
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
        mDestroyed = true
    }

    override fun update() {
        if (x < -Screen.screenWidth * 0.5 || y > Screen.screenHeigth * 3 || y < -Screen.screenHeigth * 2) destroy()
    }

    fun createBitmap(
        @DrawableRes resId: Int,
        useFilter: Boolean
    ): Bitmap {
        BitmapFactory.decodeResource(res, resId).also {
            return Bitmap.createScaledBitmap(
                it,
                (it.width / downScale * Screen.ratioX).toInt(),
                (it.height / downScale * Screen.ratioY).toInt(),
                useFilter
            )
        }
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        if (mDestroyed) return
        currentFrameManager?.let {
            canvas.drawBitmap(it.nextFrame(), x, y, paint)
        }
        onDraw()
    }

    abstract fun onDraw()

    class FrameManager(
        private val frames: List<Bitmap>,
        val lastFrameListener: (() -> Unit)? = null

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

    }

}