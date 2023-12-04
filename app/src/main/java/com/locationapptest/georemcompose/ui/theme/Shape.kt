package com.locationapptest.georemcompose.ui.theme


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(24.dp)
)

val dimensions = {

    // small
    val smallPadding = 4.dp
    val smallIconSize = 12.dp

    // medium
    val mediumMargin = 16.dp
    val mediumTextSize = 24.dp

    // large
    val largePadding = 32.dp
    val largeHeaderTextSize = 24.sp

    //extraLarge
    val extraLargeImageSize = 56.dp
    val extraLargeMargin = 64.dp

}