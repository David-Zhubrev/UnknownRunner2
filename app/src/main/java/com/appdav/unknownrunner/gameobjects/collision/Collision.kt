package com.appdav.unknownrunner.gameobjects.collision

data class Collision(val souce: Collidable, val sourcePosition: SourcePosition) {

    enum class SourcePosition {
        ATOP,
        ON_BOTTOM,
        ON_LEFT,
        ON_RIGHT
    }

}
