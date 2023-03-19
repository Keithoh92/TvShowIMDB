package com.example.moviedd.ui.tv.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.moviedd.R
import com.example.moviedd.ui.common.ProgressIndicator
import com.example.moviedd.ui.theme.fontSize16
import com.example.moviedd.ui.theme.size36
import com.example.moviedd.ui.theme.spacing16

@Composable
fun TvShowLoadingDialog() {
    Dialog(
        onDismissRequest = {},
        DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing16),
            shape = MaterialTheme.shapes.medium,
            backgroundColor = colorResource(id = R.color.off_white_app)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(spacing16),
                modifier = Modifier.padding(spacing16)
            ) {
                ProgressIndicator(indicatorSize = size36)

                Text(
                    text = stringResource(R.string.dialog_loading),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = fontSize16,
                    textAlign = TextAlign.Start,
                    color = Color.Gray,
                    modifier = Modifier.padding(spacing16)
                )
            }
        }
    }
}