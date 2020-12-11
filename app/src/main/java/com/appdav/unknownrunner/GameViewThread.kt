package com.appdav.unknownrunner

import android.util.Log
import android.view.SurfaceHolder

class GameViewThread(
    private val holder: SurfaceHolder,
    private val gameView: GameView,
    private val level: GameDrawable
) : Thread() {

    private var frameCount = 0

    var isRunning = false

    companion object {
        const val FPS_LIMIT = 60 // target fps lock
        const val WAIT_TIME_LIMIT = 1000 //max lag time limit
    }

    override fun run() {
        var startTime: Long
        var drawTime: Long
        var waitTime: Long
        var totalTime: Long = 0

        val targetTime = 1000L / FPS_LIMIT

        while (isRunning && !level.isDestroyed()) {
            if (!holder.surface.isValid) continue
            startTime = System.nanoTime()
            val canvas = holder.lockCanvas() ?: continue
            if (level.isDestroyed()) break else level.update()
            gameView.draw(canvas)
            holder.unlockCanvasAndPost(canvas)
            drawTime = (System.nanoTime() - startTime) / 1_000_000
            //Count drawing time and sleep for it's value so that frame count won't exceed target fps
            (targetTime - drawTime).let {
                if (it > WAIT_TIME_LIMIT) return@let //TODO: make some freeze safety implementation
                if (it > 0) {
                    try {
                        sleep(it)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            frameCount++
            totalTime += System.nanoTime() - startTime
            if (totalTime >= 1_000_000_000) {
                Log.e(Constant.FPS_LOG, frameCount.toString().apply {
                    frameCount = 0
                    totalTime = 0
                })
            }
        }
    }
}