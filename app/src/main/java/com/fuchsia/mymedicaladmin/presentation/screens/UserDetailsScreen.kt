package com.fuchsia.mymedicaladmin.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fuchsia.mymedicaladmin.presentation.nav.Routes
import com.fuchsia.mymedicaladmin.viewmodel.MyViewModel

@Composable
fun UserDetailsScreen(
    id: String? = null,
    viewModel: MyViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.getSpecificUserState.collectAsState()

    LaunchedEffect(Unit) {
        if (id != null) {
            viewModel.getSpecificUser(id)
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

            var isChecked by remember { mutableStateOf(data.isApproved == 2) }

            val userDetails = listOf(
                "Name: " to data.name,
                "User Id: " to data.user_id,
                "Email: " to data.email,
                "Phone Number: " to data.phone_number,
                "Address: " to data.address,
                "Password: " to data.password,
                "Pin Code: " to data.pincode,
                "Account Created on: " to data.date_of_account_creation,
                "Status: " to if (data.isApproved == 1) "Approved" else if (data.isApproved == 0) "Approval Pending" else "Blocked",
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Card(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            Modifier.padding(20.dp)
                        ) {

                            Text(
                                text = "Block User",
                                fontFamily = FontFamily.Serif,
                                color = Color.Red
                            )
                            Switch(
                                checked = isChecked,
                                onCheckedChange = { checked ->
                                    isChecked = checked
                                    if (checked) {
                                        viewModel.approveUser(data.user_id, 2)
                                        viewModel.getSpecificUser(id!!)

                                    } else {
                                        viewModel.approveUser(data.user_id, 1)
                                        viewModel.getSpecificUser(id!!)

                                    }
                                }
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(modifier = Modifier.padding(10.dp),
                            onClick = {
                                if (id != null) {
                                    viewModel.deleteUser(data.user_id)
                                }
                                navController.popBackStack()

                            }) {
                            Text(
                                text = "Delete User",
                                fontFamily = FontFamily.Serif,
                            )
                        }
                    }
                }

                Card(
                    Modifier
                        .height(500.dp)
                        .padding(10.dp)) {

                    Box(
                        Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile Icon",
                            tint = Color(0xFF03727C),
                            modifier = Modifier.size(120.dp)
                        )

                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(userDetails) { detail ->
                            ItemView(label = detail.first, value = detail.second)
                        }
                    }

                }

                Button(onClick = {
                    navController.navigate(Routes.EditUserScreen(data.user_id))
                }) {
                    Text(text = "Edit User Profile",
                        fontFamily = FontFamily.Serif)
                }


            }

        }

    }

}

@Composable
fun ItemView(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontFamily = FontFamily.Serif,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            fontFamily = FontFamily.Serif
        )
    }
}

