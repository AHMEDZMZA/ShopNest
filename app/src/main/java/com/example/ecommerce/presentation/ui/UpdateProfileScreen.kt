/*
package com.example.ecommerce.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.R
import com.example.ecommerce.data.model.ProfileDataClass
import com.example.ecommerce.presentation.viewModel.ChangeProfileViewModel
import com.example.ecommerce.ui.theme.Poppins
import com.example.ecommerce.ui.theme.PrimaryColor
import com.example.ecommerce.ui.theme.SecondaryColor
import com.example.ecommerce.ui.theme.Shapes
class UpdateProfile(private val profileData: ProfileDataClass) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        ChangeProfileScreen(
            initialName = profileData.name,
            initialEmail = profileData.email,
            initialPhone = profileData.phone,
            onSuccess = {
                Toast.makeText(
                    context,
                    "Profile updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
                navigator.pop()
            },
            onError = { errorMessage ->
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeProfileScreen(
    viewModel: ChangeProfileViewModel = hiltViewModel(),
    initialName: String,
    initialEmail: String,
    initialPhone: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var email by remember { mutableStateOf(initialEmail) }
    var phone by remember { mutableStateOf(initialPhone) }
    val password by remember { mutableStateOf("") } // Add password variable if needed
    val image by remember { mutableStateOf("") } // Image placeholder if needed
    val isLoading by viewModel.isLoading.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.updata),
                contentDescription = "",
                modifier = Modifier.size(240.dp)
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    SecondaryColor,
                    cursorColor = PrimaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = Shapes.medium,
                singleLine = true,
                placeholder = {
                    Text(text = "Name", color = Color.Gray)
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    SecondaryColor,
                    cursorColor = PrimaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = Shapes.medium,
                singleLine = true,
                placeholder = {
                    Text(text = "Email", color = Color.Gray)
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    SecondaryColor,
                    cursorColor = PrimaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = Shapes.medium,
                singleLine = true,
                placeholder = {
                    Text(text = "Phone", color = Color.Gray)
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )

            Button(
                onClick = {
                    viewModel.changeProfile(
                        email = email,
                        image = image,
                        name = name,
                        password = password,
                        phone = phone,
                        onSuccess = onSuccess,
                        onError = onError
                    )
                },
                enabled = !isLoading,
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
                Text(text = if (isLoading) "Updating..." else "Update Profile", fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}
*/

package com.example.ecommerce.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.R
import com.example.ecommerce.data.model.ProfileDataClass
import com.example.ecommerce.presentation.viewModel.ChangeProfileViewModel
import com.example.ecommerce.ui.theme.Poppins
import com.example.ecommerce.ui.theme.PrimaryColor
import com.example.ecommerce.ui.theme.SecondaryColor
import com.example.ecommerce.ui.theme.Shapes

class UpdateProfile(private val profileData: ProfileDataClass) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current

        // This needs to be in a Composable function.
        ChangeProfileScreen(
            initialName = profileData.name,
            initialEmail = profileData.email,
            initialPhone = profileData.phone,
            onSuccess = {
                Toast.makeText(
                    context,
                    context.getString(R.string.profile_updated_successfully), // Using context for Toast
                    Toast.LENGTH_SHORT
                ).show()
                navigator.pop()
            },
            onError = {
                Toast.makeText(
                    context,
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeProfileScreen(
    viewModel: ChangeProfileViewModel = hiltViewModel(),
    initialName: String,
    initialEmail: String,
    initialPhone: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var email by remember { mutableStateOf(initialEmail) }
    var phone by remember { mutableStateOf(initialPhone) }
    val image by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.updata),
                contentDescription = "",
                modifier = Modifier.size(240.dp)
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    SecondaryColor,
                    cursorColor = PrimaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = Shapes.medium,
                singleLine = true,
                placeholder = {
                    Text(text = stringResource(R.string.name), color = Color.Gray)
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    SecondaryColor,
                    cursorColor = PrimaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = Shapes.medium,
                singleLine = true,
                placeholder = {
                    Text(text = stringResource(R.string.email), color = Color.Gray)
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    SecondaryColor,
                    cursorColor = PrimaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = Shapes.medium,
                singleLine = true,
                placeholder = {
                    Text(text = stringResource(R.string.phone), color = Color.Gray)
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )

            Button(
                onClick = {
                    viewModel.changeProfile(
                        email = email,
                        image = image,
                        name = name,
                        phone = phone,
                        onSuccess = onSuccess,
                        onError = onError
                    )
                },
                enabled = !isLoading,
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
                    text = if (isLoading) stringResource(R.string.updating) else stringResource(R.string.update_profile),
                    fontFamily = Poppins,
                    color = SecondaryColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
