package com.example.animation_experiments.navigation.destinations.graphics

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphicsScreen(navigateHome: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Graphics") },
                navigationIcon = {
                    IconButton(onClick = navigateHome) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(top = paddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var rotateValue by remember {
                mutableFloatStateOf(0f)
            }

            val animatedRotation by animateFloatAsState(
                targetValue = rotateValue,
                animationSpec = tween(250),
                label = "x"
            )

            Canvas(modifier = Modifier
                .fillMaxSize()
                .clickable {
                    rotateValue += 45f
                }) {
                val canvasQuadrantSize = size / 8F
                drawRect(
                    topLeft = Offset(0f, 200f),
                    color = Color.Magenta,
                    size = canvasQuadrantSize
                )

                rotate(
                    degrees = animatedRotation,
                    pivot = Offset(x = size.width / 5f, y = 200f),
                ) {
                    drawRect(
                        topLeft = Offset(x = size.width / 5f, y = 200f),
                        color = Color.Green,
                        size = canvasQuadrantSize
                    )
                }

                rotate(
                    degrees = animatedRotation - 45f,
                    pivot = Offset(x = size.width / 5f, y = 200f),
                ) {
                    drawRect(
                        topLeft = Offset(x = size.width / 5f, y = 200f),
                        color = Color.Cyan,
                        size = canvasQuadrantSize
                    )
                }

                drawCircle(
                    center = Offset(x = size.width / 5f, y = 200f),
                    radius = 50f,
                    color = Color.Blue
                )

                drawRoundRect(
                    topLeft = Offset(0f, 500f),
                    color = Color.Red,
                    size = canvasQuadrantSize,
                    cornerRadius = CornerRadius(20f)
                )

                drawLine(
                    color = Color.Green,
                    strokeWidth = 5f,
                    cap = Round,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                    start = Offset(0f, 500f),
                    end = Offset(200f, 500f)
                )
                drawLine(
                    color = Color.Green,
                    strokeWidth = 5f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                    start = Offset(200f, 500f),
                    end = Offset(100f, 775f)
                )
                drawLine(
                    color = Color.Green,
                    strokeWidth = 5f,
                    cap = Round,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
                    start = Offset(100f, 775f),
                    end = Offset(0f, 500f)
                )

                drawOval(
                    topLeft = Offset(0f, 800f),
                    color = Color.Blue,
                    size = canvasQuadrantSize / 2f
                )

                drawArc(
                    topLeft = Offset(0f, 950f),
                    useCenter = true,
                    color = Color.Yellow,
                    startAngle = 0f,
                    sweepAngle = 270f,
                    size = Size(width = 250f, height = 250f),
                )

                drawArc(
                    topLeft = Offset(300f, 950f),
                    useCenter = true,
                    color = Color.Yellow,
                    startAngle = 0f,
                    sweepAngle = 270f,
                    size = Size(width = 250f, height = 250f),
                    style = Stroke(width = 5f, cap = Round)
                )

                drawPoints(
                    color = Color.Green,
                    cap = Round,
                    points = listOf(
                        Offset(300f, 950f),
                        Offset(600f, 950f),
                        Offset(900f, 950f),
                    ),
                    pointMode = PointMode.Points,
                    strokeWidth = 10f

                )

                val centerX = 150f
                val centerY = 1500f
                val radius = 100f
                val numPoints = 1000

                val boundingSquareSide =
                    2 * radius // Side length of the square that encloses the circle

                repeat(numPoints) {
                    var point: Offset
                    do {
                        // Generate a random point within the bounding square
                        val x =
                            centerX - boundingSquareSide / 2 + Random.nextFloat() * boundingSquareSide
                        val y =
                            centerY - boundingSquareSide / 2 + Random.nextFloat() * boundingSquareSide
                        point = Offset(x, y)
                    } while (!isInsideCircle(
                            point,
                            centerX,
                            centerY,
                            radius
                        )
                    ) // Reject points outside the circle

                    val color = generateRandomColor()

                    drawCircle(
                        color = color,
                        center = point,
                        radius = 1f
                    )
                }

                drawCircle(
                    color = Color.Transparent,
                    center = Offset(centerX, centerY),
                    radius = radius,
                    style = Stroke(width = 2f),
                )
            }
        }
    }
}

private fun isInsideCircle(
    point: Offset,
    centerX: Float,
    centerY: Float,
    radius: Float
): Boolean {
    val dx = point.x - centerX
    val dy = point.y - centerY
    val distance = sqrt(dx * dx + dy * dy)
    return distance <= radius
}

private fun generateRandomColor(): Color {
    val red = Random.nextFloat()
    val green = Random.nextFloat()
    val blue = Random.nextFloat()
    return Color(red, green, blue)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun GraphicsScreenPreview() {
    GraphicsScreen({})
}
