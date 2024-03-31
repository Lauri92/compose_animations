package com.example.animation_experiments.navigation.destinations.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    navigateToLines: () -> Unit,
    navigateToVisibility: () -> Unit,
    navigateToGraphicsLayer: () -> Unit,
    navigateToColor: () -> Unit,
    navigateToGraphics: () -> Unit
) {

    val colors = listOf(
        Color(0xFF0072FF),
        Color(0xFF00C6FF)
    )

    val gradientBrush = Brush.verticalGradient(colors = colors)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
            .padding(top = paddingValues.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .padding(vertical = 32.dp),
            text = "Animation Experiments",
            color = Color.Black,
            fontSize = 30.sp
        )

        ItemRow(
            navigateTarget = navigateToLines,
            rowText = "Lines"
        )
        ItemRow(
            navigateTarget = navigateToVisibility,
            rowText = "Visibility"
        )
        ItemRow(
            navigateTarget = navigateToGraphicsLayer,
            rowText = "Graphics Layer"
        )
        ItemRow(
            navigateTarget = navigateToColor,
            rowText = "Color"
        )
        ItemRow(
            navigateTarget = navigateToGraphics,
            rowText = "Graphics"
        )
    }
}

@Composable
fun ItemRow(
    navigateTarget: () -> Unit,
    rowText: String
) {
    Column(
        Modifier.padding(horizontal = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable { navigateTarget() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                color = Color.Black,
                fontSize = 20.sp,
                text = rowText
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                tint = Color.Black,
                contentDescription = null
            )
        }
        HorizontalDivider(color = Color.Black)
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    HomeScreen(
        paddingValues = PaddingValues(0.dp),
        navigateToLines = {},
        navigateToVisibility = {},
        navigateToGraphicsLayer = {},
        navigateToColor = {},
        navigateToGraphics = {}
    )
}
