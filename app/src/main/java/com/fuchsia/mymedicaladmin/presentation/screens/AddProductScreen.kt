package com.fuchsia.mymedicaladmin.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fuchsia.mymedicaladmin.viewmodel.MyViewModel

@Composable
fun AddProductScreen(viewModel: MyViewModel = hiltViewModel(), navController: NavController) {

    val state = viewModel.addProductState.collectAsState()

    val productName = remember { mutableStateOf("") }
    val productPrice = remember { mutableStateOf("") }
    val productStock = remember { mutableStateOf("") }
    val productCategory = remember { mutableStateOf("") }
    val context = LocalContext.current

    when {
        state.value.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.value.error != null -> {
            Toast.makeText(context, state.value.error, Toast.LENGTH_SHORT).show()
        }

        state.value.data != null -> {
            Toast.makeText(context, state.value.data!!.message, Toast.LENGTH_SHORT).show()

        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Add Product",
            fontFamily = FontFamily.Serif,
            fontSize = 25.sp,
            color = Color(0xFF004373)
        )
        Spacer(modifier = Modifier.height(30.dp))


        OutlinedTextField(
            value = productName.value,
            onValueChange = {
                productName.value = it
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
                    text = "Product Name",
                    fontFamily = FontFamily.Serif
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = productCategory.value,
            onValueChange = {
                productCategory.value = it
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
                    text = "Product Category",
                    fontFamily = FontFamily.Serif
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = productPrice.value,
            onValueChange = {
                productPrice.value = it
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
                    text = "Product Price",
                    fontFamily = FontFamily.Serif
                )
            }
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = productStock.value,
            onValueChange = {
                productStock.value = it
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
                    text = "Product in Stock",
                    fontFamily = FontFamily.Serif
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(modifier = Modifier.width(200.dp), onClick = {

            if (productName.value.isEmpty() || productCategory.value.isEmpty()
                || productStock.value.isEmpty() || productPrice.value.isEmpty()
            ) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.addProduct(
                    productName.value,
                    productPrice.value.toFloat(),
                    productStock.value.toInt(),
                    productCategory.value

                )
                navController.popBackStack()
            }
        }) {
            Text(
                text = "Add Product to Store",
                fontFamily = FontFamily.Serif
            )
        }

    }

}

