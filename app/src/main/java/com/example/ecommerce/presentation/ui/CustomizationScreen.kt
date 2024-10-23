package com.example.ecommerce.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizationScreen() {
    var selectedLanguage by remember { mutableStateOf("English") }
    var isDarkMode by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), // Increased padding for better spacing
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start // Align content to start
    ) {
        // Dropdown menu for language selection
        Text(
            text = "Select Language:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            TextField(
                readOnly = true,
                value = selectedLanguage,
                onValueChange = { /* No-op */ },
                label = { Text("Language") },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor() // Ensure dropdown menu aligns properly
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        selectedLanguage = "English"
                        expanded = false
                    },
                    text = { Text("English") } // Correctly passing text
                )
                DropdownMenuItem(
                    onClick = {
                        selectedLanguage = "Arabic"
                        expanded = false
                    },
                    text = { Text("Arabic") } // Correctly passing text
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp)) // Space between dropdown and switch

        // Switch for dark/light mode
        Text(
            text = "Dark Mode:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Switch(
                checked = isDarkMode,
                onCheckedChange = { isDarkMode = it }
            )

            Text(text = if (isDarkMode) "Dark" else "Light", modifier = Modifier.padding(start = 8.dp))
        }
    }
}

class PreviewCustomizationScreen : Screen {
    @Composable
    override fun Content() {
        CustomizationScreen()
    }
}
