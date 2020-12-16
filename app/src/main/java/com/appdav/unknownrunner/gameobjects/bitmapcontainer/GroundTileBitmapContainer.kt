package com.appdav.unknownrunner.gameobjects.bitmapcontainer

import android.content.res.Resources
import android.graphics.Bitmap
import com.appdav.unknownrunner.R
import com.appdav.unknownrunner.gameobjects.platforms.Platform
import java.lang.reflect.Constructor

class GroundTileBitmapContainer(
    res: Resources
) : BitmapContainer(res, Platform.downScaleConst) {

    init {
        if (bitmap == null) {
            bitmap = createBitmap(R.drawable.ground_02)
        }
    }

    companion object {
        var bitmap: Bitmap? = null
    }

}