package com.appdav.unknownrunner.gameobjects.backgrounds

import android.content.res.Resources
import com.appdav.unknownrunner.R
import com.appdav.unknownrunner.gameobjects.level.Speed

class MountainBackground(res: Resources, speed: Speed) : Background(res, speed) {

    override val layers: List<Layer> = ArrayList<Layer>().apply {
        add(Layer(R.drawable.mnt_sky))
        add(Layer(R.drawable.mnt_clouds_1, 3f))
        add(Layer(R.drawable.mnt_rocks_1, 1.5f))
    }

}