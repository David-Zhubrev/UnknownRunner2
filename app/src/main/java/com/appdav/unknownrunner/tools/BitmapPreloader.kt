package com.appdav.unknownrunner.tools

import android.content.res.Resources
import android.util.Log
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.BitmapContainer
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class BitmapPreloader(
    resources: Resources,
    callback: () -> Unit,
    classList: List<KClass<out BitmapContainer>>
) {

    init {
        classList.forEach {
            it.primaryConstructor?.call(resources)
            Log.d(Constant.LOG_TAG, "${it.simpleName} class has been initialized")
        }
        callback.invoke()

    }

}