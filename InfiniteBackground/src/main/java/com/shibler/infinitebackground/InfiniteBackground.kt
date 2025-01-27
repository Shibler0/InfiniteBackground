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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp


fun Modifier.spaceIt(shapeSize : Float = 10f): Modifier = composed {

    val transition = rememberInfiniteTransition(label = "")


    val height = LocalConfiguration.current.screenHeightDp.dp
    val width = LocalConfiguration.current.screenWidthDp.dp

    val density = LocalDensity.current

    val heightInpx = with(density) { height.toPx() }
    val widthInPx = with(density) { width.toPx() }

    var composableSize by remember { mutableStateOf(IntSize.Zero) }

    val transitionX by transition.animateFloat(
        initialValue = 0f,
        targetValue = with(LocalDensity.current) { composableSize.width.toDp().toPx() },
        animationSpec = infiniteRepeatable(
            animation = tween(12000,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val transitionY by transition.animateFloat(
        initialValue = 0f,
        targetValue = - with(LocalDensity.current) { composableSize.height.toDp().toPx() },
        animationSpec = infiniteRepeatable(
            animation = tween(12000
                , easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    this
        .onSizeChanged {
            composableSize = it
        }
        .drawBehind {
            drawInfinitePattern(Offset(transitionX, transitionY), shapeSize)
        }
}

//FUNCTION IS LIMITED TO A 300.DP SIZE COMPOSE
@Composable
fun InfiniteTransition(modifier: Modifier = Modifier, shapeSize : Float = 10f) {


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
        drawInfinitePattern(Offset(transitionX, transitionY), shapeSize)
    }

}

fun DrawScope.drawInfinitePattern(newOffset : Offset, shapeSize:Float) {

    val shapeSize = size.width / shapeSize
    val spacing = shapeSize * 1.2f

    val wrappedOffsetX = newOffset.x % spacing
    val wrappedOffsetY = newOffset.y % spacing

    var x = -spacing + wrappedOffsetX
    while (x < size.width + spacing) {
        var y = -spacing + wrappedOffsetY
        while (y < size.height + spacing) {
            drawCircle(
                color = Color.White,
                center = Offset(x, y),
                radius = shapeSize * 0.4f,
                style = Stroke(width = 1.dp.toPx())
            )
            y += spacing
        }
        x += spacing
    }

}