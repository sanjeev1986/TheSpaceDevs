package com.sample.thespacedevs.feature.vehicles.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sample.ds.compose.DSTheme
import com.sample.ds.compose.widget.Chip
import com.sample.thespacedevs.feature.spacecrafts.R

@Composable
fun SpacecraftItemWidget(
    imageUrl: String?,
    name: String,
    status: String,
    description: String,
    text3: String,
    onClick: () -> Unit
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        val (spaceCraftImageConstraint,
            spaceCraftNameConstraint,
            statusConstraint,
            descriptionConstraint
        ) = createRefs()

        AsyncImage(
            placeholder = painterResource(id = R.drawable.ic_space_vehicle),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .requiredSize(100.dp, 80.dp)
                .constrainAs(spaceCraftImageConstraint) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                })
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(DSTheme.sizes.listItemPadding)
                .fillMaxWidth()
                .constrainAs(statusConstraint) {
                    top.linkTo(parent.top)
                    start.linkTo(spaceCraftImageConstraint.end)
                }) {
            Chip(text = status)
            Chip(text = text3)
        }
        Text(
            text = name,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .constrainAs(spaceCraftNameConstraint) {
                    top.linkTo(statusConstraint.bottom)
                    start.linkTo(spaceCraftImageConstraint.end)
                }
                .padding(DSTheme.sizes.listItemPadding),
            style = DSTheme.typography.Title
        )

        Text(
            text = description,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .constrainAs(descriptionConstraint) {
                    top.linkTo(spaceCraftImageConstraint.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(DSTheme.sizes.listItemPadding),
            style = DSTheme.typography.ListItemBody
        )


    }
}

@Preview
@Composable
fun sampleSpacecraftItemWidget() {
    SpacecraftItemWidget(
        imageUrl = "",
        name = "Mercury 7",
        status = "Active",
        description = "Text 2",
        text3 = "Text 3",
        onClick = {}
    )
}