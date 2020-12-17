package com.appdav.unknownrunner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.appdav.unknownrunner.gameobjects.characters.player.Controllable
import com.appdav.unknownrunner.gameobjects.level.Level
import com.appdav.unknownrunner.gameobjects.level.concrete.MountainLevel
import com.appdav.unknownrunner.tools.Score

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

    private fun onGameOver() {
        controllable = null
        level?.destroy()
        level = null
        thread?.isRunning = false
        thread = null
        gameActivityCallback?.onGameOver()
    }

    fun isRunning(): Boolean = thread?.isRunning ?: false


    private var thread: GameViewThread? = null

    var level: Level? = null

    private val paint: Paint = Paint()

    var controllable: Controllable?

    var gameActivityCallback: GameActivityCallback? = null

    init {
        isLongClickable = true
        holder.addCallback(this)
        level = MountainLevel(resources, ::onGameOver).also {
            controllable = it.controllable
            setOnTouchListener(object : GameViewTouchEventListener(context, controllable) {})
        }
        thread = GameViewThread(holder, this)
        Score(::updateScore)
    }

    fun updateScore(){
        gameActivityCallback?.onScoreUpdated()
    }

    fun startThread() {
        if (level == null) level = MountainLevel(resources, ::onGameOver).also {
            controllable = it.controllable
            setOnTouchListener(object : GameViewTouchEventListener(context, controllable) {})
        }
        controllable = level?.controllable
        if (thread == null) thread = GameViewThread(holder, this)
        thread?.apply {
            isRunning = true
            start()
        }
        gameActivityCallback?.onGameStart()
    }

    fun update() {
        level?.update()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        level?.draw(canvas ?: return, paint)
    }

    fun stop() {
        thread?.isRunning = false
        thread = null
        gameActivityCallback?.onGamePaused()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        startThread()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        destroy()
    }

    private fun destroy() {
        if (thread?.isRunning == true) stop()
        controllable = null
        setOnTouchListener(null)
        level?.destroy()
        holder.removeCallback(this)
    }


    interface GameActivityCallback {
        fun onGameOver()
        fun onGameStart()
        fun onGamePaused()
        fun onScoreUpdated()
    }

}