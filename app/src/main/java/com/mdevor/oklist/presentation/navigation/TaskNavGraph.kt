package com.mdevor.oklist.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mdevor.oklist.presentation.screens.detailtask.DetailTaskScreen
import com.mdevor.oklist.presentation.screens.detailtask.DetailTaskViewModel
import com.mdevor.oklist.presentation.screens.login.InitialLoginScreen
import com.mdevor.oklist.presentation.screens.login.createaccount.CreateAccountScreen
import com.mdevor.oklist.presentation.screens.login.login.LoginScreen
import com.mdevor.oklist.presentation.screens.profile.ProfileScreen
import com.mdevor.oklist.presentation.screens.taskhome.TaskHomeScreen
import com.mdevor.oklist.presentation.screens.tasksuggestions.TaskSuggestionsScreen

@Composable
fun TaskNavGraph(
    navController: NavHostController,
    navigationViewState: StartNavigationUiState,
) {
    NavHost(
        navController = navController,
        startDestination = navigationViewState.startDestination.route
    ) {
        composable(InitialLogin.route) {
            InitialLoginScreen(
                onLoginClick = { navController.navigate(Login.route) },
                onCreateAccountClick = { navController.navigate(CreateAccount.route) }
            )
        }

        composable(CreateAccount.route) {
            CreateAccountScreen(
                viewModel = hiltViewModel(),
                onRegisterClick = {
                    navController.navigate(
                        route = Login.route,
                        builder = {
                            popUpTo(InitialLogin.route) {
                                inclusive = true
                            }
                        }
                    )
                },
                onBackClick = { navController.popBackStack() },
                onSignInWithGoogle = {
                    navController.navigate(
                        route = TaskList.route,
                        builder = {
                            popUpTo(InitialLogin.route) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }

        composable(Login.route) {
            LoginScreen(
                viewModel = hiltViewModel(),
                onLoginClick = {
                    navController.navigate(
                        route = TaskList.route,
                        builder = {
                            popUpTo(InitialLogin.route) {
                                inclusive = true
                            }
                        }
                    )
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(TaskSuggestions.route) {
            TaskSuggestionsScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(Profile.route) {
            ProfileScreen(
                viewModel = hiltViewModel(),
                onBackClick = { navController.navigateUp() },
                onLogoutClick = {
                    navController.navigate(InitialLogin.route) {
                        popUpTo(TaskList.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(TaskList.route) {
            TaskHomeScreen(
                viewModel = hiltViewModel(),
                onPlusClick = { navController.navigate(NewTask.route) },
                onTaskClick = { taskUuid ->
                    navController.navigate(TaskDetail.createAppendedRoute(taskUuid))
                },
                onNotificationClick = { navController.navigate(TaskSuggestions.route) },
                onProfileClick = { navController.navigate(Profile.route) },
            )
        }

        composable(NewTask.route) {
            DetailTaskScreen(viewModel = hiltViewModel()) {
                navController.navigateUp()
            }
        }

        composable(
            route = TaskDetail.createAppendedRoute(),
            arguments = TaskDetail.args
        ) {
            DetailTaskScreen(
                viewModel = hiltViewModel<DetailTaskViewModel>(),
            ) {
                navController.navigateUp()
            }
        }
    }
}
