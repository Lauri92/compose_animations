package com.example.animation_experiments.navigation.destinations.visibility

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun VisibilityScreen(navigateHome: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Visibility") },
                navigationIcon = {
                    IconButton(onClick = navigateHome) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->

        var visible by remember {
            mutableStateOf(true)
        }

        var animatedVisibilityEnter by remember { mutableStateOf(fadeIn() + expandVertically()) }
        var animatedVisibilityExitAnimation by
        remember { mutableStateOf(fadeOut() + shrinkVertically()) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(top = paddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    animatedVisibilityEnter = fadeIn() + expandVertically()
                    animatedVisibilityExitAnimation = fadeOut() + shrinkVertically()
                }) {
                    Text(text = "Expand/Shrink")
                }
                Button(onClick = {
                    animatedVisibilityEnter = scaleIn()
                    animatedVisibilityExitAnimation = scaleOut()
                }) {
                    Text(text = "Scale")
                }
                Button(onClick = {
                    animatedVisibilityEnter = slideInHorizontally(initialOffsetX = { -it })
                    animatedVisibilityExitAnimation = slideOutHorizontally(targetOffsetX = { it })
                }) {
                    Text(text = "Slide")
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .background(Color.Green)
                        .clickable {
                            visible = !visible
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        color = Color.Black,
                        text = "Enter: EnterTransition = fadeIn() + expandVertically()\n" +
                                "Exit: ExitTransition = fadeOut() + shrinkVertically()"
                    )
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = animatedVisibilityEnter,
                    exit = animatedVisibilityExitAnimation
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(75.dp)
                            .background(Color.Blue)
                    )
                }
            }

            var redBoxExpanded by remember {
                mutableStateOf(true)
            }

            var redBoxVisible by remember {
                mutableStateOf(true)
            }

            val animatedAlpha by animateFloatAsState(
                targetValue = if (redBoxVisible) 1.0f else 0f,
                animationSpec = tween(2000),
                label = "alpha"
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .animateContentSize()
                        .width(100.dp)
                        .graphicsLayer {
                            alpha = animatedAlpha
                        }
                        .height(if (redBoxExpanded) 100.dp else 50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Red)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = { redBoxVisible = !redBoxVisible }
                    ) {
                        Text("Toggle box visibility")
                    }
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = { redBoxExpanded = !redBoxExpanded }
                    ) {
                        Text("Toggle box expaded")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
            ) {
                var count by remember { mutableStateOf(0) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    AnimatedContent(targetState = count, label = "") { targetCount ->
                        // Make sure to use `targetCount`, not `count`.
                        Text(text = "Count: $targetCount")
                    }
                    AnimatedContent(
                        targetState = count,
                        transitionSpec = {
                            // Compare the incoming number with the previous number.
                            if (targetState > initialState) {
                                // If the target number is larger, it slides up and fades in
                                // while the initial (smaller) number slides up and fades out.
                                slideInVertically { height -> height } + fadeIn() togetherWith
                                        slideOutVertically { height -> -height } + fadeOut()
                            } else {
                                // If the target number is smaller, it slides down and fades in
                                // while the initial number slides down and fades out.
                                slideInVertically { height -> -height } + fadeIn() togetherWith
                                        slideOutVertically { height -> height } + fadeOut()
                            }.using(
                                // Disable clipping since the faded slide-in/out should
                                // be displayed out of bounds.
                                SizeTransform(clip = false)
                            )
                        }, label = ""
                    ) { targetCount ->
                        Text(text = "$targetCount")
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = { count++ }) {
                        Text("+")
                    }
                    Button(onClick = { count-- }) {
                        Text("-")
                    }
                }


                var expanded by remember { mutableStateOf(true) }

                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = { expanded = !expanded }) {
                            Text("Toggle")
                        }
                    }
                    AnimatedContent(
                        targetState = expanded,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(500, 150)) togetherWith
                                    fadeOut(animationSpec = tween(150)) using
                                    SizeTransform { initialSize, targetSize ->
                                        if (targetState) {
                                            keyframes {
                                                // Expand horizontally first.
                                                IntSize(targetSize.width, initialSize.height) at 250
                                                durationMillis = 500
                                            }
                                        } else {
                                            keyframes {
                                                // Shrink vertically first.
                                                IntSize(initialSize.width, targetSize.height) at 250
                                                durationMillis = 500
                                            }
                                        }
                                    }
                        }, label = ""
                    ) { targetExpanded ->
                        if (targetExpanded) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .background(Color.Green)
                            ) {
                                Text(
                                    color = Color.Black,
                                    text = "Lorem ipsum Lorem ipsum Lorem ipsum LLorem ipsum Lorem ipsum Lorem ipsum LLorem ipsum Lorem ipsum Lorem ipsum LLorem ipsum Lorem ipsum Lorem ipsum LLorem ipsum Lorem ipsum Lorem ipsum LLorem ipsum Lorem ipsum Lorem ipsum LLorem ipsum Lorem ipsum Lorem ipsum LLorem ipsum Lorem ipsum Lorem ipsum LLorem ipsum Lorem ipsum Lorem ipsum LLorem ipsum Lorem ipsum Lorem ipsum LLorem ipsum Lorem ipsum Lorem ipsum LLorem ipsum Lorem ipsum Lorem ipsum LLorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum "
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier
                                    .background(Color.Green)
                            ) {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(imageVector = Icons.Default.Build, contentDescription = "")
                                }
                            }
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
private fun VisibilityScreenPreview() {
    VisibilityScreen(navigateHome = {})
}