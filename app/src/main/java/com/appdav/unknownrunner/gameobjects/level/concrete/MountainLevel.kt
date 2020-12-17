package com.appdav.unknownrunner.gameobjects.level.concrete

import android.content.res.Resources
import android.util.Log
import com.appdav.unknownrunner.gameobjects.backgrounds.Background
import com.appdav.unknownrunner.gameobjects.level.Speed
import com.appdav.unknownrunner.gameobjects.backgrounds.MountainBackground
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.CoinBitmapContainer
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.FallenAngel3BitmapContainer
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.Golem1BitmapContainer
import com.appdav.unknownrunner.gameobjects.bitmapcontainer.GroundTileBitmapContainer
import com.appdav.unknownrunner.gameobjects.characters.concrete.MainCharacter
import com.appdav.unknownrunner.gameobjects.characters.player.Controllable
import com.appdav.unknownrunner.gameobjects.characters.player.concrete.HumanPlayer
import com.appdav.unknownrunner.gameobjects.collision.CollidableListManager
import com.appdav.unknownrunner.gameobjects.level.Level
import com.appdav.unknownrunner.gameobjects.level.LevelContentManager
import com.appdav.unknownrunner.tools.BitmapPreloader
import com.appdav.unknownrunner.tools.Constant

class MountainLevel(
    resources: Resources,
    gameOverCallback: () -> Unit
) : Level() {

    override val speed: Speed =
        Speed(15f)
    override val background: Background
    override val controllable: Controllable
    override val mainCharacter: MainCharacter
    override val collidableListManager: CollidableListManager
    override val levelContentManager: LevelContentManager
    override var mDestroyed = true

    init {
        BitmapPreloader(
            resources = resources,
            callback = this::startLevel,
            classList = listOf(
                FallenAngel3BitmapContainer::class,
                GroundTileBitmapContainer::class,
                Golem1BitmapContainer::class,
                CoinBitmapContainer::class
            )
        )
        background = MountainBackground(resources, speed)
        mainCharacter = MainCharacter(
            resources,
            speed,
            gameOverCallback
        ).apply {
            player = HumanPlayer(this).also {
                controllable = it
            }
        }
        levelContentManager = MountainLevelContentManager(resources, speed)
        collidableListManager = CollidableListManager(this)
    }

    private fun startLevel() {
        Log.d(Constant.LOG_TAG, "Bitmap preloader finished")
        mDestroyed = false
    }


}