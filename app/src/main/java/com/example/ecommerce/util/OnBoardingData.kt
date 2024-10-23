package com.uistack.onboarding

import androidx.compose.ui.graphics.Color
import com.example.ecommerce.ui.theme.ColorBlue

data class OnBoardingData(
    val image: Int,
    val backgroundColor:Color,
    val mainColor:Color = ColorBlue
)