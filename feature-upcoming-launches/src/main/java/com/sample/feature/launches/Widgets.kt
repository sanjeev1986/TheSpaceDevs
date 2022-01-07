package com.sample.feature.launches

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.sample.ds.ListItemBody
import com.sample.ds.ListItemTitle

@Composable
internal fun UpcomingLaunchItem(item:LaunchItem) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.ListItemTitle,
            modifier = Modifier
                .padding(
                    PaddingValues(
                        start = dimensionResource(
                            id = R.dimen.list_item_padding
                        ), top = dimensionResource(
                            id = R.dimen.list_item_padding
                        ),
                        end = dimensionResource(id = R.dimen.list_item_padding)
                    )
                )
        )
        Text(
            text = item.description,
            style = MaterialTheme.typography.ListItemBody,
            modifier = Modifier
                .padding(
                    PaddingValues(
                        start = dimensionResource(
                            id = R.dimen.list_item_padding
                        ), top = dimensionResource(
                            id = R.dimen.list_item_padding
                        ),
                        end = dimensionResource(id = R.dimen.list_item_padding)
                    )
                )
        )
    }
}

data class LaunchItem(val title: String, val description: String = "")