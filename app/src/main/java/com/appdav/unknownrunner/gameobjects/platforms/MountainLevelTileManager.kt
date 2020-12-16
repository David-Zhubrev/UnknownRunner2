package com.appdav.unknownrunner.gameobjects.platforms

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.level.Speed

class MountainLevelTileManager(res: Resources, speed: Speed): TileManagerBase(res, speed) {

    override val tiles: ArrayList<Platform> = ArrayList(createTiles())

}