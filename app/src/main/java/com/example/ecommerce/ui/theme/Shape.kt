package com.example.ecommerce.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Define the default Shapes used across the app
val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp)
)

// Define a custom shape for the Bottom Box
val BottomBoxShape = RoundedCornerShape(
    topStart = 14.dp,
    topEnd = 14.dp,
    bottomEnd = 0.dp,
    bottomStart = 0.dp
)

// Define a custom shape for Input Boxes
val InputBoxShape = RoundedCornerShape(14.dp)
