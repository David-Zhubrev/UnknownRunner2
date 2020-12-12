package com.appdav.unknownrunner.gameobjects.level

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.backgrounds.Background
import com.appdav.unknownrunner.gameobjects.platforms.Speed
import com.appdav.unknownrunner.gameobjects.backgrounds.MountainBackground
import com.appdav.unknownrunner.gameobjects.platforms.MountainLevelTileManager
import com.appdav.unknownrunner.gameobjects.platforms.TileManager

class MountainLevel(resources: Resources) : Level() {

    override val speed: Speed =
        Speed(15f)
    override val background: Background = MountainBackground(resources, speed)
    override val tileManager: TileManager = MountainLevelTileManager(resources, speed)


}