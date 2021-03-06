package ru.weber.remindme.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.toArgb
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ru.weber.remindme.ui.feature.start.BottomFlowStartScreen
import ru.weber.remindme.ui.feature.task.detailed.TaskDetailedScreen
import ru.weber.remindme.ui.screens.FlowScreens
import ru.weber.remindme.ui.theme.RemindMeTheme
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: RemindMeViewModel = hiltViewModel()
            RemindMeDark(viewModel)
        }
    }
}

@Composable
private fun Activity.RemindMeDark(activityViewModel: RemindMeViewModel) {
    val stateTheme = activityViewModel.themeState.collectAsState(initial = Random.nextBoolean())
    RemindMeTheme(darkTheme = stateTheme.value) {
        window.statusBarColor = MaterialTheme.colors.primary.toArgb()
        window.navigationBarColor = MaterialTheme.colors.primary.toArgb()
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = FlowScreens.Start.screenKey
        ) {
            composable(FlowScreens.Start.screenKey) {
                BottomFlowStartScreen(mainNavController = navController)
            }

            val argTaskId = "titleTask"
            composable("${FlowScreens.Task.screenKey}/{$argTaskId}", arguments = listOf(
                navArgument(argTaskId) { type = NavType.StringType }
            )) {
                TaskDetailedScreen(navController, it.arguments?.getString(argTaskId).orEmpty())
            }
        }
    }
}

