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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aay.compose.donutChart.DonutChart
import com.aay.compose.donutChart.model.PieChartData
import com.fuchsia.mymedicaladmin.presentation.nav.Routes
import com.fuchsia.mymedicaladmin.viewmodel.MyViewModel
import com.fuchsia.mymedicaladmin.viewmodel.ProductViewmodel

@Composable
fun DashBordScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MyViewModel = hiltViewModel(),
    viewmodel2: ProductViewmodel = hiltViewModel()
) {

    val userState = viewModel.Get_All_User_state.collectAsState()
    val orderState = viewmodel2.getAllOrdersHistoryState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllUsers()
        viewmodel2.getAllOrdersHistory()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        when {
            userState.value.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                    Spacer(modifier.height(500.dp))

                }
            }

            userState.value.error != null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = userState.value.error!!)

                }
            }

            userState.value.data != null -> {
                val userdata = userState.value.data

                Card(
                    modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(5.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {

                        Spacer(modifier = Modifier.height(5.dp))

                        if (userdata != null) {
                            Text(
                                text = "Total Users: " + userdata.size,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color(0xFF019CB7)
                            )
                        }

                        val data1 = userdata?.filter { it.isApproved == 1 }?.size?.toDouble()
                        val data2 = userdata?.filter { it.isApproved == 0 }?.size?.toDouble()
                        val data3 =
                            userdata?.filter { it.isApproved != 0 && it.isApproved != 1 }?.size?.toDouble()


                        if (data1 != null) {
                            if (data2 != null) {
                                if (data3 != null) {
                                    DonutChartSample(
                                        title1 = "Approved: ${data1.toInt()}",
                                        data1,
                                        title2 = "Pending: ${data2.toInt()}",
                                        data2,
                                        title3 = "Rejected: ${data3.toInt()}",
                                        data3,
                                        centerTitle = "Orders"

                                    )
                                }
                            }
                        }
                    }
                }
            }
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
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Total Sells Amount:",
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            color = Color(0xFF019CB7),
                            style = MaterialTheme.typography.titleLarge
                        )

                        Card(
                            modifier
                                .clip(
                                    RoundedCornerShape(50.dp)
                                )
                                .clickable {
                                    navController.navigate(Routes.SalesHistoryScreen)
                                },

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

                    }
                }

                Card(
                    modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(5.dp),
                ) {
                    Column(
                        modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "Total Orders: " + salesData!!.size.toString(),
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF019CB7)
                        )

                        val data1 = salesData.filter { it.isApproved == "1" }.size.toDouble()
                        val data2 = salesData.filter { it.isApproved == "0" }.size.toDouble()
                        val data3 =
                            salesData.filter { it.isApproved != "0" && it.isApproved != "1" }.size.toDouble()
                        DonutChartSample(
                            title1 = "Approved: ${data1.toInt()}",
                            data1,
                            title2 = "Pending: ${data2.toInt()}",
                            data2,
                            title3 = "Rejected: ${data3.toInt()}",
                            data3,
                            centerTitle = "Orders"

                        )
                    }
                }


            }
        }

    }


}


@Composable
fun DonutChartSample(
    title1: String,
    data1: Double,
    title2: String,
    data2: Double,
    title3: String,
    data3: Double,
    centerTitle: String
) {

    val testPieChartData: List<PieChartData> = listOf(

        PieChartData(
            partName = title1,
            data = data1,
            color = Color(0xFF009673),
        ),
        PieChartData(
            partName = title2,
            data = data2,
            color = Color(0xFFF39200),
        ),
        PieChartData(
            partName = title3,
            data = data3,
            color = Color(0xFFDE0101),
        ),

        )

    DonutChart(
        modifier = Modifier.height(260.dp),
        pieChartData = testPieChartData,
        centerTitle = centerTitle,
        centerTitleStyle = TextStyle(color = Color(0xFF071952)),
        outerCircularColor = Color.LightGray,
        innerCircularColor = Color.Gray,
        ratioLineColor = Color.LightGray,
    )
}