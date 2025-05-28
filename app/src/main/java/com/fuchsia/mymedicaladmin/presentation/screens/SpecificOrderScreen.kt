package com.fuchsia.mymedicaladmin.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fuchsia.mymedicaladmin.viewmodel.ProductViewmodel

@Composable
fun SpecificOrderScreen(
    orderId: String? = null,
    navController: NavController,
    viewmodel: ProductViewmodel = hiltViewModel()
) {

    val state = viewmodel.getSpecificOrderState.collectAsState()

    LaunchedEffect(Unit) {
        if (orderId != null) {
            viewmodel.getSpecificOrder(orderId)
        }
    }

    when {
        state.value.isLoading -> {

            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()

            }
        }

        state.value.error != null -> {

            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                Text(text = state.value.error!!)

            }

        }

        state.value.data != null -> {

            val data = state.value.data!!

            val message = remember { mutableStateOf(state.value.data?.message ?: "") }
            val totalPrice = remember { mutableStateOf(state.value.data?.total_price ?: "") }
            val orderState = remember { mutableStateOf(data.isApproved) }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Card(modifier = Modifier.padding(10.dp)) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {

                        Row {
                            Button(onClick = {
                                orderState.value = "1"
                            }) {
                                Text(
                                    text = "Accept",
                                    fontFamily = FontFamily.Serif
                                )

                            }
                            Spacer(modifier = Modifier.weight(1f))

                            Button(onClick = {
                                orderState.value = "2"
                            }, colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            )
                            ) {
                                Text(
                                    text = "Reject",
                                    fontFamily = FontFamily.Serif
                                )
                            }

                        }
                        Spacer(modifier = Modifier.width(20.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = when (orderState.value) {
                                    "1" -> "Order is Accepted"
                                    "2" -> "Order is Rejected"
                                    else -> "Order is Pending"
                                },
                                fontFamily = FontFamily.Serif

                            )
                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = ("Message: " + data.message),
                                fontFamily = FontFamily.Serif
                            )

                            OutlinedTextField(
                                value = message.value,
                                onValueChange = {
                                    message.value = it
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedContainerColor = Color(0xFFEBF7FF),
                                    unfocusedContainerColor = Color(0xFFEBF7FF),
                                    focusedIndicatorColor = Color(0xFF004373),
                                ),
                                label = {
                                    Text(
                                        text = "Negotiation Request",
                                        fontFamily = FontFamily.Serif
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(10.dp))

                            OutlinedTextField(
                                value = totalPrice.value.toString(),
                                onValueChange = {
                                    totalPrice.value = it
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedContainerColor = Color(0xFFEBF7FF),
                                    unfocusedContainerColor = Color(0xFFEBF7FF),
                                    focusedIndicatorColor = Color(0xFF004373),
                                ),
                                label = {
                                    Text(
                                        text = "New Price",
                                        fontFamily = FontFamily.Serif
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(40.dp))

                            Button(onClick = {
                                if (orderState.value == "1") {
                                    viewmodel.updateOrder(
                                        data.order_id,
                                        orderState.value,
                                        totalPrice.value.toString(),
                                        message.value
                                    )
                                    viewmodel.update_Stock(
                                        data.product_id,
                                        data.quantity
                                    )
                                    navController.popBackStack()
                                } else {
                                    viewmodel.updateOrder(
                                        data.order_id,
                                        orderState.value,
                                        totalPrice.value.toString(),
                                        message.value
                                    )
                                    navController.popBackStack()
                                }

                            }) {
                                Text(
                                    text = "Update Order Details",
                                    fontFamily = FontFamily.Serif
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}