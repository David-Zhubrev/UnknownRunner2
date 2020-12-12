package com.appdav.unknownrunner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.appdav.unknownrunner.gameobjects.GameDrawable
import com.appdav.unknownrunner.gameobjects.level.MountainLevel

class GameView : SurfaceView, SurfaceHolder.Callback {

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var thread: GameViewThread? = null
    var level: GameDrawable =
        MountainLevel(resources)
    private val paint: Paint = Paint()


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        level.draw(canvas ?: return, paint)
    }

    init {
        holder.addCallback(this)
        //TODO: setOnTouchListener()
    }

    fun update() {
        level.update()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        destroy()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        start()
    }

    private fun start() {
        thread = GameViewThread(holder, this)
            .apply {
                isRunning = true
                start()
            }
    }

    private fun pause() {
        thread?.isRunning = false
    }

    private fun destroy() {
        pause()
        thread = null
        level?.destroy()
        holder.removeCallback(this)
    }


}