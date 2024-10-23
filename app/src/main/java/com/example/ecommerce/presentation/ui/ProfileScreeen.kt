package com.example.ecommerce.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberImagePainter
import com.example.ecommerce.R
import com.example.ecommerce.data.model.ProfileDataClass
import com.example.ecommerce.presentation.viewModel.ProfileViewModel
import com.example.ecommerce.ui.theme.Poppins
import com.example.ecommerce.ui.theme.PrimaryColor
import com.example.ecommerce.ui.theme.SecondaryColor
import com.example.ecommerce.ui.theme.Shapes

class ProfileScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: ProfileViewModel = hiltViewModel()
        val profile by viewModel.profileState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            viewModel.getProfile { errorMessage ->
                // Handle any errors here if necessary
            }
        }

        // Show a loading indicator if profile data is null
        if (profile == null) {
            LoadingIndicator()
        } else {
            ProfileContent(profile = profile!!.data, onBackClick = { navigator.pop() })
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .background(Color(0xFFF5F5F5)) // Optional: background color while loading
    ) {
        CircularProgressIndicator(color =Color(0xFFFF9800))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(profile: ProfileDataClass, onBackClick: () -> Unit) {
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Home")
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = R.drawable.profile,
                            builder = {
                                placeholder(R.drawable.profileimage) // Add a placeholder image
                                error(R.drawable.profileimage) // Add an error image
                            }
                        ),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = profile.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = profile.email,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(id = R.string.phone_label),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = profile.phone, style = MaterialTheme.typography.bodyMedium)
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    // Change Password Button
                    Button(
                        onClick = {
                            navigator.push(ChangePassword())
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 20.dp),
                        contentPadding = PaddingValues(vertical = 14.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 2.dp
                        ),
                        shape = Shapes.medium
                    ) {
                        Text(
                            text = stringResource(id = R.string.change_password),
                            fontFamily = Poppins,
                            color = SecondaryColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Update Profile Button
                    Button(
                        onClick = {
                            navigator.push(UpdateProfile(profile))
                        },
                        colors = ButtonDefaults.buttonColors(PrimaryColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 20.dp),
                        contentPadding = PaddingValues(vertical = 14.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 2.dp
                        ),
                        shape = Shapes.medium
                    ) {
                        Text(
                            text = stringResource(id = R.string.update_profile),
                            fontFamily = Poppins,
                            color = SecondaryColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
