package com.appdav.unknownrunner.gameobjects.level.concrete

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.level.LevelContentManager
import com.appdav.unknownrunner.gameobjects.level.Speed

class MountainLevelContentManager(
    res: Resources,
    speed: Speed
) : LevelContentManager(res,speed) {


    override fun createTiles() {
        TODO("Not yet implemented")
    }

    fun getTilesFromPattern(pattern: TilePattern){

    }

    class TilePattern {

    }

}