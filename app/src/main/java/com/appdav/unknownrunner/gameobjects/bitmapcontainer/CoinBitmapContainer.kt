package com.appdav.unknownrunner.gameobjects.bitmapcontainer

import android.content.res.Resources
import android.graphics.Bitmap
import com.appdav.unknownrunner.R

class CoinBitmapContainer(res: Resources) : BitmapContainer(res, COIN_DOWN_SCALE) {

    init {
        if (idleBitmaps == null) {
            idleBitmaps = createIdleBitmaps()
        }
    }

    companion object {
        var idleBitmaps: List<Bitmap>? = null
        const val COIN_DOWN_SCALE = 4f
    }

    private fun createIdleBitmaps(): List<Bitmap> {
        return createBitmapList(
            listOf(
                R.drawable.coin_01,
                R.drawable.coin_02,
                R.drawable.coin_03,
                R.drawable.coin_04,
                R.drawable.coin_05,
                R.drawable.coin_06
            )
        )
    }
}