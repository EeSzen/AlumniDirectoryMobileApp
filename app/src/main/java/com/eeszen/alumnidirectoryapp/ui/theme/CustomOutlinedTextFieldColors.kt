package com.eeszen.alumnidirectoryapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun customOutlinedTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = MaterialTheme.colorScheme.surface,
    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
    focusedBorderColor = Color.Black,
    unfocusedBorderColor = Color.Black.copy(alpha = 0.7f),
    focusedLabelColor = Color.Black,
    unfocusedLabelColor = Color.Black.copy(alpha = 0.7f),
    cursorColor = Color.Black,
    errorBorderColor = MaterialTheme.colorScheme.error
)