package com.example.omadachallengechenige.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.compose.AppTheme
import com.example.omadachallengechenige.R
import com.example.omadachallengechenige.ui.util.debugPlaceholder

@Composable
fun GridAsyncImage(photoUrl: String?, onClick: () -> Unit) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(photoUrl)
            .crossfade(true)
            .build(),
        placeholder = debugPlaceholder(R.drawable.ic_launcher_background),
        contentScale = ContentScale.Crop,
        contentDescription = "Grid Image",
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer).clickable {
                onClick.invoke()
            }
    )
}

@Preview
@Composable
fun GridAsyncImagePreview() {
    AppTheme {
        GridAsyncImage("test", {})
    }
}