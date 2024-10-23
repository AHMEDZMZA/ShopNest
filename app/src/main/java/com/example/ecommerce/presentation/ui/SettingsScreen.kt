package com.example.ecommerce.presentation.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.ui.theme.LightPrimaryColor
import com.example.ecommerce.ui.theme.Poppins
import com.example.ecommerce.ui.theme.PrimaryColor
import com.example.ecommerce.ui.theme.SecondaryColor
import com.example.ecommerce.ui.theme.Shapes
import com.example.ecommerce.R
import com.example.ecommerce.presentation.viewModel.LogoutViewModel
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.LocaleList
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.ui.layout.ContentScale
import com.example.ecommerce.util.PreferencesManager
import java.util.Locale

class Settings : Screen {
    @Composable
    override fun Content() {
        SettingsScreen()
    }
}

@Composable
fun SettingsScreen() {
    Column() {
        HeaderWithBackButton()
        ProfileCardUI()
        GeneralOptionsUI()
        SupportOptionsUI()
    }
}

@Composable
fun HeaderWithBackButton() {
    val navigator = LocalNavigator.currentOrThrow
    Spacer(modifier = Modifier.height(25.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier
                .size(25.dp)
                .clickable(
                    indication = null, // إخفاء تأثير الضغط الافتراضي
                    interactionSource = remember { MutableInteractionSource() }, onClick = {
                        navigator.pop()
                    })
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = R.string.settings),
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 20.sp,
        )}
}

@Composable
fun ProfileCardUI() {
    val navigator = LocalNavigator.currentOrThrow
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = Shapes.large
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically // Center vertically
        ) {
            // Left Column for Text and Button
            Column(
                modifier = Modifier.weight(1f) // Take remaining space
            ) {
                Text(
                    text = stringResource(id = R.string.check_your_profile),
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Button(
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = {
                        navigator.push(ProfileScreen())
                    },
                    colors = ButtonDefaults.buttonColors(PrimaryColor),
                    contentPadding = PaddingValues(horizontal = 30.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 2.dp
                    ),
                    shape = Shapes.medium
                ) {
                    Text(
                        text = stringResource(id = R.string.view),
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            // Image on the right side
            Image(
                painter = painterResource(id = R.drawable.ic_profile_card_image),
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp) // Specify a width if needed
                    .clip(Shapes.medium), // Optional: add clipping if needed
                contentScale = ContentScale.Crop // To maintain aspect ratio
            )
        }
    }
}


@Composable
fun GeneralOptionsUI() {
    val navigator = LocalNavigator.currentOrThrow
    var showLanguageDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.general),
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        GeneralSettingItem(
            icon = R.drawable.ic_more,
            mainText = stringResource(id = R.string.language),
            subText = stringResource(id = R.string.choose_your_language),
            onClick = {
                showLanguageDialog = true
            }
        )
        if (showLanguageDialog) {
            LanguageDialog(
                onDismiss = { showLanguageDialog = false },
                onConfirmClick = { selectedLanguage ->
                    // Handle language change here
                }
            )
        }

    }
}

@Composable
fun GeneralSettingItem(icon: Int, mainText: String, subText: String, onClick: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) } // Manage dialog visibility

    Card(
        onClick = { showDialog = true }, // Show dialog on click
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = Shapes.medium)
                        .background(LightPrimaryColor)
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.offset(y = (2).dp)
                ) {
                    Text(
                        text = mainText,
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = subText,
                        fontFamily = Poppins,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.offset(y = (-4).dp)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
        }
    }

    if (showDialog) {
        LanguageSelectionDialog(onDismiss = { showDialog = false })
    }
}

@Composable
fun SupportItem(icon: Int, mainText: String, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(shape = Shapes.medium)
                        .background(LightPrimaryColor)
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Text(
                    text = mainText,
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun LogoutButton() {
    val navigator = LocalNavigator.currentOrThrow
    val logOutViewModel: LogoutViewModel = hiltViewModel()
    val context = LocalContext.current
    Button(
        onClick = {
            logOutViewModel.logout(
                onSuccess = {
                    navigator.push(LoginScreen())
                    Toast.makeText(
                        context,
                        context.getString(R.string.logout_successful),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onError = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            )
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Red
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.logout),
                fontFamily = Poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = "Logout",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun SupportOptionsUI() {
    val navigator = LocalNavigator.currentOrThrow
    var showContactDialog by remember { mutableStateOf(false) } // State to manage dialog visibility

    Column(
        modifier = Modifier
            .padding(horizontal = 14.dp)
            .padding(top = 10.dp)
    ) {
        Text(
            text = stringResource(id = R.string.support),
            fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        SupportItem(
            icon = R.drawable.ic_whatsapp,
            mainText = stringResource(id = R.string.contact),
            onClick = { showContactDialog = true } // Show dialog on click
        )
        SupportItem(
            icon = R.drawable.ic_privacy_policy,
            mainText = stringResource(id = R.string.privacy_policy),
            onClick = {}
        )
        SupportItem(
            icon = R.drawable.ic_about,
            mainText = stringResource(id = R.string.about),
            onClick = {}
        )
        LogoutButton()

        // Show dialog if showContactDialog is true
        if (showContactDialog) {
            ContactDialog(
                onDismiss = { showContactDialog = false }
            )
        }
    }
}

@Composable
fun ContactDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(id = R.string.contact_us)) },
        text = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // صورة دائرية للهاتف
                Image(
                    painter = painterResource(id = R.drawable.phone2),
                    contentDescription = "Phone Icon",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .clickable {
                            val intent = Intent(
                                Intent.ACTION_DIAL,
                                Uri.parse("tel:+201007107341")
                            )
                            context.startActivity(intent)
                        }
                        .padding(vertical = 8.dp)

                )

                Spacer(modifier = Modifier.height(16.dp))

                // صورة دائرية لفيسبوك
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Facebook Icon",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/profile.php?id=100012140385861")
                            )
                            context.startActivity(intent)
                        }
                        .padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.linkedin),
                    contentDescription = "LinkedIn Icon",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.linkedin.com/in/eslam-khaled-7b70b12a0//")
                            )
                            context.startActivity(intent)
                        }
                        .padding(vertical = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss,colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))) {
                Text(text = stringResource(id = R.string.ok),color = Color.Black)
            }
        }
    )
}




@Composable
fun LanguageSelectionDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val options = listOf("English", "Arabic")
    var selectedOption by remember {
        mutableStateOf(
            PreferencesManager.getSelectedLanguage(context) ?: "English"
        )
    } // Load the saved language

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = stringResource(id = R.string.language_selection)) },
        text = {
            Column {
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedOption = option
                            }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            colors = RadioButtonDefaults.colors(Color(0xFFFF9800)),
                            selected = selectedOption == option,
                            onClick = { selectedOption = option }
                        )
                        Text(
                            text = option,
                            modifier = Modifier.padding(start = 8.dp),
                            fontFamily = Poppins
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    when (selectedOption) {
                        "Arabic" -> setLocale(context, "ar")
                        "English" -> setLocale(context, "en")
                    }
                    onDismiss()  // Dismiss the dialog after changing the locale
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
            ) {
                Text(text = stringResource(id = R.string.confirm))
            }
        }
    )
}

@Composable
fun LanguageDialog(onDismiss: () -> Unit, onConfirmClick: (String) -> Unit) {
    val context = LocalContext.current
    var selectedLanguage by remember { mutableStateOf("en") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = stringResource(id = R.string.language_selection))
        },
        text = {
            Column {
                RadioButtonWithText(
                    selected = selectedLanguage == "en",
                    text = stringResource(id = R.string.english),
                    onClick = { selectedLanguage = "en" }
                )
                RadioButtonWithText(
                    selected = selectedLanguage == "ar",
                    text = stringResource(id = R.string.arabic),
                    onClick = { selectedLanguage = "ar" }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmClick(selectedLanguage)
                    setLocale(context, selectedLanguage) // Change the locale
                    onDismiss()
                }
            ) {
                Text(text = stringResource(id = R.string.confirm))
            }
        }
    )
}

@Composable
fun RadioButtonWithText(selected: Boolean, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

fun setLocale(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)

    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)

    // For API level 24 and above
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)
        config.setLocales(localeList)
    }

    // Update the configuration
    context.resources.updateConfiguration(config, context.resources.displayMetrics)

    // Save the selected language in SharedPreferences
    PreferencesManager.setSelectedLanguage(context, language)

    // Restart the activity to apply the new locale
    if (context is Activity) {
        context.recreate()
    }
}


