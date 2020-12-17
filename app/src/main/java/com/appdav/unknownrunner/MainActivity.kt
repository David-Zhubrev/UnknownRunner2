package com.appdav.unknownrunner

import android.content.SharedPreferences
import android.graphics.Point
import android.os.Bundle
import android.view.View.*
import com.appdav.unknownrunner.tools.Score
import com.appdav.unknownrunner.tools.Screen
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FullscreenActivity(), GameView.GameActivityCallback {

    private lateinit var preferences: SharedPreferences

    companion object {
        const val COINS = "coins"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        Screen.initialize(point)
        super.onCreate(savedInstanceState)
        preferences = getSharedPreferences("pref", MODE_PRIVATE)
        Score.gold = preferences.getInt(COINS, 0)
        setContentView(R.layout.activity_main)

        game_view.gameActivityCallback = this
        btn_pause.setOnClickListener {
            if (game_view.isRunning()) game_view.stop()
            else game_view.startThread()
        }
        tv_gold.text = Score.gold.toString()
    }

    override fun onGameOver() {
        runOnUiThread {
            MyDialogs.GameOverDialog(game_view::startThread).show(supportFragmentManager, "dialog")
        }
    }

    override fun onGameStart() {
        btn_pause.visibility = VISIBLE
    }

    override fun onGamePaused() {
        if (game_view.isRunning()) return
        runOnUiThread {
            btn_pause.visibility = INVISIBLE
            MyDialogs.PauseDialog(game_view::startThread).show(supportFragmentManager, "dialog")
        }
    }

    override fun onScoreUpdated() {
        runOnUiThread {
            tv_gold.text = Score.gold.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        preferences.edit().putInt(COINS, Score.gold).apply()
    }
}