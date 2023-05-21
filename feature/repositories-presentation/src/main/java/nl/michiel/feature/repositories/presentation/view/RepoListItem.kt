package nl.michiel.feature.repositories.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.michiel.design.components.Bubble
import nl.michiel.design.theme.AssignmentTheme
import nl.michiel.feature.repositories.domain.entities.Person
import nl.michiel.feature.repositories.domain.entities.Repo
import nl.michiel.feature.repositories.R

@Composable
fun RepoListItem(repo: Repo, onClick: (() -> Unit)? = null) {
    Column(
        Modifier
            .clickable { onClick?.invoke() }
            .padding(16.dp)
    ) {
        Row {
            Text(repo.name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.weight(1f))
            Bubble(R.drawable.ic_star_16, repo.stargazersCount.toString())
            Spacer(modifier = Modifier.width(8.dp))
            Bubble(R.drawable.ic_repo_forked_16, repo.forksCount.toString())
        }
        Text(repo.description ?: "", maxLines = 1 , overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(repo.owner.name, style = MaterialTheme.typography.bodySmall)
    }
}

@Preview
@Composable
fun RepoListItemPreview() {
    AssignmentTheme {
        Surface(Modifier.width(360.dp), color = MaterialTheme.colorScheme.background) {
            RepoListItem(
                Repo(
                    1,
                    "abs.io",
                    "Simple URL shortener for ActionBarSherlock using node.js and express.",
                    9,
                    1,
                    listOf(),
                    Person(1, "JakeWharton", "https://avatars.githubusercontent.com/u/66577?v=4")
                )
            )
        }
    }
}
