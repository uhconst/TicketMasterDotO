package com.uhc.lib.compose.utils.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val spacing: Spacing = Spacing(),
    val iconSize: IconSize = IconSize()
)

data class Spacing(
    val xSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val xLarge: Dp = 32.dp
)

data class IconSize(
    val xSmall: Dp = 12.dp,
    val small: Dp = 16.dp,
    val medium: Dp = 24.dp,
    val large: Dp = 32.dp,
    val xLarge: Dp = 48.dp
)