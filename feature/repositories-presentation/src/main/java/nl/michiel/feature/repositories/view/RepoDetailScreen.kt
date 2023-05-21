package nl.michiel.feature.repositories.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import nl.michiel.design.components.EmptyState

@Composable
fun RepoDetailScreen(id: Int) {
    EmptyState(icon = Icons.Filled.Build, title = "Details for $id\nnot implemented")
}
