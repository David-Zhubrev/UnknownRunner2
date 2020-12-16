package com.appdav.unknownrunner.gameobjects.bitmapcontainer

import android.content.res.Resources
import android.graphics.Bitmap
import com.appdav.unknownrunner.R
import com.appdav.unknownrunner.gameobjects.characters.concrete.Golem1

class Golem1BitmapContainer(res: Resources) : BitmapContainer(res, Golem1.DOWNSCALE) {

    init {
        if (runningBitmaps == null) {
            runningBitmaps = createRunningBitmaps(false)
        }
        if (runningBitmapsMirrored == null) {
            runningBitmapsMirrored = createRunningBitmaps(true)
        }
        if (attackBitmaps == null) {
            attackBitmaps = createAttackBitmaps()
        }
        if (dyingBitmaps == null){
            dyingBitmaps = createDieBitmaps()
        }
    }


    companion object {

        var runningBitmaps: List<Bitmap>? = null
        var runningBitmapsMirrored: List<Bitmap>? = null
        var attackBitmaps: List<Bitmap>? = null
        var dyingBitmaps: List<Bitmap>? = null

        const val HITBOX_MARGIN_LEFT: Int = 20
        const val HITBOX_MARGIN_RIGHT: Int = 20
        const val HITBOX_MARGIN_BOTTOM: Int = 20
        const val HITBOX_MARGIN_TOP: Int = 20
    }

    private fun createRunningBitmaps(isMirrored: Boolean): List<Bitmap> {
        return createBitmapList(
            listOf(
                R.drawable.golem_01_walking_000,
                R.drawable.golem_01_walking_001,
                R.drawable.golem_01_walking_002,
                R.drawable.golem_01_walking_003,
                R.drawable.golem_01_walking_004,
                R.drawable.golem_01_walking_005,
                R.drawable.golem_01_walking_006,
                R.drawable.golem_01_walking_007,
                R.drawable.golem_01_walking_008,
                R.drawable.golem_01_walking_009,
                R.drawable.golem_01_walking_010,
                R.drawable.golem_01_walking_011,
                R.drawable.golem_01_walking_012,
                R.drawable.golem_01_walking_013,
                R.drawable.golem_01_walking_014,
                R.drawable.golem_01_walking_015,
                R.drawable.golem_01_walking_016,
                R.drawable.golem_01_walking_017
            ),
            false,
            isMirrored
        )
    }

    fun createAttackBitmaps(): List<Bitmap> {
        return createBitmapList(
            listOf(
                R.drawable.golem_01_attacking_000,
                R.drawable.golem_01_attacking_001,
                R.drawable.golem_01_attacking_002,
                R.drawable.golem_01_attacking_003,
                R.drawable.golem_01_attacking_004,
                R.drawable.golem_01_attacking_005,
                R.drawable.golem_01_attacking_006,
                R.drawable.golem_01_attacking_007,
                R.drawable.golem_01_attacking_008,
                R.drawable.golem_01_attacking_009,
                R.drawable.golem_01_attacking_010,
                R.drawable.golem_01_attacking_011
            ),
            isMirrored =  true
        )
    }

    fun createDieBitmaps(): List<Bitmap>{
        return createBitmapList(
            listOf(
                R.drawable.golem_01_dying_000,
                R.drawable.golem_01_dying_001,
                R.drawable.golem_01_dying_002,
                R.drawable.golem_01_dying_003,
                R.drawable.golem_01_dying_004,
                R.drawable.golem_01_dying_005,
                R.drawable.golem_01_dying_006,
                R.drawable.golem_01_dying_007,
                R.drawable.golem_01_dying_008,
                R.drawable.golem_01_dying_009,
                R.drawable.golem_01_dying_010,
                R.drawable.golem_01_dying_011,
                R.drawable.golem_01_dying_012,
                R.drawable.golem_01_dying_013,
                R.drawable.golem_01_dying_014
            ),
            isMirrored = true
        )
    }


}