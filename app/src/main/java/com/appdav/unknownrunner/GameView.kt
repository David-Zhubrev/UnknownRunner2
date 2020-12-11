package com.appdav.unknownrunner

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int,
    defStyleRes: Int
) : SurfaceView(context, attributeSet, defStyleAttr, defStyleRes), SurfaceHolder.Callback {

    private var thread: GameViewThread? = null
    var level: GameDrawable? = null
    private val paint: Paint

    var isRunning = false

    init {
        holder.addCallback(this)
        //TODO: add level
        //TODO: setOnTouchListener()
        paint = Paint()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        TODO("Not yet implemented")
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        start()
    }

    private fun start() {
        thread = GameViewThread(holder, this, level ?: return)
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