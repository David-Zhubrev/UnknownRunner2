package com.appdav.unknownrunner

import android.util.Log
import android.view.SurfaceHolder
import com.appdav.unknownrunner.tools.Constant.LOG_TAG

class GameViewThread(
    private val holder: SurfaceHolder,
    private val view: GameView
) : Thread() {

    companion object {
        const val targetFps = 60
    }

    var isRunning: Boolean = false



    override fun run() {
        var startTime: Long
        var drawTime: Long
        var waitTime: Long
        var totalTime: Long = 0

        var frameCount = 0
        val targetTime = 1000L / targetFps
        while (isRunning && view.level?.isDestroyed() == false) {
            if (!holder.surface.isValid) continue
            startTime = System.nanoTime()
            val canvas = holder.lockCanvas() ?: continue
            view.apply {
                update()
                draw(canvas)
            }
            holder.unlockCanvasAndPost(canvas)
            drawTime = (System.nanoTime() - startTime) / 1_000_000L
            waitTime = targetTime - drawTime
            if (waitTime > 0) {
                try {
                    sleep(waitTime)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            frameCount++
            totalTime += System.nanoTime() - startTime
            if (totalTime >= 1_000_000_000) {
                Log.d(LOG_TAG, "FPS: $frameCount")
                frameCount = 0
                totalTime = 0
            }
        }
    }

}
