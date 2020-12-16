package com.appdav.unknownrunner

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.appdav.unknownrunner.gameobjects.characters.player.Controllable
import com.appdav.unknownrunner.tools.Screen
import kotlin.math.abs

open class GameViewTouchEventListener(
    private val context: Context,
    private val controllable: Controllable
) : View.OnTouchListener {

    private val listener: GestureDetector = GestureDetector(context, GestureListener())

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return listener.onTouchEvent(event)
    }

    companion object {
        const val SWIPE_THRESHOLD = 100
        const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            try{
                if (e!!.x < Screen.screenWidth / 2) controllable.onLeftSideClick()
            } catch (e: Exception){
                e.printStackTrace()
            }
            return false
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            try {
                val diffY = e2!!.y - e1!!.y
                val diffX = e2.x - e1.x
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) controllable.onSwipeRight()
                        else controllable.onSwipeLeft()
                    }
                } else {
                    if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) controllable.onSwipeBottom()
                        else controllable.onSwipeUp()
                    }
                }
            } catch (e: NullPointerException) {
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }
    }

}