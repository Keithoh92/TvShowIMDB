package com.example.moviedd.ui.tv.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviedd.R
import com.example.moviedd.ui.event.BaseComposeEvent
import com.example.moviedd.ui.theme.fontSize16
import com.example.moviedd.ui.theme.full
import com.example.moviedd.ui.theme.spacing16
import com.example.moviedd.ui.theme.spacing8
import com.example.moviedd.ui.tv.event.TvShowScreenEvent

@Composable
fun TvShowScreenSortingBar(
    isGridView: Boolean,
    onEvent: (BaseComposeEvent) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val sortValues = listOf(
        stringResource(R.string.alphabetically), stringResource(R.string.top_rated), stringResource(R.string.air_date)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing16, vertical = spacing8)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing8),
            modifier = Modifier
                .clickable { expanded = !expanded }
        ) {
            Text(
                text = stringResource(R.string.sort_by),
                fontSize = fontSize16,
                color = Color.Black
            )

            Icon(
                imageVector = Icons.Filled.Sort,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                sortValues.forEach {
                    DropdownMenuItem(
                        onClick = {
                            onEvent(TvShowScreenEvent.OnSortOptionChosen(it))
                            expanded = !expanded
                        }
                    ) {
                        Text(text = it, color = Color.Black)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(full))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing8),
            modifier = Modifier
                .clickable { onEvent(TvShowScreenEvent.OnViewChanged(isGridView)) }
        ) {
            Text(
                text = stringResource(R.string.view),
                fontSize = fontSize16,
                color = Color.Black
            )

            Icon(
                imageVector = if (isGridView) Icons.Outlined.Menu else Icons.Outlined.GridView,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}