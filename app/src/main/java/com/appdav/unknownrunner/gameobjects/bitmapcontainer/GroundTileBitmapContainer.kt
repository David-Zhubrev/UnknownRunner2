package com.appdav.unknownrunner.gameobjects.bitmapcontainer

import android.content.res.Resources
import android.graphics.Bitmap
import com.appdav.unknownrunner.R
import com.appdav.unknownrunner.gameobjects.platforms.Platform

class GroundTileBitmapContainer(
    res: Resources
) : BitmapContainer(res, Platform.downScaleConst) {

    init {
        if (regular == null) regular = createBitmapList(listOf(R.drawable.ground_02))
        if (leftTop == null) leftTop = createBitmapList(listOf(R.drawable.ground_04))
        if (rightTop == null) rightTop = createBitmapList(listOf(R.drawable.ground_08))
        if (leftCliff == null) leftCliff = createBitmapList(listOf(R.drawable.ground_09))
        if (rightCliff == null) rightCliff = createBitmapList(listOf(R.drawable.ground_13))
        if (centerCliff == null) centerCliff = createBitmapList(listOf(R.drawable.ground_06))
        if (barrel == null) barrel = createBitmapList(listOf(R.drawable.wooden_barrel))
        if (box == null) box = createBitmapList(listOf(R.drawable.wooden_box))


        //TODO: this uses wrong bitmaps, consider changing bitmaps or class names that use it
        if (ruins1 == null) ruins1 = createBitmapList(listOf(R.drawable.little_wreckage))
        if (ruins2 == null) ruins2 = createBitmapList(listOf(R.drawable.sign_04))
        if (grass == null) grass = createBitmapList(listOf(R.drawable.grass_02))
    }

    companion object {
        var regular: List<Bitmap>? = null
        var leftTop: List<Bitmap>? = null
        var rightTop: List<Bitmap>? = null
        var leftCliff: List<Bitmap>? = null
        var rightCliff: List<Bitmap>? = null
        var centerCliff: List<Bitmap>? = null

        var barrel: List<Bitmap>? = null
        var box: List<Bitmap>? = null
        var ruins1: List<Bitmap>? = null
        var ruins2: List<Bitmap>? = null
        var grass: List<Bitmap>? = null
    }

}