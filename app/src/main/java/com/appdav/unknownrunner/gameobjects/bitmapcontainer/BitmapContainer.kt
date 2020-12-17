package com.appdav.unknownrunner.gameobjects.bitmapcontainer

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.annotation.DrawableRes
import com.appdav.unknownrunner.tools.Screen

//This class implements basic methods for fast creation of bitmaps
// All classes that inherit this class should also implement their own companion object containing list of preloaded bitmaps
// Also those classes should be constructed at least once during loading
abstract class BitmapContainer(
    val res: Resources,
    private val downScale: Float
) {
    private fun createBitmap(
        @DrawableRes resId: Int,
        useFilter: Boolean = false,
        isMirrored: Boolean = false
    ): Bitmap {
        var tmp = BitmapFactory.decodeResource(res, resId)
        if (isMirrored) tmp = tmp.let {
            val matrix = Matrix().apply {
                setScale(-1f, 1f)
                postTranslate(it.width.toFloat(), 0f)
            }
            Bitmap.createBitmap(it, 0, 0, it.width, it.height, matrix, useFilter)
        }
        return Bitmap.createScaledBitmap(
            tmp,
            (tmp.width / downScale * Screen.ratioX).toInt(),
            (tmp.height / downScale * Screen.ratioY).toInt(),
            useFilter
        )
    }


    protected fun createBitmapList(
        resIds: List<Int>,
        useFilter: Boolean = false,
        isMirrored: Boolean = false
    ): List<Bitmap> = ArrayList<Bitmap>().apply {
        resIds.forEach {
            this.add(createBitmap(it, useFilter, isMirrored))
        }
    }

}