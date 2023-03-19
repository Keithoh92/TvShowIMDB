package com.example.moviedd.ui.common

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moviedd.ui.theme.full
import com.example.moviedd.ui.theme.pointOne
import com.example.moviedd.ui.theme.spacing4

@Composable
fun ProgressIndicator(indicatorSize: Dp) {
    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition
        .animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 600
                }
            )
        )

    CircularProgressIndicator(
        progress = full,
        modifier = Modifier
            .size(indicatorSize)
            .rotate(angle)
            .border(
                spacing4,
                brush = Brush.sweepGradient(
                    listOf(
                        Color.Red,
                        MaterialTheme.colors.primary.copy(pointOne),
                        MaterialTheme.colors.primary
                    )
                ),
                shape = CircleShape
            ),
        strokeWidth = 1.dp,
        color = Color.White
    )
}