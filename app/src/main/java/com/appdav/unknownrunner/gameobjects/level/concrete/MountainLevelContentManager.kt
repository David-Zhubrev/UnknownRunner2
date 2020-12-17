package com.appdav.unknownrunner.gameobjects.level.concrete

import android.content.res.Resources
import com.appdav.unknownrunner.gameobjects.characters.concrete.Golem1
import com.appdav.unknownrunner.gameobjects.collectables.Coin
import com.appdav.unknownrunner.gameobjects.level.LevelContentManager
import com.appdav.unknownrunner.gameobjects.level.Speed
import com.appdav.unknownrunner.gameobjects.level.concrete.MountainLevelContentManager.Type.*
import com.appdav.unknownrunner.gameobjects.platforms.Platform
import com.appdav.unknownrunner.gameobjects.platforms.concrete.*
import com.appdav.unknownrunner.tools.Screen
import kotlin.properties.Delegates
import kotlin.random.Random

class MountainLevelContentManager(
    res: Resources,
    speed: Speed
) : LevelContentManager(res, speed) {

    companion object {
        var platformWidth by Delegates.notNull<Float>()
        var platformHeight by Delegates.notNull<Float>()
        var platformBottomY by Delegates.notNull<Float>()
    }

    init {
        val tile = GroundTile(res, speed)
        platformWidth = tile.width.toFloat()
        platformHeight = tile.height.toFloat()
        platformBottomY = (Screen.screenHeigth - tile.height).toFloat()
        createTiles(0f)
    }

    override fun createTiles(initialX: Float) {
        if (tiles.isEmpty()) {
            addTiles(ArrayList<Type>().apply {
                repeat(15) { add(GROUND_BOTTOM) }
            }, 0f)
        } else {
            choosePatternAndAddTiles(Random.nextInt(10), initialX)
        }
    }

    private fun addTiles(pattern: List<Type>, initialX: Float) {
        var currentX = initialX
        for (type in pattern) {
            val platforms = when (type) {
                GROUND_BOTTOM ->
                    createGroundBottom(currentX).also { currentX += platformWidth }
                GROUND_BOTTOM_WITH_COLLECTIBLE ->
                    createGroundBottom(currentX).apply {
                        addCollectible(currentX, platformBottomY)
                        currentX += platformWidth
                    }
                LEFT_GROUND_BOTTOM ->
                    createLeftGroundBottom(currentX).also { currentX += platformWidth }
                LEFT_GROUND_BOTTOM_WITH_COLLECTIBLE -> createLeftGroundBottom(currentX).apply {
                    addCollectible(currentX, platformBottomY)
                    currentX += platformWidth
                }
                RIGHT_GROUND_BOTTOM ->
                    createRightGroundBottom(currentX).also { currentX += platformWidth }
                RIGHT_GROUND_BOTTOM_WITH_COLLECTIBLE -> {
                    createRightGroundBottom(currentX).apply {
                        addCollectible(currentX, platformBottomY)
                        currentX += platformWidth
                    }
                }
                GROUND_MEDIUM ->
                    createGroundMedium(currentX).also { currentX += platformWidth }
                GROUND_MEDIUM_WITH_COLLECTIBLE -> createGroundMedium(currentX).apply {
                    collectibles.add(Coin(res, speed).also {
                        addCollectible(currentX, platformBottomY - platformHeight)
                        currentX += platformWidth
                    })
                }
                LEFT_GROUND_MEDIUM ->
                    createLeftGroundMedium(currentX).also { currentX += platformWidth }
                LEFT_GROUND_MEDIUM_WITH_COLLECTIBLE -> createLeftGroundMedium(currentX).apply {
                    addCollectible(currentX, platformBottomY - platformHeight)
                    currentX += platformWidth
                }
                RIGHT_GROUND_MEDIUM ->
                    createRightGroundMedium(currentX).also { currentX += platformWidth }
                RIGHT_GROUND_MEDIUM_WITH_COLLECTIBLE -> createRightGroundMedium(currentX).apply {
                    addCollectible(currentX, platformBottomY - platformHeight)
                    currentX += platformWidth
                }
                LEFT_GROUND_HIGH ->
                    createLeftGroundHigh(currentX).also { currentX += platformWidth }
                LEFT_GROUND_HIGH_WITH_COLLECTIBLE -> createLeftGroundHigh(currentX).apply {
                    addCollectible(currentX, platformBottomY - platformHeight * 2)
                    currentX += platformWidth
                }
                RIGHT_GROUND_HIGH ->
                    createRightGroundHigh(currentX).also { currentX += platformWidth }
                RIGHT_GROUND_HIGH_WITH_COLLECTIBLE -> createRightGroundHigh(currentX).apply {
                    addCollectible(currentX, platformBottomY - platformHeight * 2)
                    currentX += platformWidth
                }
                GROUND_HIGH ->
                    createGroundHigh(currentX).also { currentX += platformWidth }
                GROUND_HIGH_WITH_COLLECTIBLE -> createGroundHigh(currentX).apply {
                    addCollectible(currentX, platformBottomY - platformHeight * 2)
                    currentX += platformWidth
                }
                GAP -> {
                    ArrayList<Platform>().apply {
                        repeat(2) {
                            addAll(createGap(currentX).also { currentX += platformWidth })
                        }
                    }
                }
                GROUND_BOTTOM_WITH_ENEMY -> {
                    createGroundBottom(currentX).apply {
                        addEnemy(currentX, platformBottomY)
                        currentX += platformWidth
                    }
                }
                GROUND_MEDIUM_WITH_ENEMY -> {
                    createGroundMedium(currentX).apply {
                        addEnemy(currentX, platformBottomY - platformHeight * 2)
                        currentX += platformWidth
                    }
                }
                GROUND_HIGH_WITH_ENEMY -> createGroundHigh(currentX).apply {
                    addEnemy(currentX, platformBottomY - platformHeight * 3)
                    currentX += platformWidth
                }

            }
            val decorators = ArrayList<Platform>()
            platforms.forEach {
                if (it is GroundTile ||
                    it is LeftTopTile ||
                    it is RightTopTile
                ) {
                    val decorator = when (Random.nextInt(40)) {
                        5 -> Box(res, speed)
                        6 -> Barrel(res, speed)
                        7 -> Ruins1(res, speed)
                        8 -> Ruins2(res, speed)
                        else -> Grass(res, speed)
                    }
                    decorators.add(decorator.apply {
                        x = it.x
                        y = it.y - height
                    })
                }
            }
            tiles.addAll(platforms)
            tiles.addAll(decorators)
        }
    }

    private fun choosePatternAndAddTiles(number: Int, initialX: Float) {
        val nextPattern = when (number) {
            0 -> createPattern1()
            2 -> createPattern2()
            4 -> createPattern3()
            6 -> createPattern4()
            8 -> createPattern5()
            10 -> createPattern6()
            else -> createPattern0()
        }
        addTiles(nextPattern, initialX)
    }

    private fun addEnemy(platformX: Float, platformY: Float) {
        enemies.add(Golem1(res, speed).apply {
            x = platformX
            y = platformY - this.height
        })
    }

    private fun addCollectible(platformX: Float, platformY: Float) {
        collectibles.add(Coin(res, speed).apply {
            x = platformX
            y = platformY - height
        })
    }

    private fun createGroundBottom(currentX: Float) =
        listOf(GroundTile(res, speed).apply { x = currentX; y = platformBottomY })

    private fun createLeftGroundBottom(currentX: Float) =
        listOf(LeftTopTile(res, speed).apply { x = currentX; y = platformBottomY })

    private fun createRightGroundBottom(currentX: Float) =
        listOf(RightTopTile(res, speed).apply { x = currentX; y = platformBottomY })

    private fun createGroundMedium(currentX: Float) = listOf(
        GroundTile(res, speed).apply {
            x = currentX
            y = platformBottomY - height
        },
        CenterCliffTile(res, speed).apply {
            x = currentX
            y = platformBottomY
        }
    )

    private fun createLeftGroundMedium(currentX: Float) = listOf(
        LeftTopTile(res, speed).apply {
            x = currentX
            y = platformBottomY - height
        },
        LeftCliffTile(res, speed).apply {
            x = currentX
            y = platformBottomY
        }
    )

    private fun createRightGroundMedium(currentX: Float) = listOf(
        RightTopTile(res, speed).apply {
            x = currentX
            y = platformBottomY - height
        },
        RightCliffTile(res, speed).apply {
            x = currentX
            y = platformBottomY
        }
    )

    private fun createGroundHigh(currentX: Float) = listOf(
        GroundTile(res, speed).apply {
            x = currentX
            y = platformBottomY - height * 2
        },
        CenterCliffTile(res, speed).apply {
            x = currentX
            y = platformBottomY - height
        },
        CenterCliffTile(res, speed).apply {
            x = currentX
            y = platformBottomY
        }
    )

    private fun createLeftGroundHigh(currentX: Float) = listOf(
        LeftTopTile(res, speed).apply {
            x = currentX
            y = platformBottomY - height * 2
        },
        LeftCliffTile(res, speed).apply {
            x = currentX
            y = platformBottomY - height
        },
        LeftCliffTile(res, speed).apply {
            x = currentX
            y = platformBottomY
        }
    )

    private fun createRightGroundHigh(currentX: Float) = listOf(
        RightTopTile(res, speed).apply {
            x = currentX
            y = platformBottomY - height * 2
        },
        RightCliffTile(res, speed).apply {
            x = currentX
            y = platformBottomY - height
        },
        RightCliffTile(res, speed).apply {
            x = currentX
            y = platformBottomY
        }
    )

    private fun createGap(currentX: Float) =
        listOf(Gap(res, speed).apply { x = currentX; y = platformBottomY })

    private fun createPattern0(): List<Type> {
        return ArrayList<Type>().apply {
            repeat(5) { add(GROUND_BOTTOM) }
            add(GROUND_BOTTOM_WITH_ENEMY)
            repeat(3) { add(GROUND_BOTTOM_WITH_COLLECTIBLE) }
            add(GROUND_BOTTOM_WITH_ENEMY)
            repeat(5) { add(GROUND_BOTTOM) }
        }
    }

    private fun createPattern1(): List<Type> {
        return listOf(
            GROUND_BOTTOM,
            RIGHT_GROUND_BOTTOM,
            GAP,
            GAP,
            LEFT_GROUND_BOTTOM_WITH_COLLECTIBLE,
            GROUND_BOTTOM,
            GROUND_BOTTOM_WITH_ENEMY,
            GROUND_BOTTOM,
            RIGHT_GROUND_BOTTOM,
            GAP,
            GAP,
            LEFT_GROUND_BOTTOM,
            GROUND_BOTTOM
        )
    }

    private fun createPattern2(): List<Type> {
        return listOf(
            GROUND_BOTTOM,
            GROUND_BOTTOM,
            RIGHT_GROUND_BOTTOM,
            GAP,
            GAP,
            LEFT_GROUND_MEDIUM_WITH_COLLECTIBLE,
            RIGHT_GROUND_MEDIUM_WITH_COLLECTIBLE,
            GAP,
            GAP,
            LEFT_GROUND_HIGH_WITH_COLLECTIBLE,
            RIGHT_GROUND_HIGH_WITH_COLLECTIBLE,
            GAP,
            GAP,
            LEFT_GROUND_BOTTOM,
            GROUND_BOTTOM_WITH_ENEMY
        )
    }

    private fun createPattern3(): List<Type> {
        return listOf(
            GROUND_BOTTOM,
            GROUND_BOTTOM_WITH_ENEMY,
            RIGHT_GROUND_BOTTOM,
            GAP,
            GAP,
            LEFT_GROUND_BOTTOM,
            RIGHT_GROUND_BOTTOM,
            GAP,
            LEFT_GROUND_HIGH,
            RIGHT_GROUND_HIGH,
            GAP,
            LEFT_GROUND_BOTTOM,
            GROUND_BOTTOM_WITH_ENEMY,
            RIGHT_GROUND_BOTTOM,
            GAP,
            LEFT_GROUND_HIGH,
            RIGHT_GROUND_HIGH,
            GAP,
            LEFT_GROUND_BOTTOM,
            GROUND_BOTTOM,
            GROUND_BOTTOM,
            GROUND_BOTTOM
        )
    }

    private fun createPattern4(): List<Type> {
        return listOf(
            RIGHT_GROUND_BOTTOM,
            GROUND_BOTTOM,
            GROUND_BOTTOM_WITH_COLLECTIBLE,
            GROUND_BOTTOM_WITH_ENEMY,
            GROUND_BOTTOM_WITH_COLLECTIBLE,
            GROUND_BOTTOM_WITH_COLLECTIBLE,
            GROUND_BOTTOM_WITH_ENEMY,
            GROUND_BOTTOM_WITH_COLLECTIBLE,
            GROUND_BOTTOM_WITH_COLLECTIBLE,
            GROUND_BOTTOM_WITH_ENEMY,
            GROUND_BOTTOM_WITH_COLLECTIBLE,
            GROUND_BOTTOM
        )
    }

    private fun createPattern5(): List<Type> {
        return listOf(
            GROUND_BOTTOM,
            RIGHT_GROUND_BOTTOM,
            LEFT_GROUND_MEDIUM,
            GROUND_MEDIUM,
            RIGHT_GROUND_MEDIUM,
            LEFT_GROUND_HIGH,
            GROUND_HIGH,
            RIGHT_GROUND_HIGH,
            GAP,
            LEFT_GROUND_HIGH_WITH_COLLECTIBLE,
            RIGHT_GROUND_HIGH_WITH_COLLECTIBLE,
            GAP,
            LEFT_GROUND_HIGH,
            RIGHT_GROUND_HIGH,
            LEFT_GROUND_MEDIUM,
            RIGHT_GROUND_MEDIUM,
            LEFT_GROUND_BOTTOM,
            GROUND_BOTTOM,
            GROUND_BOTTOM
        )
    }

    private fun createPattern6(): List<Type> {
        return listOf(
            GROUND_BOTTOM,
            RIGHT_GROUND_BOTTOM,
            GAP,
            LEFT_GROUND_MEDIUM_WITH_COLLECTIBLE,
            LEFT_GROUND_HIGH_WITH_COLLECTIBLE,
            GROUND_HIGH_WITH_COLLECTIBLE,
            RIGHT_GROUND_HIGH_WITH_COLLECTIBLE,
            RIGHT_GROUND_MEDIUM_WITH_COLLECTIBLE,
            GAP,
            LEFT_GROUND_BOTTOM,
            GROUND_BOTTOM
        )
    }

    enum class Type {
        GROUND_BOTTOM,
        GROUND_BOTTOM_WITH_COLLECTIBLE,
        GROUND_BOTTOM_WITH_ENEMY,
        LEFT_GROUND_BOTTOM,
        LEFT_GROUND_BOTTOM_WITH_COLLECTIBLE,
        RIGHT_GROUND_BOTTOM,
        RIGHT_GROUND_BOTTOM_WITH_COLLECTIBLE,
        GROUND_MEDIUM,
        GROUND_MEDIUM_WITH_COLLECTIBLE,
        GROUND_MEDIUM_WITH_ENEMY,
        LEFT_GROUND_MEDIUM,
        LEFT_GROUND_MEDIUM_WITH_COLLECTIBLE,
        RIGHT_GROUND_MEDIUM,
        RIGHT_GROUND_MEDIUM_WITH_COLLECTIBLE,
        LEFT_GROUND_HIGH,
        LEFT_GROUND_HIGH_WITH_COLLECTIBLE,
        RIGHT_GROUND_HIGH,
        RIGHT_GROUND_HIGH_WITH_COLLECTIBLE,
        GROUND_HIGH,
        GROUND_HIGH_WITH_COLLECTIBLE,
        GROUND_HIGH_WITH_ENEMY,
        GAP
    }

}

