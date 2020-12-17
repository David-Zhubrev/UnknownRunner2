package com.appdav.unknownrunner.gameobjects.bitmapcontainer

import android.content.res.Resources
import android.graphics.Bitmap
import com.appdav.unknownrunner.R
import com.appdav.unknownrunner.gameobjects.characters.concrete.MainCharacter

class FallenAngel3BitmapContainer(
    res: Resources
) : BitmapContainer(res, MainCharacter.downScaleConst) {

    init {
        if (runningBitmaps == null) {
            runningBitmaps = createRunningBitmaps()
        }
        if (airborneBitmaps == null) {
            airborneBitmaps = createAirborneBitmaps()
        }
        if (jumpStartBitmaps == null)
            jumpStartBitmaps = createJumpStartBitmaps()
        if (attackBitmaps == null)
            attackBitmaps = createAttackBitmaps()
    }

    companion object {
        var runningBitmaps: List<Bitmap>? = null
        var airborneBitmaps: List<Bitmap>? = null
        var jumpStartBitmaps: List<Bitmap>? = null
        var attackBitmaps: List<Bitmap>? = null

        const val HITBOX_MARGIN_LEFT: Int = 30
        const val HITBOX_MARGIN_RIGHT: Int = 30
        const val HITBOX_MARGIN_BOTTOM: Int = 30
        const val HITBOX_MARGIN_TOP: Int = 30
    }

    private fun createAirborneBitmaps(): List<Bitmap> {
        return createBitmapList(
            listOf(
                R.drawable.fallen_angels_jump_loop_000,
                R.drawable.fallen_angels_jump_loop_001,
                R.drawable.fallen_angels_jump_loop_002,
                R.drawable.fallen_angels_jump_loop_003,
                R.drawable.fallen_angels_jump_loop_004,
                R.drawable.fallen_angels_jump_loop_005
            )
        )
    }

    private fun createRunningBitmaps(): List<Bitmap> {
        return createBitmapList(
            listOf(
                R.drawable.fallen_angels_running_000,
                R.drawable.fallen_angels_running_001,
                R.drawable.fallen_angels_running_002,
                R.drawable.fallen_angels_running_003,
                R.drawable.fallen_angels_running_004,
                R.drawable.fallen_angels_running_005,
                R.drawable.fallen_angels_running_006,
                R.drawable.fallen_angels_running_007,
                R.drawable.fallen_angels_running_008,
                R.drawable.fallen_angels_running_009,
                R.drawable.fallen_angels_running_010,
                R.drawable.fallen_angels_running_011
            )
        )
    }

    private fun createJumpStartBitmaps(): List<Bitmap> {
        return createBitmapList(
            listOf(
                R.drawable.fallen_angels_jump_loop_000,
                R.drawable.fallen_angels_jump_loop_001,
                R.drawable.fallen_angels_jump_loop_002,
                R.drawable.fallen_angels_jump_loop_003,
                R.drawable.fallen_angels_jump_loop_004,
                R.drawable.fallen_angels_jump_loop_005
            )
        )
    }

    private fun createAttackBitmaps(): List<Bitmap> {
        return createBitmapList(
            listOf(
                R.drawable.fallen_angels_run_slashing_000,
                R.drawable.fallen_angels_run_slashing_000,
                R.drawable.fallen_angels_run_slashing_001,
                R.drawable.fallen_angels_run_slashing_001,
                R.drawable.fallen_angels_run_slashing_002,
                R.drawable.fallen_angels_run_slashing_002,
                R.drawable.fallen_angels_run_slashing_003,
                R.drawable.fallen_angels_run_slashing_003,
                R.drawable.fallen_angels_run_slashing_004,
                R.drawable.fallen_angels_run_slashing_004,
                R.drawable.fallen_angels_run_slashing_005,
                R.drawable.fallen_angels_run_slashing_005,
                R.drawable.fallen_angels_run_slashing_006,
                R.drawable.fallen_angels_run_slashing_006,
                R.drawable.fallen_angels_run_slashing_007,
                R.drawable.fallen_angels_run_slashing_007,
                R.drawable.fallen_angels_run_slashing_008,
                R.drawable.fallen_angels_run_slashing_008,
                R.drawable.fallen_angels_run_slashing_009,
                R.drawable.fallen_angels_run_slashing_009,
                R.drawable.fallen_angels_run_slashing_010,
                R.drawable.fallen_angels_run_slashing_010,
                R.drawable.fallen_angels_run_slashing_011,
                R.drawable.fallen_angels_run_slashing_011
            )
        )
    }

}