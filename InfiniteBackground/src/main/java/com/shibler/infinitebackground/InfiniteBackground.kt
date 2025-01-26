package com.shibler.infinitebackground

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


//FUNCTION IS LIMITED TO A 300.DP SIZE COMPOSE
@Composable
fun InfiniteTransition(modifier: Modifier = Modifier) {


    val transition = rememberInfiniteTransition(label = "")


    val height = LocalConfiguration.current.screenHeightDp.dp
    val width = LocalConfiguration.current.screenWidthDp.dp

    val density = LocalDensity.current

    val heightInpx = with(density) { height.toPx() }
    val widthInPx = with(density) { width.toPx() }


    val transitionX by transition.animateFloat(
        initialValue = 0f,
        targetValue = widthInPx,
        animationSpec = infiniteRepeatable(
            animation = tween(12000,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val transitionY by transition.animateFloat(
        initialValue = 0f,
        targetValue = - heightInpx,
        animationSpec = infiniteRepeatable(
            animation = tween(12000
                , easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Canvas(modifier = Modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(10.dp))
        .then(modifier)) {
        drawInfinitePattern(Offset(transitionX, transitionY))
    }

}

fun DrawScope.drawInfinitePattern(newOffset : Offset) {

    val shapeSize = size.width/50

    val width = size.width/13
    val height = size.height/30

    for(x in 0 until size.width.toInt() step width.toInt()) {
        for(y in 0 until size.height.toInt() step height.toInt()) {

            val nextOffset = Offset(x.toFloat() + newOffset.x , y.toFloat() + newOffset.y)

            val previousOffset = Offset(x.toFloat() - size.width + newOffset.x, y.toFloat() + newOffset.y)

            val nextOffset2 = Offset(x.toFloat() +newOffset.x, y.toFloat() + size.height + newOffset.y)

            val previousOffset2 = Offset(x.toFloat() - size.width + newOffset.x , y.toFloat() + size.height + newOffset.y)

            drawCircle(
                color = Color.White,
                center = previousOffset,
                radius = shapeSize / 2,
                style = Stroke(width = 1.dp.toPx())
            )

            drawCircle(
                color = Color.White,
                center = nextOffset,
                radius = shapeSize / 2,
                style = Stroke(width = 1.dp.toPx())
            )

            drawCircle(
                color = Color.White,
                center = previousOffset2,
                radius = shapeSize / 2,
                style = Stroke(width = 1.dp.toPx())
            )

            drawCircle(
                color = Color.White,
                center = nextOffset2,
                radius = shapeSize / 2,
                style = Stroke(width = 1.dp.toPx())
            )
        }
    }

}