package com.example.animation_experiments.navigation.destinations.graphics

import android.os.Build
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.sqrt
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
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
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = 16.dp),
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

            var activeCanvas by remember {
                mutableStateOf("pointer")
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { activeCanvas = "basic" }) {
                    Text(text = "Basic")
                }
                Button(onClick = { activeCanvas = "pointer" }) {
                    Text(text = "Pointer")
                }
            }


            when (activeCanvas) {
                "basic" -> {
                    Canvas(modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            rotateValue += 45f
                        }) {

                        val canvasQuadrantSize = size / 8F

                        rectShapes(
                            drawScope = this,
                            canvasQuadrantSize = canvasQuadrantSize,
                            size = size,
                            animatedRotation = animatedRotation
                        )

                        varyingShapes(
                            drawScope = this,
                            canvasQuadrantSize = canvasQuadrantSize
                        )

                        colorBall(this)
                    }
                }

                "pointer" -> {

                    val balls = remember { mutableStateListOf<Pair<Offset, Brush>>() }
                    val alphaValues =
                        remember { mutableStateMapOf<Offset, Animatable<Float, AnimationVector1D>>() }
                    val scope = rememberCoroutineScope()

                    fun startFadeAnimation(ball: Offset) {
                        val alpha = alphaValues[ball] ?: Animatable(0.5f).also {
                            alphaValues[ball] = it
                        }
                        alphaValues[ball] = alpha
                        scope.launch {
                            alpha.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(durationMillis = 1000)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInteropFilter {
                                if (it.action == MotionEvent.ACTION_DOWN) {
                                    val position = Offset(it.x, it.y)
                                    val gradient = generateRainbowGradient()
                                    balls.add(position to gradient)
                                    startFadeAnimation(Offset(it.x, it.y))
                                }
                                false
                            }
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDrag = { change, dragAmount ->
                                        val position = change.position
                                        val gradient = generateRainbowGradient()
                                        balls.add(position to gradient)
                                        startFadeAnimation(position)
                                    }
                                )
                            }
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            balls.forEach { (ball, gradient) ->
                                val alpha = alphaValues[ball] ?: return@forEach
                                drawCircle(
                                    brush = gradient,
                                    alpha = alpha.value,
                                    center = ball,
                                    radius = 25f
                                )
                            }
                        }
                    }
                }

                else -> {
                    Text("Something else")
                }
            }
        }
    }
}

fun rectShapes(
    drawScope: DrawScope,
    canvasQuadrantSize: Size,
    size: Size,
    animatedRotation: Float
) {
    drawScope.drawRect(
        topLeft = Offset(0f, 200f),
        color = Color.Magenta,
        size = canvasQuadrantSize
    )

    drawScope.rotate(
        degrees = animatedRotation,
        pivot = Offset(x = size.width / 5f, y = 200f),
    ) {
        drawRect(
            topLeft = Offset(x = size.width / 5f, y = 200f),
            color = Color.Green,
            size = canvasQuadrantSize
        )
    }

    drawScope.rotate(
        degrees = animatedRotation - 45f,
        pivot = Offset(x = size.width / 5f, y = 200f),
    ) {
        drawRect(
            topLeft = Offset(x = size.width / 5f, y = 200f),
            color = Color.Cyan,
            size = canvasQuadrantSize
        )
    }

    drawScope.drawCircle(
        center = Offset(x = size.width / 5f, y = 200f),
        radius = 50f,
        color = Color.Blue
    )

    drawScope.drawRoundRect(
        topLeft = Offset(0f, 500f),
        color = Color.Red,
        size = canvasQuadrantSize,
        cornerRadius = CornerRadius(20f)
    )
}

fun varyingShapes(drawScope: DrawScope, canvasQuadrantSize: Size) {
    drawScope.drawLine(
        color = Color.Green,
        strokeWidth = 5f,
        cap = Round,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
        start = Offset(0f, 500f),
        end = Offset(200f, 500f)
    )
    drawScope.drawLine(
        color = Color.Green,
        strokeWidth = 5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
        start = Offset(200f, 500f),
        end = Offset(100f, 775f)
    )
    drawScope.drawLine(
        color = Color.Green,
        strokeWidth = 5f,
        cap = Round,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
        start = Offset(100f, 775f),
        end = Offset(0f, 500f)
    )

    drawScope.drawOval(
        topLeft = Offset(0f, 800f),
        color = Color.Blue,
        size = canvasQuadrantSize / 2f
    )

    drawScope.drawArc(
        topLeft = Offset(0f, 950f),
        useCenter = true,
        color = Color.Yellow,
        startAngle = 0f,
        sweepAngle = 270f,
        size = Size(width = 250f, height = 250f),
    )

    drawScope.drawArc(
        topLeft = Offset(300f, 950f),
        useCenter = true,
        color = Color.Yellow,
        startAngle = 0f,
        sweepAngle = 270f,
        size = Size(width = 250f, height = 250f),
        style = Stroke(width = 5f, cap = Round)
    )

    drawScope.drawPoints(
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
}

fun colorBall(drawScope: DrawScope) {
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

        drawScope.drawCircle(
            color = color,
            center = point,
            radius = 1f
        )
    }

    drawScope.drawCircle(
        color = Color.Transparent,
        center = Offset(centerX, centerY),
        radius = radius,
        style = Stroke(width = 2f),
    )
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

fun generateRBGGradient(): Brush {
    val colors = listOf(Color.Red, Color.Green, Color.Blue)
    return Brush.linearGradient(colors)
}

fun generateRainbowGradient(): Brush {
    val colors = listOf(
        Color(0xFFFF0000),  // Red
        Color(0xFFFF7F00),  // Orange
        Color(0xFFFFFF00),  // Yellow
        Color(0xFF7FFF00),  // Chartreuse
        Color(0xFF00FF00),  // Green
        Color(0xFF00FF7F),  // Spring Green
        Color(0xFF00FFFF),  // Cyan
        Color(0xFF007FFF),  // Azure
        Color(0xFF0000FF),  // Blue
        Color(0xFF8A2BE2),  // Blue Violet
        Color(0xFFFF00FF),  // Magenta
        Color(0xFFFF007F)   // Rose
    )
    return Brush.linearGradient(colors)
}

fun generateRandomGradient(): Brush {
    val colors = List(3) { Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat()) }
    return Brush.linearGradient(colors)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun GraphicsScreenPreview() {
    GraphicsScreen(navigateHome = {})
}
