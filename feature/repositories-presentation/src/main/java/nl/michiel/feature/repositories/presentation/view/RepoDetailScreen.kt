package nl.michiel.feature.repositories.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import nl.michiel.design.components.Bubble
import nl.michiel.design.components.EmptyState
import nl.michiel.design.theme.AssignmentTheme
import nl.michiel.design.theme.LightBlue1
import nl.michiel.feature.repositories.domain.MockRepoRepository
import nl.michiel.feature.repositories.domain.entities.Event
import nl.michiel.feature.repositories.domain.entities.Repo
import nl.michiel.feature.repositories.presentation.viewmodel.RepoDetailViewModel
import nl.michiel.feature.repositories.R

@Composable
fun RepoDetailScreen(id: Int) {
    // TODO inject viewmodel
    val viewModel = remember { RepoDetailViewModel(MockRepoRepository()) }
    val repo by viewModel.getRepo(id).collectAsState(initial = null)
    val events by viewModel.getEvents(id).collectAsState(initial = emptyList())
    if (repo == null) {
        EmptyState(icon = Icons.Filled.Refresh, title = "Loading...")
    } else {
        RepoDetailScreen(repo!!, events)
    }
}

@Composable
fun RepoDetailScreen(repo: Repo, events: List<Event>) {
    LazyColumn {
        item {
            RepoDetails(repo)
        }
        item {
            Text(
                stringResource(id = R.string.details_history),
                Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        items(events.size, key = { events[it].id }) { index ->
            Divider(color = LightBlue1)
            EventItem(events[index])
        }
    }
}

@Composable
private fun RepoDetails(repo: Repo) {
    Column(
        Modifier.padding(16.dp, 24.dp)
    ) {
        Row {
            Text(repo.name, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.weight(1f))
            Bubble(Icons.Filled.Star, repo.stargazersCount.toString())
            Spacer(Modifier.width(8.dp))
            Bubble(R.drawable.ic_repo_forked_16, repo.forksCount.toString())
        }
        Spacer(Modifier.height(8.dp))
        Text(repo.description ?: "", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                repo.owner.avatarUrl, "avatar",
                Modifier
                    .size(24.dp)
                    .clip(MaterialTheme.shapes.large)
            )
            Spacer(Modifier.width(8.dp))
            Text(repo.owner.login, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun EventItem(event: Event) {
    Row(
        Modifier.padding(16.dp)
    ) {
        EventIcon(event.type)
        Spacer(Modifier.width(8.dp))
        Text(event.type, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.weight(1f))
        Text(event.actor.login, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.width(8.dp))
        AsyncImage(
            event.actor.avatarUrl, "avatar",
            Modifier
                .size(24.dp)
                .clip(MaterialTheme.shapes.large)
        )
    }
}

@Composable
fun EventIcon(type: String) {
    val icon = when (type) {
        "PushEvent" -> R.drawable.ic_repo_push_24
        "CreateEvent" -> R.drawable.ic_upload_24
        "WatchEvent" -> R.drawable.ic_star_16
        "IssuesEvent" -> R.drawable.ic_comment_24
        "IssueCommentEvent" -> R.drawable.ic_comment_24
        "ForkEvent" -> R.drawable.ic_repo_forked_16
        else -> R.drawable.ic_github_16
    }
    Icon(painterResource(icon), "", Modifier.size(24.dp))
}
@Preview(showBackground = true)
@Composable
fun RepoDetailPreview() {
    val mocks = MockRepoRepository()
    AssignmentTheme {
        RepoDetailScreen(
            mocks.repoData[0],
            mocks.eventData
        )
    }
}
