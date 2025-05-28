package com.fuchsia.mymedicaladmin.presentation.nav

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object TabScreen : Routes()

    @Serializable
    object ApprovalPendingScreen : Routes()

    @Serializable
    object ApprovedUserScreen: Routes()

    @Serializable
    data class UserDetailsScreen(val id: String)

    @Serializable
    object BottomNavigationBar : Routes()

    @Serializable
    data class SpecificOrderScreen(val orderId: String)

    @Serializable
    data class EditUserScreen(val userId: String)

    @Serializable
    object AddProductScreen : Routes()

    @Serializable
    object DashBordScreen : Routes()

    @Serializable
    object SalesHistoryScreen : Routes()
}
