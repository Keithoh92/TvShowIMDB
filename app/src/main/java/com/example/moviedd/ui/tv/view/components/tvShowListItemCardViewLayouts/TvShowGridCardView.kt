package com.example.moviedd.ui.tv.view.components.tvShowListItemCardViewLayouts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviedd.R
import com.example.moviedd.domain.model.ShowInfo
import com.example.moviedd.ui.event.BaseComposeEvent
import com.example.moviedd.ui.theme.*
import com.example.moviedd.ui.tv.event.TvShowScreenEvent

@Composable
fun TvShowGridCardView(
    tvShow: ShowInfo,
    descriptionMinimised: Map<Int, Boolean>,
    onEvent: (BaseComposeEvent) -> Unit
) {
    val minimised = descriptionMinimised[tvShow.id]

    Column(
        verticalArrangement = Arrangement.spacedBy(spacing8),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(horizontal = spacing16, vertical = spacing8)
    ) {
        AsyncImage(
            model = tvShow.imagePath,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .border(BorderStroke(width = 1.dp, color = Color.LightGray)),
            contentScale = ContentScale.FillBounds
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Column {
                Column(modifier = Modifier.height(40.dp)) {
                    Text(
                        text = tvShow.title,
                        fontSize = fontSize16,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }

                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.clickable {
                        onEvent(TvShowScreenEvent.OnDescriptionClicked(tvShow.id, minimised))
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            onEvent(TvShowScreenEvent.OnDescriptionClicked(tvShow.id, minimised))
                        }
                    ) {
                        Text(text = stringResource(R.string.description), fontSize = fontSize12, color = Color.Gray)

                        IconButton(onClick = { onEvent(TvShowScreenEvent.OnDescriptionClicked(tvShow.id, minimised)) }) {
                            if (minimised == true) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropUp,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        }
                    }

                    Text(text = "${stringResource(id = R.string.first_aired_on)} ${tvShow.airDate}", fontSize = fontSize12, color = Color.Gray)
                }
            }
        }

        AnimatedVisibility(visible = minimised != true) {
            Row {
                Text(text = tvShow.description, fontSize = fontSize12, color = Color.Gray)
            }
        }
    }
}