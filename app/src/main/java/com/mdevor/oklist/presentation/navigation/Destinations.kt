package com.mdevor.oklist.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destinations(
    val route: String,
    val args: List<NamedNavArgument> = emptyList()
)

data object InitialLogin : Destinations("initial_login")

data object CreateAccount : Destinations("create_account")

data object Login : Destinations("login")

data object TaskList : Destinations("task_list")

data object NewTask : Destinations("new_task")

data object TaskSuggestions : Destinations("task_suggestions")

data object Profile : Destinations("profile")

data object TaskDetail : Destinations(
    route = "task_detail",
    args = listOf(navArgument(name = "id") {
        type = NavType.StringType
    })
)

fun TaskDetail.createAppendedRoute(argument: String? = null): String {
    val routeWithPlaceholder = "$route/{id}"
    return argument?.let {
        "$route/$argument"
    } ?: routeWithPlaceholder
}