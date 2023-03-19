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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviedd.R
import com.example.moviedd.domain.model.ShowInfo
import com.example.moviedd.ui.event.BaseComposeEvent
import com.example.moviedd.ui.theme.*
import com.example.moviedd.ui.tv.event.TvShowScreenEvent
import com.example.moviedd.ui.tv.mockData.mockTvShowList

@Composable
fun TvShowVerticalCardView(
    tvShow: ShowInfo,
    descriptionMinimised: Map<Int, Boolean>,
    onEvent: (BaseComposeEvent) -> Unit
) {
    val minimised = descriptionMinimised[tvShow.id]

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {

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
                    .fillMaxHeight()
                    .border(BorderStroke(width = 1.dp, color = Color.LightGray)),
                contentScale = ContentScale.FillBounds
            )
            Column {
                Text(
                    text = tvShow.title,
                    fontSize = fontSize22,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                )

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

                    Spacer(modifier = Modifier.weight(full))

                    Text(text = "${stringResource(id = R.string.first_aired_on)} ${tvShow.airDate}", fontSize = fontSize12, color = Color.Gray)
                }
            }

            AnimatedVisibility(visible = minimised != true) {
                Row {
                    Text(text = tvShow.description, fontSize = fontSize12, color = Color.Gray)
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TvShowVerticalCardViewPreview() {
    AppTheme {
        TvShowVerticalCardView(tvShow = mockTvShowList().first(), descriptionMinimised = emptyMap(), onEvent = {})
    }
}