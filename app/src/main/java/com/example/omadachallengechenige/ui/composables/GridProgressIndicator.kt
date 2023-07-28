package com.example.omadachallengechenige.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.AppTheme

@Composable
fun GridProgressIndicator() {
    Box(
        modifier = Modifier.aspectRatio(1f),) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GridProgressIndicatorPreview() {
    AppTheme {
        GridProgressIndicator()
    }
}