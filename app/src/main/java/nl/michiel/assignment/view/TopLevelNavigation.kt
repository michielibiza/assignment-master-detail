package nl.michiel.assignment.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import nl.michiel.assignment.R
import nl.michiel.design.theme.AssignmentTheme
import nl.michiel.feature.repositories.presentation.view.RepoDetailScreen
import nl.michiel.feature.repositories.presentation.view.RepoListScreen
import timber.log.Timber

object Routes {
    private const val DETAIL_ARG_NAME = "id"
    const val DETAIL_BASE = "repoDetail"

    const val LIST = "repoList"
    const val DETAIL = "$DETAIL_BASE/{$DETAIL_ARG_NAME}"

    val detailArgs = listOf(navArgument(DETAIL_ARG_NAME) { type = NavType.IntType })

    fun detail(id: Int) = "$DETAIL_BASE/$id"
}

enum class Screen(
    @StringRes val title: Int,
) {
    LIST(R.string.screen_list_title),
    DETAIL(R.string.screen_detail_title),

    ;

    companion object {
        fun fromRoute(route: String?): Screen? = when (route?.substringBefore("/")) {
            Routes.LIST -> LIST.also { Timber.d("list") }
            Routes.DETAIL_BASE -> DETAIL.also { Timber.d("detail") }
            else -> null
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopLevelNavigation() {
    val navController = rememberNavController()
    val titleRes = navController.currentBackStackEntryAsState().value?.destination?.route
        ?.let { route -> Screen.fromRoute(route)?.title }
        ?: R.string.app_name

    AssignmentTheme {
        Scaffold(
            topBar = { AppBar(titleRes, navController) }
        ) { padding ->
            Surface(
                modifier = Modifier.fillMaxSize().padding(padding),
                color = MaterialTheme.colorScheme.background,
                ) {
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
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AppBar(titleRes: Int, navController: androidx.navigation.NavController) {
    TopAppBar(
        title = { Text(text = stringResource(id = titleRes)) },
        colors = with(MaterialTheme.colorScheme) {
            topAppBarColors(primary, primary, onPrimary, onPrimary, onPrimary)
        },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            } else {
                null
            }
        },
    )
}
