package nl.michiel.feature.repositories.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import nl.michiel.feature.repositories.R
import nl.michiel.feature.repositories.domain.MockRepoRepository
import nl.michiel.feature.repositories.domain.entities.Event
import nl.michiel.feature.repositories.domain.entities.Repo
import nl.michiel.feature.repositories.presentation.viewmodel.EventsState
import nl.michiel.feature.repositories.presentation.viewmodel.RepoDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RepoDetailScreen(
    id: Long,
    viewModel: RepoDetailViewModel = koinViewModel()
) {
    val repo by viewModel.getRepo(id).collectAsState(initial = null)
    val events by viewModel.getEvents(id).collectAsState(initial = EventsState.Loading)
    repo?.let { repo ->
        RepoDetailScreen(repo, events)
    } ?: EmptyState(Icons.Filled.Refresh, stringResource(id = R.string.screen_state_loading))
}

@Composable
fun RepoDetailScreen(repo: Repo, eventsState: EventsState) {
    LazyColumn {
        item {
            RepoDetails(repo)
        }
        eventsList(eventsState)
    }
}

fun LazyListScope.eventsList(eventsState: EventsState) {

    when (eventsState) {
        is EventsState.Loading -> item { EventsLoading() }
        is EventsState.Error -> item { EventsError(eventsState.message) }
        is EventsState.Success -> {
            val events = eventsState.events
            if (events.isEmpty()) {
                item {
                    Text(
                        stringResource(id = R.string.details_history_empty),
                        Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                item {
                    Text(
                        stringResource(id = R.string.details_history_title),
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
    }
}

@Composable
fun EventsLoading() {
    Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        CircularProgressIndicator(Modifier.size(24.dp))
        Spacer(Modifier.width(8.dp))
        Text(stringResource(id = R.string.details_history_loading))
    }
}

@Composable
fun EventsError(error: String) {
    Row(Modifier.padding(16.dp)) {
        Icon(
            Icons.Filled.Warning,
            contentDescription = "",
            Modifier
                .size(24.dp)
                .clip(MaterialTheme.shapes.small),
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.width(8.dp))
        Text(error)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RepoDetails(repo: Repo) {
    Column(
        Modifier.padding(16.dp, 24.dp)
    ) {
        Text(repo.name, style = MaterialTheme.typography.titleLarge)
        if (!repo.description.isNullOrBlank()) {
            Spacer(Modifier.height(8.dp))
            Text(repo.description!!, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                repo.owner.avatarUrl, "avatar",
                Modifier
                    .size(24.dp)
                    .clip(MaterialTheme.shapes.large)
            )
            Spacer(Modifier.width(8.dp))
            Text(repo.owner.name, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(Modifier.height(8.dp))
        FlowRow {
            Pad { Bubble(Icons.Filled.Star, repo.stargazersCount.toString()) }
            Pad { Bubble(R.drawable.ic_repo_forked_16, repo.forksCount.toString()) }
            repo.topics
                .filter { it.isNotBlank() }
                .forEach { topic ->
                    Pad { Bubble(topic) }
                }
        }
    }
}

@Composable
fun Pad(content: @Composable () -> Unit) {
    Box(Modifier.padding(end = 8.dp, bottom = 4.dp)) {
        content()
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
        Text(event.actor.name, style = MaterialTheme.typography.bodyMedium)
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
        "IssuesEvent" -> R.drawable.ic_issue_opened_16
        "IssueCommentEvent" -> R.drawable.ic_comment_24
        "ForkEvent" -> R.drawable.ic_repo_forked_16
        else -> R.drawable.ic_github_16
    }
    Icon(painterResource(icon), "", Modifier.size(24.dp))
}

@Preview(showBackground = true)
@Composable
fun RepoDetailLoadingPreview() {
    val mocks = MockRepoRepository()
    AssignmentTheme {
        RepoDetailScreen(
            mocks.repoData[0],
            EventsState.Loading,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RepoDetailErrorPreview() {
    val mocks = MockRepoRepository()
    AssignmentTheme {
        RepoDetailScreen(
            mocks.repoData[0],
            EventsState.Error("Something went wrong"),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RepoDetailEmptyPreview() {
    val mocks = MockRepoRepository()
    AssignmentTheme {
        RepoDetailScreen(
            mocks.repoData[0],
            EventsState.Success(emptyList()),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RepoDetailPreview() {
    val mocks = MockRepoRepository()
    AssignmentTheme {
        RepoDetailScreen(
            mocks.repoData[0],
            EventsState.Success(mocks.eventData),
        )
    }
}
