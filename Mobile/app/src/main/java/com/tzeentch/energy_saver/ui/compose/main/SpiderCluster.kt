package com.tzeentch.energy_saver.ui.compose.main

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.PathMeasure
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.tzeentch.energy_saver.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

// Function to draw the electricity effect
fun DrawScope.drawElectricityEffect(path: Path, progress: Float, strokeWidth: Float) {

    val pathMeasure = PathMeasure(path.asAndroidPath(), false)
    val pathLength = pathMeasure.length

    // Calculate start and end points for the line segment
    val startDistance = progress * pathLength
    val endDistance = startDistance + 50f // Adjust length of the line segment as needed

    // Extract the segment of the path
    val effectPath = Path()
    pathMeasure.getSegment(startDistance, endDistance, effectPath.asAndroidPath(), true)

    // Draw the segment of the path
    drawPath(
        path = effectPath,
        color = Color(0xFF99CCFF),
        style = Stroke(width = strokeWidth,) // Adjust for rounded ends
    )
}

fun buildLegPath(center: Offset, path: Path, angles: List<Float>, segmentLength: Float): Path {
    path.moveTo(center.x, center.y)

    var currentPoint = center
    angles.forEach { angle ->
        val nextPoint = Offset(
            currentPoint.x + (segmentLength * cos(angle)),
            currentPoint.y + (segmentLength * sin(angle))
        )
        path.lineTo(nextPoint.x, nextPoint.y)
        currentPoint = nextPoint
    }

    return path
}

@SuppressLint("RememberReturnType")
@Composable
fun SpiderCluster(legVisibility: List<Boolean>) {
    val density = LocalDensity.current
    val canvasSize = 300.dp
    val bodyRadius = with(density) { 60.dp.toPx() }
    val legSegmentLength = with(density) { 68.dp.toPx() }
    val strokeWidth = with(density) { 4.dp.toPx() }
    val tipCircleRadius = with(density) { 24.dp.toPx() } // Increased tip size

    val tipImageBitmap = ImageBitmap.imageResource(id = R.drawable.device_removebg_preview)
    val tipImageScale = tipCircleRadius / (tipImageBitmap.width / 3f)
    val tipScaleWidth = tipImageBitmap.width * tipImageScale
    val tipScaleHeight = tipImageBitmap.height * tipImageScale

    val bodyImageBitmap = ImageBitmap.imageResource(id = R.drawable.circle_removebg_preview)
    val bodyImageScale = bodyRadius / (bodyImageBitmap.width / 3.6f)
    val bodyScaleWidth = bodyImageBitmap.width * bodyImageScale
    val bodyScaleHeight = bodyImageBitmap.height * bodyImageScale

    val animatedProgress = remember { Animatable(1f) }
    val randomDelay = remember { Random.nextInt(0, 1000) }

    LaunchedEffect(key1 = "electricityAnimation") {
        delay(randomDelay.toLong())
        animatedProgress.animateTo(
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(canvasSize)) {
            val center = Offset(size.width / 2, size.height / 2)

            // Function to draw a leg with given segments and angles
            fun drawLeg(segmentAngles: List<Float>, mColor: Color = Color.Red, text: String) {
                val path = Path()
                path.moveTo(center.x, center.y)

                var currentPoint = center
                segmentAngles.forEachIndexed { index, angle ->
                    val nextPoint = Offset(
                        currentPoint.x + (legSegmentLength * cos(angle)).toFloat(),
                        currentPoint.y + (legSegmentLength * sin(angle)).toFloat()
                    )

                    // Define control points for the Bezier curve. Adjust these for smoother curves.
                    val controlPoint1 = Offset(currentPoint.x, (currentPoint.y + nextPoint.y) / 2)
                    val controlPoint2 = Offset(nextPoint.x, (currentPoint.y + nextPoint.y) / 2)

                    if (index == 0) {
                        path.quadraticBezierTo(
                            controlPoint1.x, controlPoint1.y,
                            nextPoint.x, nextPoint.y
                        )
                    } else {
                        path.cubicTo(
                            controlPoint1.x, controlPoint1.y,
                            controlPoint2.x, controlPoint2.y,
                            nextPoint.x, nextPoint.y
                        )
                    }

                    currentPoint = nextPoint
                }

                val gradient = Brush.linearGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFFFFFFFF), Color(0xFF240588), Color(0xFF76568A)),
                    start = Offset(center.x, center.y),
                    end = currentPoint,
                    tileMode = TileMode.Clamp
                )
                drawPath(
                    path = path,
                    brush = gradient,
                    style = Stroke(width = strokeWidth)
                )

                val legPath = buildLegPath(center, path, segmentAngles, legSegmentLength)
                drawElectricityEffect(legPath, animatedProgress.value, strokeWidth)

                val tipImageTopLeft = Offset(
                    currentPoint.x - tipScaleWidth / 2,
                    currentPoint.y - tipScaleHeight / 2
                )
                drawImage(
                    image = Bitmap.createScaledBitmap(
                        tipImageBitmap.asAndroidBitmap(),
                        tipScaleWidth.toInt(),
                        tipScaleHeight.toInt(),
                        false
                    ).asImageBitmap(),
                    topLeft = tipImageTopLeft
                )

                drawContext.canvas.nativeCanvas.drawText(
                    text,
                    currentPoint.x,
                    currentPoint.y + tipCircleRadius / 3, // Adjust this value to center the text vertically
                    Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = tipCircleRadius - 8.dp.toPx() // Set the text size relative to the tip circle size
                        textAlign = Paint.Align.CENTER
                    }
                )
            }

            // Draw the spider's legs
            for (i in 0 until 10) {
                if (legVisibility.getOrElse(i) { false }) {
                    val baseAngle = Math.PI / 5 * (i - 5)
                    val angles = when (i) {
                        0, 5 -> listOf(baseAngle, baseAngle)
                        9, 4 -> listOf(baseAngle, baseAngle - Math.PI / 4.3, baseAngle + Math.PI / 18, baseAngle - Math.PI / 6.3)
                        1, 6 -> listOf(baseAngle, baseAngle + Math.PI / 4.5, baseAngle - Math.PI / 18, baseAngle + Math.PI / 6.3)
                        2, 7 -> listOf(baseAngle, baseAngle + Math.PI / 20, baseAngle - Math.PI / 5, baseAngle + Math.PI / 10)
                        3 -> listOf(baseAngle, baseAngle - Math.PI / 15, baseAngle + Math.PI / 18, baseAngle - Math.PI / 10)
                        8 -> listOf(baseAngle, baseAngle - Math.PI / 15, baseAngle + Math.PI / 15, baseAngle - Math.PI / 10)
                        else -> listOf(baseAngle, baseAngle - Math.PI / 9.3, baseAngle + Math.PI / 10, baseAngle - Math.PI / 6.3)
                    }
                    drawLeg(segmentAngles = angles.map { it.toFloat() }, text = "100W")
                }
            }

            val bodyImageTopLeft = Offset(
                center.x - bodyScaleWidth / 2,
                center.y - bodyScaleHeight / 2
            )

            // Draw the spider's body
            drawImage(
                image = Bitmap.createScaledBitmap(
                    bodyImageBitmap.asAndroidBitmap(),
                    bodyScaleWidth.toInt(),
                    bodyScaleHeight.toInt(),
                    false
                ).asImageBitmap(),
                topLeft = bodyImageTopLeft
            )
        }
    }
}
