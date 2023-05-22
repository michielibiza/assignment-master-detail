package nl.michiel.design.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.michiel.design.theme.AssignmentTheme
import nl.michiel.design.theme.Blue

@Composable
fun Bubble(icon: ImageVector, text: String) {
    Bubble(icon = rememberVectorPainter(icon), text = text)
}

@Composable
fun Bubble(@DrawableRes icon: Int, text: String) {
    Bubble(icon = painterResource(icon), text = text)
}

@Composable
fun Bubble(icon: Painter, text: String) {
    Row(
        Modifier
            .clip(MaterialTheme.shapes.large)
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(8.dp, 4.dp)) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSecondary)
        Text(text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondary)
    }
}

@Composable
fun Bubble(text: String) {
    Row(
        Modifier
            .clip(MaterialTheme.shapes.large)
            .background(color = Blue)
            .padding(8.dp, 4.dp)) {
        Text(text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondary)
    }
}

@Preview
@Composable
fun BubblePreview() {
    AssignmentTheme {
        Bubble(icon = Icons.Filled.Star, text = "1")
    }
}

@Preview
@Composable
fun BubblePreviewAsset() {
    AssignmentTheme {
        Bubble(icon = androidx.core.R.drawable.ic_call_answer, text = "1")
    }
}

@Preview
@Composable
fun TextBubblePreview() {
    AssignmentTheme {
        Bubble(text = "topic")
    }
}
