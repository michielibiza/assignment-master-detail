package nl.michiel.design.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.content.ContextCompat.startActivity
import nl.michiel.design.theme.Blue

@Composable
fun UrlText(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    val context = LocalContext.current

    Text(
        url,
        Modifier.clickable { startActivity(context, intent, null) },
        color = Blue,
        textDecoration = TextDecoration.Underline,
        style = MaterialTheme.typography.bodySmall
    )
}
