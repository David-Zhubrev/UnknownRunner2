package com.appdav.unknownrunner.gameobjects.collision

class CollisionSource(val source: Collidable,
                      val verticalPosition: SourcePosition,
                      val horizontalPosition: SourcePosition,
                      val horizontalImpact: Float,
                      val verticalImpact: Float) {

    enum class SourcePosition {
        ATOP,
        ON_BOTTOM,
        ON_LEFT,
        ON_RIGHT,
        ON_CENTER;

        fun opposite(): SourcePosition {
            return when (this) {
                ATOP -> ON_BOTTOM
                ON_BOTTOM -> ATOP
                ON_LEFT -> ON_RIGHT
                ON_RIGHT -> ON_LEFT
                ON_CENTER -> ON_CENTER
            }
        }
    }

}


