package com.example.animation_experiments.navigation.destinations.lines

import android.graphics.PointF
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import java.math.BigDecimal
import java.time.LocalDate
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.drawscope.clipRect
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinesScreen(navigateHome: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lines") },
                navigationIcon = {
                    IconButton(onClick = navigateHome) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->

        var graphData by remember { mutableStateOf(randomizeGraphData()) }

        val animationProgress = remember {
            Animatable(0f)
        }

        LaunchedEffect(key1 = graphData) {
            animationProgress.snapTo(0f)
            animationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(1500, easing = EaseIn)
            )

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(top = paddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
            ) {

                GraphCanvas(
                    graphData = graphData,
                    animationProgress = animationProgress,
                    isSharp = false
                )

                GraphCanvas(
                    graphData = graphData,
                    animationProgress = animationProgress,
                    isSharp = true
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        graphData = randomizeGraphData()
                    }) {
                        Text("Randomize")
                    }
                }
            }
        }
    }
}

@Composable
fun GraphCanvas(
    graphData: List<Balance>,
    animationProgress: Animatable<Float, AnimationVector1D>,
    isSharp: Boolean
) {
    Canvas(
        modifier = Modifier
            .padding(8.dp)
            .aspectRatio(3 / 2f)
            .fillMaxSize()
            .drawWithCache {
                val path = if (!isSharp) {
                    generateSmoothPath(
                        graphData,
                        size
                    )
                } else {
                    generatePath(
                        graphData,
                        size
                    )
                }
                val filledPath = Path()
                filledPath.addPath(path)
                filledPath.lineTo(size.width, size.height)
                filledPath.lineTo(0f, size.height)
                filledPath.close()

                val brush = Brush.verticalGradient(
                    listOf(
                        Color.Green.copy(alpha = 0.4f),
                        Color.Transparent
                    )
                )

                onDrawBehind {
                    clipRect(right = size.width * animationProgress.value) {
                        drawPath(
                            path = path,
                            color = Color.Green,
                            style = Stroke(2.dp.toPx())
                        )

                        drawPath(
                            path = filledPath,
                            brush = brush,
                            style = Fill
                        )
                    }
                }

            }
    ) {
        val barWidthPx = 1.dp.toPx()

        // Frame around the graph
        drawRect(
            color = Color.LightGray,
            style = Stroke(barWidthPx)
        )

        drawLines(
            size = size,
            drawScope = this@Canvas,
            strokeWidth = barWidthPx
        )
    }

}

fun drawLines(
    size: Size,
    drawScope: DrawScope,
    strokeWidth: Float,
    verticalLines: Int = 4,
    horizontalLines: Int = 3,
) {

    // Vertical lines drawing
    val verticalSize = (size.width / verticalLines + 1)
    repeat(verticalLines) { i ->
        val startX = verticalSize * (i + 1)
        drawScope.drawLine(
            color = Color.LightGray,
            start = Offset(startX, 0f),
            end = Offset(startX, size.height),
            strokeWidth = strokeWidth
        )
    }

    // Horizontal lines drawing
    val horizontalSize = (size.height / (horizontalLines + 1))
    repeat(horizontalLines) { i ->
        val startY = horizontalSize * (i + 1)
        drawScope.drawLine(
            color = Color.LightGray,
            start = Offset(0f, startY),
            end = Offset(size.width, startY),
            strokeWidth = strokeWidth
        )
    }
}

fun generatePath(data: List<Balance>, size: Size): Path {
    val path = Path()
    val numberEntries = data.size - 1
    val weekWidth = size.width / numberEntries

    val max = data.maxBy { it.amount }
    val min = data.minBy { it.amount } // will map to x= 0, y = height
    val range = max.amount - min.amount
    val heightPxPerAmount = size.height / range.toFloat()

    data.forEachIndexed { i, balance ->
        if (i == 0) {
            path.moveTo(
                0f,
                size.height - (balance.amount - min.amount).toFloat() *
                        heightPxPerAmount
            )
        }
        val balanceX = i * weekWidth
        val balanceY = size.height - (balance.amount - min.amount).toFloat() *
                heightPxPerAmount
        path.lineTo(balanceX, balanceY)
    }
    return path
}

fun generateSmoothPath(data: List<Balance>, size: Size): Path {
    val path = Path()
    val numberEntries = data.size - 1
    val weekWidth = size.width / numberEntries

    val max = data.maxBy { it.amount }
    val min = data.minBy { it.amount } // will map to x= 0, y = height
    val range = max.amount - min.amount
    val heightPxPerAmount = size.height / range.toFloat()

    var previousBalanceX = 0f
    var previousBalanceY = size.height
    data.forEachIndexed { i, balance ->
        if (i == 0) {
            path.moveTo(
                0f,
                size.height - (balance.amount - min.amount).toFloat() *
                        heightPxPerAmount
            )

        }

        val balanceX = i * weekWidth
        val balanceY = size.height - (balance.amount - min.amount).toFloat() *
                heightPxPerAmount
        // to do smooth curve graph - we use cubicTo, uncomment section below for non-curve
        val controlPoint1 = PointF((balanceX + previousBalanceX) / 2f, previousBalanceY)
        val controlPoint2 = PointF((balanceX + previousBalanceX) / 2f, balanceY)
        path.cubicTo(
            controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y,
            balanceX, balanceY
        )

        previousBalanceX = balanceX
        previousBalanceY = balanceY
    }
    return path
}

data class Balance(val date: LocalDate, val amount: BigDecimal)


@RequiresApi(Build.VERSION_CODES.O)
fun randomizeGraphData(): List<Balance> {
    val startDate = LocalDate.now()
    val endDate = startDate.plusWeeks(13) // Adjust this to the desired end date range

    return (0 until 14).map { // Adjust the range based on the number of elements in the graphData
        val randomDate = generateRandomDate(startDate, endDate)
        val randomAmount = generateRandomAmount()
        Balance(randomDate, randomAmount)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateRandomDate(startDate: LocalDate, endDate: LocalDate): LocalDate {
    val days = Random.nextLong(startDate.toEpochDay(), endDate.toEpochDay())
    return LocalDate.ofEpochDay(days)
}

fun generateRandomAmount(): BigDecimal {
    val minAmount = BigDecimal(50000)
    val maxAmount = BigDecimal(80000)
    return minAmount + BigDecimal(Random.nextDouble()) * (maxAmount - minAmount)
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun LinesScreenPreview() {
    LinesScreen(navigateHome = {})
}