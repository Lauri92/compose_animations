package com.example.animation_experiments.navigation.destinations.color

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorScreen(navigateHome: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Color") },
                navigationIcon = {
                    IconButton(onClick = navigateHome) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->

        val infiniteTransition = rememberInfiniteTransition(label = "infinite")
        val color by infiniteTransition.animateColor(
            initialValue = Color.Blue,
            targetValue = Color.Green,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "color"
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawRect(color)
                }
                .padding(top = paddingValues.calculateTopPadding())
        ) {

            val infiniteTextColorTransition =
                rememberInfiniteTransition(label = "infinite transition")
            val animatedColor by infiniteTextColorTransition.animateColor(
                initialValue = Color.Red,
                targetValue = Color.Yellow,
                animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
                label = "color"
            )

            BasicText(
                text = "Hello Compose",
                color = {
                    animatedColor
                },
                style = LocalTextStyle.current.copy(
                    fontSize = 40.sp
                )
            )

            val infiniteTextScaleTransition =
                rememberInfiniteTransition(label = "infinite transition")
            val scale by infiniteTextScaleTransition.animateFloat(
                initialValue = 8f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
                label = "scale"
            )

            val infiniteTextRotationTransition =
                rememberInfiniteTransition(label = "infinite transition")
            val rotation by infiniteTextRotationTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(tween(5000), RepeatMode.Restart),
                label = "scale"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Text(
                        text = "N",
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                transformOrigin = TransformOrigin.Center
                                rotationY = rotation
                            }
                            .padding(top = 50.dp),
                        // Text composable does not take TextMotion as a parameter.
                        // Provide it via style argument but make sure that we are copying from current theme
                        style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated)
                    )

                    Text(
                        text = "o",
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                transformOrigin = TransformOrigin.Center
                                rotationX = rotation
                            }
                            .padding(top = 50.dp),
                        // Text composable does not take TextMotion as a parameter.
                        // Provide it via style argument but make sure that we are copying from current theme
                        style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated)
                    )

                    Text(
                        text = "k",
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                transformOrigin = TransformOrigin.Center
                                rotationZ = rotation
                            }
                            .padding(top = 50.dp),
                        // Text composable does not take TextMotion as a parameter.
                        // Provide it via style argument but make sure that we are copying from current theme
                        style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated)
                    )
                    Text(
                        text = "i",
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                transformOrigin = TransformOrigin.Center
                                rotationZ = rotation
                                rotationY = rotation
                            }
                            .padding(top = 50.dp),
                        // Text composable does not take TextMotion as a parameter.
                        // Provide it via style argument but make sure that we are copying from current theme
                        style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated)
                    )
                    Text(
                        text = "a",
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                transformOrigin = TransformOrigin.Center
                                rotationY = rotation
                                rotationX = rotation
                            }
                            .padding(top = 50.dp),
                        // Text composable does not take TextMotion as a parameter.
                        // Provide it via style argument but make sure that we are copying from current theme
                        style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated)
                    )

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun VisibilityScreenPreview() {
    ColorScreen(navigateHome = {})
}