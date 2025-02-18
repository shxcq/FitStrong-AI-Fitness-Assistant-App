package james.apt3065.aicoachfitnessapp.ui.composables.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.SupportAgent
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import james.apt3065.aicoachfitnessapp.ui.composables.RegularButton
import james.apt3065.aicoachfitnessapp.ui.composables.home.Heading
import james.apt3065.aicoachfitnessapp.ui.composables.login.InputField
import james.apt3065.aicoachfitnessapp.ui.navigation.Screens
import james.apt3065.aicoachfitnessapp.ui.theme.holoGreen
import james.apt3065.aicoachfitnessapp.ui.theme.veryDarkBlue
import james.apt3065.aicoachfitnessapp.viewmodel.UserViewModel
import james.apt3056.aicoachfitnessapp.R

fun isValidEmail(email: String): Boolean {
    val emailPattern = Regex(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$|^@admin\\.com$"
    )
    return emailPattern.matches(email)
}

@Composable
fun SignUpScreen(
    navController: NavHostController = rememberNavController(),
    userViewModel: UserViewModel = viewModel(),
    scaffoldState: ScaffoldState
) {

    val state = userViewModel.signUpState

    var userName by remember { mutableStateOf("") }
    var eMail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = state.error) {
        state.error?.let {
            scaffoldState.snackbarHostState.showSnackbar(
                it,
                null,
                SnackbarDuration.Short
            )
        }
    }

    LaunchedEffect(key1 = state.success) {
        if (state.success) {
            navController.navigate(Screens.Login.route)
        }
    }

    Surface(
        color = veryDarkBlue.copy(0.6f),
        modifier = Modifier.paint(
            painterResource(id = R.drawable.register_background),
            contentScale = ContentScale.Crop,
        ).fillMaxSize(),
    ) {
        if (state.loading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator(
                    color = holoGreen.copy(0.6f),
                    strokeWidth = 5.dp,
                )
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(horizontal = 50.dp)
            ) {
                Heading(
                    text = stringResource(R.string.signup),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                )

                InputField(
                    userName,
                    { userName = it },
                    stringResource(R.string.enter_name_hint),
                    Icons.Rounded.SupportAgent,
                    type = KeyboardType.Text
                )
                InputField(
                    eMail,
                    { eMail = it
                        emailError = if (isValidEmail(eMail)) null else "Invalid email address"
                    },
                    stringResource(R.string.enter_email_hint),
                    Icons.Rounded.Email,
                    type = KeyboardType.Email
                )
                emailError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                InputField(
                    password,
                    { password = it },
                    stringResource(R.string.enter_password_hint),
                    Icons.Rounded.Lock,
                    type = KeyboardType.Password,
                    true
                )
                InputField(
                    confirmPassword,
                    { confirmPassword = it },
                    stringResource(R.string.confirm_password_hint),
                    Icons.Rounded.Lock,
                    type = KeyboardType.Password,
                    true
                )
                passwordError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                RegularButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp),
                    text = stringResource(R.string.continue_text)
                ) {
                    if (isValidEmail(eMail) && password == confirmPassword) {
                        userViewModel.signUpUser(
                            userName = userName,
                            userEmail = eMail,
                            userPassword = password,
                            confirmPassword = confirmPassword
                        )
                    } else {
                        emailError = if (!isValidEmail(eMail)) "Invalid email address" else null
                        passwordError = if (password != confirmPassword) "Passwords do not match" else null
                    }
                }
            }
        }
    }
}