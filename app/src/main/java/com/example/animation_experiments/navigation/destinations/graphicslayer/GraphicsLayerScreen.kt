package com.example.animation_experiments.navigation.destinations.graphicslayer

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphicsLayerScreen(navigateHome: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Graphics Layer") },
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


            var xValue by remember {
                mutableFloatStateOf(0f)
            }

            var yValue by remember {
                mutableFloatStateOf(0f)
            }

            var zValue by remember {
                mutableFloatStateOf(0f)
            }

            val animatedX by animateFloatAsState(
                targetValue = xValue,
                animationSpec = tween(1500),
                label = "x"
            )


            val animatedY by animateFloatAsState(
                targetValue = yValue,
                animationSpec = tween(1500),
                label = "y"
            )

            val animatedZ by animateFloatAsState(
                targetValue = zValue,
                animationSpec = tween(1500),
                label = "z"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .width(200.dp)
                        .graphicsLayer {
                            rotationX = animatedX
                            rotationY = animatedY
                            rotationZ = animatedZ
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            // Sweep through screen
                            if (animatedY > 60 && animatedY < 120) {
                                Color.Transparent
                            } else if (animatedY > 90 || animatedY < -90) {
                                Color.Green
                            } else {
                                Color.Red
                            }
                        )
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onDoubleTap = {
                                    xValue = 0f
                                    yValue = 0f
                                    zValue = 0f
                                },
                                onTap = {
                                    xValue += (30..360).random()
                                    yValue += (30..360).random()
                                    zValue += (30..360).random()
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .graphicsLayer {
                                rotationY = if (animatedY > 90 || animatedY < -90) {
                                    180f
                                } else {
                                    0f
                                }
                            },
                        text = "Graphics layer animations\n" +
                                "y: ${animatedY}"
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        Button(onClick = {
                            xValue += 45f
                        }) {
                            Text("+ rotation x")
                        }

                        Button(onClick = {
                            xValue -= 45f
                        }) {
                            Text("- rotation x")
                        }
                    }
                    Column {
                        Button(onClick = {
                            yValue += 180f
                        }) {
                            Text("+ rotation y")
                        }

                        Button(onClick = {
                            yValue -= 180f
                        }) {
                            Text("- rotation y")
                        }
                    }

                    Column {
                        Button(onClick = {
                            zValue += 45f
                        }) {
                            Text("+ rotation z")
                        }

                        Button(onClick = {
                            zValue -= 45f
                        }) {
                            Text("- rotation z")
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun GraphicsLayerScreenPreview() {
    GraphicsLayerScreen(navigateHome = {})
}
