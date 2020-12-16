package com.appdav.unknownrunner.gameobjects.characters.player

import com.appdav.unknownrunner.gameobjects.characters.Character
import com.appdav.unknownrunner.gameobjects.collision.CollisionSource

abstract class EnemyAi(character: Character) : PlayerBase(character){

    abstract fun onMainPlayerInSight(character: CollisionSource)
}