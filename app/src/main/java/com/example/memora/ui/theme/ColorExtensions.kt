package com.example.memora.ui.theme

import androidx.compose.ui.graphics.Color

fun Color.blendWith(other: Color, ratio: Float): Color {
    val inverseRatio = 1f - ratio
    return Color(
        red = (this.red * inverseRatio) + (other.red * ratio),
        green = (this.green * inverseRatio) + (other.green * ratio),
        blue = (this.blue * inverseRatio) + (other.blue * ratio),
        alpha = 1f
    )
}
