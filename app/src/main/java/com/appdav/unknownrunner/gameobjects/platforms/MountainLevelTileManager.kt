package com.appdav.unknownrunner.gameobjects.platforms

import android.content.res.Resources

class MountainLevelTileManager(res: Resources, speed: Speed): TileManagerBase(res, speed) {

    override val tiles: ArrayList<Platform> = ArrayList(createTiles())

}