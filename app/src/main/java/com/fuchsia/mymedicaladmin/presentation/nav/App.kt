package com.fuchsia.mymedicaladmin.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.fuchsia.mymedicaladmin.presentation.screens.AddProductScreen
import com.fuchsia.mymedicaladmin.presentation.screens.ApprovalPendingScreen
import com.fuchsia.mymedicaladmin.presentation.screens.ApprovedUserScreen
import com.fuchsia.mymedicaladmin.presentation.screens.BottomNavigationBar
import com.fuchsia.mymedicaladmin.presentation.screens.DashBordScreen
import com.fuchsia.mymedicaladmin.presentation.screens.EditUserScreen
import com.fuchsia.mymedicaladmin.presentation.screens.SalesHistoryScreen
import com.fuchsia.mymedicaladmin.presentation.screens.SpecificOrderScreen
import com.fuchsia.mymedicaladmin.presentation.screens.TabBar
import com.fuchsia.mymedicaladmin.presentation.screens.UserDetailsScreen

@Composable
fun App() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.BottomNavigationBar) {

        composable<Routes.TabScreen> {
            TabBar(navController = navController)

        }
        composable<Routes.ApprovalPendingScreen> {
            ApprovalPendingScreen(navController)
        }

        composable<Routes.ApprovedUserScreen> {
            ApprovedUserScreen(navController)
        }

        composable<Routes.UserDetailsScreen> {

            val data = it.toRoute<Routes.UserDetailsScreen>()
            UserDetailsScreen(id = data.id, navController = navController)
        }
        composable<Routes.BottomNavigationBar> {
            BottomNavigationBar(navController = navController)
        }
        composable<Routes.SpecificOrderScreen> {

            val data = it.toRoute<Routes.SpecificOrderScreen>()
            SpecificOrderScreen(orderId = data.orderId, navController)
        }

        composable<Routes.EditUserScreen> {
            val data = it.toRoute<Routes.EditUserScreen>()
            EditUserScreen(userId = data.userId, navController = navController)

        }

        composable<Routes.AddProductScreen> {
            AddProductScreen(navController = navController)

        }

        composable<Routes.DashBordScreen> {
            DashBordScreen(navController = navController)
        }

        composable<Routes.SalesHistoryScreen> {
            SalesHistoryScreen()
        }

    }

}