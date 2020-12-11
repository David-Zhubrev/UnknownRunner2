package com.appdav.unknownrunner

import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

open class FullscreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystemUi()
        super.onCreate(savedInstanceState)
    }

    private fun hideSystemUi() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val decorView = window.decorView.also {
            val uiOptions =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            it.systemUiVisibility = uiOptions
        }
    }
}