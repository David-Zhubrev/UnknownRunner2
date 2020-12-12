package com.appdav.unknownrunner

import android.util.Log
import android.view.SurfaceHolder
import com.appdav.unknownrunner.tools.Constant

class GameViewThread(
    private val holder: SurfaceHolder,
    private val gameView: GameView
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
        var totalTime: Long = 0
        val targetTime = 1000L / FPS_LIMIT
        while (isRunning) {
            if (!holder.surface.isValid) continue
            startTime = System.nanoTime()
            val canvas = holder.lockCanvas() ?: continue
            gameView.apply {
                update()
                draw(canvas)
            }
            holder.unlockCanvasAndPost(canvas)
            //Count drawing time and sleep so that frame count won't exceed fps lock
            drawTime = (System.nanoTime() - startTime) / 1_000_000
            val waitTime = targetTime - drawTime
            if (waitTime > 0) {
                try {
                    sleep(waitTime)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            frameCount++
            totalTime += (System.nanoTime() - startTime)
            if (totalTime >= 1_000_000_000) {
                Log.e(Constant.FPS_LOG, frameCount.toString())
                frameCount = 0
                totalTime = 0
            }
        }
    }
}