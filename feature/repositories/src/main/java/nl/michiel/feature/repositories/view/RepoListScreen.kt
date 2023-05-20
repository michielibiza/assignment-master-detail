package nl.michiel.feature.repositories.view

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.michiel.design.components.EmptyState
import nl.michiel.design.theme.AssignmentTheme
import nl.michiel.design.theme.LightBlue1
import nl.michiel.domain.github.entities.Owner
import nl.michiel.domain.github.entities.Repo
import nl.michiel.feature.repositories.R
import nl.michiel.feature.repositories.viewmodel.RepoListState

@Composable
fun RepoListScreen() {
    //TODO get viewModel
    val state = RepoListState.Success(
        List(5) { i ->
            Repo(i, "repo $i", "description $i", i+3, i, emptyList(), Owner(1, "author $i", "https://randomuser.me/api/portraits/thumb/men/$i.jpg"))
        }
    )
    RepoListScreen(state)
}

@Composable
fun RepoListScreen(state: RepoListState) {
    when(state) {
        RepoListState.Loading -> EmptyState(Icons.Filled.Refresh, stringResource(id = R.string.screen_state_loading))
        is RepoListState.Error -> EmptyState(Icons.Filled.Warning, state.message, stringResource(id = R.string.screen_state_retry), state.onRetry)
        is RepoListState.Success -> if (state.repos.isEmpty()) {
            EmptyState(Icons.Filled.Search, stringResource(id = R.string.screen_state_empty))
        } else {
            RepoList(state.repos)
        }
    }
}

@Composable
fun RepoList(repos: List<Repo>) {
    LazyColumn {
        items(repos.size, key = { repos[it].id }) { index ->
            if (index > 0) {
                Divider(color = LightBlue1)
            }
            RepoListItem(repos[index])
        }
    }
}


@Composable
fun test(state: RepoListState) {
    AssignmentTheme {
        Surface(modifier = Modifier.size(320.dp), color = MaterialTheme.colorScheme.background) {
            RepoListScreen(state)
        }
    }
}

@Preview
@Composable
fun RepoListScreenPreviewLoading() {
    test(RepoListState.Loading)
}

@Preview
@Composable
fun RepoListScreenPreviewEmpty() {
    test(RepoListState.Success(emptyList()))
}

@Preview
@Composable
fun RepoListScreenPreviewList() {
    val owner = Owner(1, "login", "avatarUrl")
    val data = listOf(
        Repo(1, "repo1", "description1", 1, 1, emptyList(), owner),
        Repo(2, "repo2", "description2", 2, 2, emptyList(), owner),
    )
    test(RepoListState.Success(data))
}

@Preview
@Composable
fun RepoListScreenPreviewError() {
    test(RepoListState.Error("Something went wrong") {})
}
