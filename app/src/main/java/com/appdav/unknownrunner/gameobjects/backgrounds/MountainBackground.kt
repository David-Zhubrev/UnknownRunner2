package com.appdav.unknownrunner.gameobjects.backgrounds

import android.content.res.Resources
import com.appdav.unknownrunner.R
import com.appdav.unknownrunner.gameobjects.platforms.Speed

class MountainBackground(res: Resources, speed: Speed) : Background(res, speed) {

    override val layers: List<Layer> = ArrayList<Layer>().apply {
        add(Layer(res, R.drawable.mnt_sky))
        add(Layer(res, R.drawable.mnt_clouds_1, SpeedModifier(SpeedModifier.MODE.DIV, 3f)))
        add(Layer(res, R.drawable.mnt_rocks_1, SpeedModifier(SpeedModifier.MODE.DIV, 1.5f)))
    }

}