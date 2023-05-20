package nl.michiel.feature.repositories

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.michiel.design.components.EmptyState
import nl.michiel.design.theme.AssignmentTheme
import nl.michiel.domain.github.entities.Owner
import nl.michiel.domain.github.entities.Repo

@Composable
fun RepoListScreen(state: RepoListState) {
    when(state) {
        RepoListState.Loading -> EmptyState(Icons.Filled.Refresh, "Loading...")
        is RepoListState.Error -> EmptyState(Icons.Filled.Warning, state.message, "Retry", state.onRetry)
        is RepoListState.Success -> if (state.repos.isEmpty()) {
            EmptyState(Icons.Filled.Search, "No results")
        } else {
            RepoList(state.repos)
        }
    }
}

@Composable
fun RepoList(repos: List<Repo>) {
    LazyColumn {
        items(repos.size, key = { repos[it].id }) { index ->
            RepoListItem(repos[index])
        }
    }
}

@Composable
fun RepoListItem(repo: Repo) {
    Text(repo.name, style = MaterialTheme.typography.bodyLarge)
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
