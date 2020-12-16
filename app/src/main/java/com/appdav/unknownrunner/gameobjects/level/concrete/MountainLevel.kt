package com.appdav.unknownrunner.gameobjects.level.concrete

import android.content.res.Resources
import android.util.Log
import com.appdav.unknownrunner.gameobjects.backgrounds.Background
import com.appdav.unknownrunner.gameobjects.level.Speed
import com.appdav.unknownrunner.gameobjects.backgrounds.MountainBackground
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.FallenAngel3BitmapContainer
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.Golem1BitmapContainer
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.GroundTileBitmapContainer
import com.appdav.unknownrunner.gameobjects.characters.EnemyManager
import com.appdav.unknownrunner.gameobjects.characters.concrete.MainCharacter
import com.appdav.unknownrunner.gameobjects.characters.player.Controllable
import com.appdav.unknownrunner.gameobjects.characters.player.HumanPlayer
import com.appdav.unknownrunner.gameobjects.collision.CollidableListManager
import com.appdav.unknownrunner.gameobjects.level.Level
import com.appdav.unknownrunner.gameobjects.platforms.MountainLevelTileManager
import com.appdav.unknownrunner.gameobjects.platforms.TileManager
import com.appdav.unknownrunner.tools.BitmapPreloader
import com.appdav.unknownrunner.tools.Constant

class MountainLevel(resources: Resources) : Level() {

    override val speed: Speed =
        Speed(15f)
    override val background: Background
    override val tileManager: TileManager
    override val controllable: Controllable
    override val mainCharacter: MainCharacter
    override val collidableListManager: CollidableListManager
    override val enemyManager: EnemyManager

    override var mDestroyed = true

    override fun destroy() {
        super.destroy()
    }

    init {
        BitmapPreloader(
            resources = resources,
            callback = this::startLevel,
            classList = listOf(
                FallenAngel3BitmapContainer::class,
                GroundTileBitmapContainer::class,
                Golem1BitmapContainer::class
            )
        )
        background = MountainBackground(resources, speed)
        tileManager = MountainLevelTileManager(resources, speed)
        mainCharacter = MainCharacter(
            resources,
            speed
//            FallenAngel3BitmapContainer.runningBitmaps!!,
//            FallenAngel3BitmapContainer.runningBitmaps!!,
//            FallenAngel3BitmapContainer.runningBitmaps!!
        ).apply {
            player = HumanPlayer(this).also {
                controllable = it
            }
        }
        collidableListManager = CollidableListManager(this)
        enemyManager = EnemyManager(resources, speed)
    }

    fun startLevel() {
        //TODO: add good impl
        Log.d(Constant.LOG_TAG, "Bitmap preloader finished")
        mDestroyed = false
    }


}