package com.fuchsia.mymedicaladmin.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun TabBar(navController: NavController) {

    val tabs = listOf(
        TabItem("Approved User",
            Icons.Default.VerifiedUser, Icons.Filled.VerifiedUser),
        TabItem("Approval Pending", Icons.Default.Pending, Icons.Filled.Pending)
    )

    val pageState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = pageState.currentPage, modifier = Modifier.fillMaxWidth()) {

            tabs.forEachIndexed { index, tabItem ->

                Tab(
                    modifier = Modifier.fillMaxWidth(),
                    selected = pageState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pageState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                if (pageState.currentPage == index) tabItem.fillIcon else tabItem.icon,
                                contentDescription = null
                            )
                            Text(text = tabItem.title)
                        }
                    },
                    selectedContentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    unselectedContentColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface

                )

            }

        }

        HorizontalPager(state = pageState) { page ->
            when (page) {
                0 -> ApprovedUserScreen(navController)
                1 -> ApprovalPendingScreen(navController)
            }
        }

    }
}

data class TabItem(
    val title: String, val icon: ImageVector, val fillIcon: ImageVector
)