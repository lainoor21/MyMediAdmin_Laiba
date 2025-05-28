package com.fuchsia.mymedicaladmin.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fuchsia.mymedicaladmin.R
import com.fuchsia.mymedicaladmin.presentation.nav.Routes
import com.fuchsia.mymedicaladmin.viewmodel.MyViewModel

@Composable
fun ApprovedUserScreen(navController: NavController, viewModel: MyViewModel = hiltViewModel()) {

    val state = viewModel.Get_All_User_state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllUsers()

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
            val data = state.value.data

            if (data!!.isNotEmpty()) {

                LazyColumn(modifier = Modifier.fillMaxSize().padding(10.dp)) {

                    items(data.size) {

                        if (data[it].isApproved == 1 || data[it].isApproved == 2) {

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .clickable {
                                        navController.navigate(Routes.UserDetailsScreen(id = data[it].user_id))
                                    },
                                elevation = CardDefaults.cardElevation(4.dp)

                                ) {
                                Row(modifier = Modifier.fillMaxSize().padding(start = 15.dp, top = 5.dp, bottom = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(R.drawable.img),
                                        contentDescription = null,
                                        modifier = Modifier.size(60.dp).padding(5.dp)
                                    )

                                    Spacer(modifier = Modifier.padding(10.dp))

                                    Column(Modifier.padding(10.dp)) {
                                        Text(text = "Name: " + data[it].name,
                                            fontFamily = FontFamily.Serif,
                                            style = MaterialTheme.typography.bodyLarge

                                        )
                                        Text(text = "Email: " + data[it].email,
                                            fontFamily = FontFamily.Serif,
                                            style = MaterialTheme.typography.bodyMedium

                                        )
                                        if(data[it].isApproved == 2){
                                            Text(text = "Status: Blocked",
                                                fontFamily = FontFamily.Serif,
                                                color = Color.Red,
                                                style = MaterialTheme.typography.bodySmall
                                            )

                                        }else{
                                            Text(text = "Status: Approved",
                                                fontFamily = FontFamily.Serif,
                                                color = Color(0xFF07BB6E),
                                                style = MaterialTheme.typography.bodySmall

                                            )

                                        }

                                    }
                                }

                            }
                        }

                    }

                }
            }
        }

    }
}