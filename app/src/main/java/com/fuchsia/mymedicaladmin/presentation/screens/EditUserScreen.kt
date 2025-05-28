package com.fuchsia.mymedicaladmin.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fuchsia.mymedicaladmin.viewmodel.MyViewModel


@Composable
fun EditUserScreen( userId: String, modifier: Modifier = Modifier, viewModel: MyViewModel = hiltViewModel(), navController: NavController) {

    val state = viewModel.getSpecificUserState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getSpecificUser(userId)
    }

    when {
        state.value.isLoading -> {
            AlertDialog(modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(50.dp),
                containerColor = Color.Transparent,

                onDismissRequest = { /* Prevent dismissal */ },
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false
                ),
                title = null,
                text = {
                    Box(
                        modifier = Modifier
                            .size(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                        )
                    }
                },
                confirmButton = {},
                dismissButton = {}
            )
        }

        state.value.error != null -> {

            Box(contentAlignment = Alignment.Center) {
                Text(text = state.value.error.toString())
            }

        }

        state.value.data != null -> {
            if (state.value.data != null) {

                val nameState = remember { mutableStateOf(state.value.data?.name ?: "") }
                val emailState = remember { mutableStateOf(state.value.data?.email ?: "") }
                val passwordState = remember { mutableStateOf(state.value.data?.password ?: "") }
                val addressState = remember { mutableStateOf(state.value.data?.address ?: "") }
                val pincodeState = remember { mutableStateOf(state.value.data?.pincode ?: "") }
                val phone_numberState = remember { mutableStateOf(state.value.data?.phone_number ?: "") }


                Column(modifier.systemBarsPadding().fillMaxSize(), verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(text = "Edit ${state.value.data!!.name}'s Details",
                        fontSize = 22.sp,
                        color = Color(0xFF08719A),
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif)

                    Spacer(modifier = Modifier.size(25.dp))

                    OutlinedTextField(
                        value = nameState.value,
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color(0xFFEBF7FF),
                            unfocusedContainerColor =Color(0xFFEBF7FF),
                            focusedIndicatorColor = Color(0xFF004373),
                        ),
                        onValueChange = {
                            nameState.value = it
                        },
                        label = {
                            Text(text = "Name",
                                fontFamily = FontFamily.Serif)
                        }
                    )
                    OutlinedTextField(
                        value = emailState.value,
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color(0xFFEBF7FF),
                            unfocusedContainerColor =Color(0xFFEBF7FF),
                            focusedIndicatorColor = Color(0xFF004373),
                        ),
                        onValueChange = {
                            emailState.value = it
                        },
                        label = {
                            Text(text = "Email",
                                fontFamily = FontFamily.Serif)
                        }
                    )

                    OutlinedTextField(
                        value = passwordState.value,
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color(0xFFEBF7FF),
                            unfocusedContainerColor =Color(0xFFEBF7FF),
                            focusedIndicatorColor = Color(0xFF004373),
                        ),
                        onValueChange = {
                            passwordState.value = it
                        },
                        label = {
                            Text(text = "Password",
                                fontFamily = FontFamily.Serif)
                        }
                    )

                    OutlinedTextField(
                        value = addressState.value,
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color(0xFFEBF7FF),
                            unfocusedContainerColor =Color(0xFFEBF7FF),
                            focusedIndicatorColor = Color(0xFF004373),
                        ),
                        onValueChange = {
                            addressState.value = it
                        },
                        label = {
                            Text(text = "Address",
                                fontFamily = FontFamily.Serif)
                        }
                    )
                    OutlinedTextField(
                        value = pincodeState.value,
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color(0xFFEBF7FF),
                            unfocusedContainerColor =Color(0xFFEBF7FF),
                            focusedIndicatorColor = Color(0xFF004373),
                        ),
                        onValueChange = {
                            pincodeState.value = it
                        },
                        label = {
                            Text(text = "Pincode",
                                fontFamily = FontFamily.Serif)
                        }
                    )

                    OutlinedTextField(
                        value = phone_numberState.value,
                        colors = androidx.compose.material3.TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color(0xFFEBF7FF),
                            unfocusedContainerColor =Color(0xFFEBF7FF),
                            focusedIndicatorColor = Color(0xFF004373),
                        ),
                        onValueChange = {
                            phone_numberState.value = it
                        },
                        label = {
                            Text(text = "Phone Number",
                                fontFamily = FontFamily.Serif)
                        }
                    )

                    Spacer(modifier = Modifier.size(15.dp))

                    Button(onClick = {

                        if (nameState.value.isNotEmpty() && emailState.value.isNotEmpty()
                            && passwordState.value.isNotEmpty() && addressState.value.isNotEmpty()
                            && pincodeState.value.isNotEmpty() && phone_numberState.value.isNotEmpty()
                        ) {
                            viewModel.updateUsers(
                                name = nameState.value,
                                email = emailState.value,
                                password = passwordState.value,
                                address = addressState.value,
                                pincode = pincodeState.value,
                                phone_number = phone_numberState.value,
                                user_id =userId
                            )
                            Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()

                            navController.popBackStack()

                        }

                    }) {
                        Text(text = "Update Details",
                            fontFamily = FontFamily.Serif)
                    }

                }
            }

        }

    }

}
