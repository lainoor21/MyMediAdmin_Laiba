package com.fuchsia.mymedicaladmin.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fuchsia.mymedicaladmin.presentation.nav.Routes
import com.fuchsia.mymedicaladmin.viewmodel.ProductViewmodel

@Composable
fun SalesHistoryScreen(
    modifier: Modifier = Modifier,
    viewmodel2: ProductViewmodel = hiltViewModel()
) {

    val orderState = viewmodel2.getAllOrdersHistoryState.collectAsState()
    LaunchedEffect(Unit) {
        viewmodel2.getAllOrdersHistory()
    }

    when {
        orderState.value.isLoading -> {

            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()

            }
        }

        orderState.value.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = orderState.value.error!!)

            }
        }

        orderState.value.data != null -> {

            val salesData = orderState.value.data
            val totalPrice = salesData?.sumOf { it.total_price.toDouble() } ?: 0.0

            Card(
                modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Total Sells Amount:",
                        fontFamily = FontFamily.Serif,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Card(
                        modifier.clip(
                            RoundedCornerShape(50.dp)
                        ),

                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "$totalPrice ₹",
                            color = Color(0xFF0573CB),
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.displaySmall
                        )
                    }


                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Sales History:",
                        fontFamily = FontFamily.Serif,
                        style = MaterialTheme.typography.titleMedium
                    )

                        val reversedSalesData = salesData!!.reversed()
                        LazyColumn {
                            items(reversedSalesData.size) { index ->

                                if (reversedSalesData[index].isApproved == "1") {

                                    Text(
                                        text = "+ ₹${reversedSalesData[index].total_price} credited on ${reversedSalesData[index].date_of_order_creation}",
                                        fontFamily = FontFamily.Serif,
                                        color = Color(0xFF01A208)
                                    )
                                }
                            }
                        }
                }
            }
        }
    }


}