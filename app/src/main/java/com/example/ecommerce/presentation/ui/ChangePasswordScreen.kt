package com.example.ecommerce.presentation.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.ecommerce.R
import com.example.ecommerce.data.model.ChangePasswordResponse
import com.example.ecommerce.presentation.viewModel.ChangePasswordViewModel
import com.example.ecommerce.ui.theme.Poppins
import com.example.ecommerce.ui.theme.PrimaryColor
import com.example.ecommerce.ui.theme.SecondaryColor
import com.example.ecommerce.ui.theme.Shapes

class ChangePassword() : Screen {
    @Composable
    override fun Content() {
        val viewModel: ChangePasswordViewModel = hiltViewModel()
        val changePasswordResponse by viewModel.changePasswordResponse.collectAsState()
        PasswordScreen(viewModel, changePasswordResponse)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordScreen(
    viewModel: ChangePasswordViewModel,
    changePasswordResponse: ChangePasswordResponse?
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
    var isCurrentPasswordVisible by remember { mutableStateOf(false) }
    var isNewPasswordVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Home")
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(padding.calculateTopPadding()))
            Image(
                painter = painterResource(id = R.drawable.ic_forgot_password_illustration),
                contentDescription = "",
                modifier = Modifier.size(240.dp)
            )
            Card(
                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Column(
                    modifier = Modifier.padding(vertical = 20.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.enter_registered_password),
                        fontFamily = Poppins,
                        color = SecondaryColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp
                    )
                    OutlinedTextField(
                        value = currentPassword,
                        onValueChange = { currentPassword = it },
                        modifier = Modifier
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
                        visualTransformation = if (isCurrentPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.current_password_hint),
                                color = Color.Gray
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = Poppins
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                isCurrentPasswordVisible = !isCurrentPasswordVisible
                            }) {
                                Icon(
                                    imageVector = if (isCurrentPasswordVisible) Icons.Filled.Close else Icons.Filled.Done,
                                    contentDescription = if (isCurrentPasswordVisible) "Hide password" else "Show password"
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        modifier = Modifier
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
                        visualTransformation = if (isNewPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.new_password_hint),
                                color = Color.Gray
                            )
                        },
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = Poppins
                        ),
                        trailingIcon = {
                            IconButton(onClick = { isNewPasswordVisible = !isNewPasswordVisible }) {
                                Icon(
                                    imageVector = if (isNewPasswordVisible) Icons.Filled.Close else Icons.Filled.Done,
                                    contentDescription = if (isNewPasswordVisible) "Hide password" else "Show password"
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            viewModel.changePassword(currentPassword,newPassword,
                                onSuccess = {
                                    navigator.pop()
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.password_changed_successfully),
                                        Toast.LENGTH_SHORT
                                    ).show()

                                },
                                onError = { errorMessage ->
                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            PrimaryColor
                        ),
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
                            text = stringResource(id = R.string.change_password_title),
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
