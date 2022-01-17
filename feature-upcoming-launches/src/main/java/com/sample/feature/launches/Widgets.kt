package com.sample.feature.launches

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.sample.ds.compose.DSTheme

@Composable
internal fun UpcomingLaunchItem(item: LaunchListItem, onClick: () -> Unit) {
    Column(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clickable { onClick() }) {
        Text(
            text = item.missionName,
            style = DSTheme.typography.ListItemTitle,
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
            text = item.missionDescription,
            style = DSTheme.typography.ListItemBody,
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