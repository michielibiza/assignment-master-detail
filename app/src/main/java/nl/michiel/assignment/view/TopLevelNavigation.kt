package nl.michiel.assignment.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.michiel.design.theme.AssignmentTheme
import nl.michiel.feature.repositories.view.RepoDetailScreen
import nl.michiel.feature.repositories.view.RepoListScreen

object Routes {
    private const val DETAIL_ARG_NAME = "id"
    private const val DETAIL_BASE = "repoDetail"

    const val LIST = "repoList"
    const val DETAIL = "$DETAIL_BASE/{$DETAIL_ARG_NAME}"

    val detailArgs = listOf(navArgument(DETAIL_ARG_NAME) { type = NavType.IntType })

    fun detail(id: Int) = "$DETAIL_BASE/$id"
}

@Composable
fun TopLevelNavigation() {
    val navController = rememberNavController()
    AssignmentTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            NavHost(navController, startDestination = Routes.LIST) {
                composable(Routes.LIST) {
                    RepoListScreen(onRepoClick = { id -> navController.navigate(Routes.detail(id)) })
                }
                composable(Routes.DETAIL, arguments = Routes.detailArgs) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: throw IllegalStateException("Missing id")
                    RepoDetailScreen(id)
                }
            }
        }
    }
}
