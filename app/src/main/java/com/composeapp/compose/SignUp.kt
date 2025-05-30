package com.composeapp.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.composeapp.compose.SignupViewModel
import com.composeapp.compose.SignupState

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignupPreview() {
    Signup(onNavigateBack = {}, onSignupSuccess = {})
}

@Composable
fun Signup(onNavigateBack: () -> Unit, onSignupSuccess: () -> Unit) {
    val keyboardVisible = isKeyboardVisible()
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogText by remember { mutableStateOf("") }

    val signupViewModel: SignupViewModel = viewModel()
    val signupState by signupViewModel.signupState.collectAsState()

    // React to signup state changes
    LaunchedEffect(signupState) {
        when (signupState) {
            is SignupState.Success -> {
                // Only navigate on true success
                onSignupSuccess()
            }
            is SignupState.Error -> {
                dialogText = (signupState as SignupState.Error).message
                showDialog = true
                if ((signupState as SignupState.Error).message == "Username already exists") {
                    username = ""
                    email = ""
                    password = ""
                }
                // Do NOT navigate on error
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.DarkGray, Color.Black)
                )
            )
            .imePadding()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (keyboardVisible) Arrangement.Top else Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.height(if (keyboardVisible) 20.dp else 40.dp))

        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Signup image",
            modifier = Modifier
                .size(250.dp)
                .padding(top = if (keyboardVisible) 0.dp else 10.dp)
        )

        Text(
            text = "Create Account",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color = Color.Yellow,
            modifier = Modifier.padding(top = 20.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Sign up to get started",
            color = Color.White,
            modifier = Modifier.shadow(elevation = 10.dp, spotColor = Color.Red)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = Color.White
            )
        )

//        Spacer(modifier = Modifier.height(10.dp))

//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text(text = "Email") },
//            modifier = Modifier.fillMaxWidth(),
//            keyboardOptions = KeyboardOptions.Default.copy(
//                imeAction = ImeAction.Next,
//                keyboardType = KeyboardType.Email
//            ),
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = Color.Transparent,
//                unfocusedContainerColor = Color.Transparent,
//                focusedTextColor = Color.White,
//                unfocusedTextColor = Color.White,
//                focusedIndicatorColor = Color.White,
//                unfocusedIndicatorColor = Color.Gray,
//                cursorColor = Color.White
//            )
//        )

//        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.Gray,
                cursorColor = Color.White
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Text(
                        text = if (passwordVisible) "🙈" else "👁",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                when {
                    username.isBlank() -> {
                        dialogText = "Please enter username"
                        showDialog = true
                    }

                    password.isBlank() -> {
                        dialogText = "Please enter password"
                        showDialog = true
                    }
                    else -> {
                        signupViewModel.signUp(username, password)
                        showDialog = false
                        Log.d("SignupScreen", "Username: $username, Email: $email, Password: $password")
                        onSignupSuccess()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { onNavigateBack() }) {
            Text(text = "Already have an account? Log in", color = Color.White)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Alert") },
            text = { Text(text = dialogText) },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}
