package com.sample.thespacedevs.feature.spacecrafts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.material.chip.Chip
import com.sample.ds.compose.DSTheme
import com.sample.ds.compose.widget.Chip
import com.sample.thespacedevs.services.spacecraft.SpaceCraft
import com.sample.thespacedevs.services.spacecraft.Spacecraft_config
import com.sample.thespacedevs.services.spacecraft.Status

@Composable
fun SpacecraftItemWidget(
    imageUrl: String,
    name: String,
    status: String,
    text2: String,
    text3: String
) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (spaceCraftImageConstraint,
            spaceCraftNameConstraint,
            statusConstraint
        ) = createRefs()

        Image(painter = painterResource(id = R.drawable.ic_space_vehicle),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(spaceCraftImageConstraint) {
                    top.linkTo(parent.top)
                }
                .aspectRatio(1.5f)
        )
        Text(
            text = name,
            modifier = Modifier
                .padding(DSTheme.sizes.listItemPadding)
                .fillMaxWidth()
                .constrainAs(spaceCraftNameConstraint) {
                    top.linkTo(spaceCraftImageConstraint.bottom)
                },
            style = DSTheme.typography.Title
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(DSTheme.sizes.listItemPadding)
                .fillMaxWidth()
                .constrainAs(statusConstraint) {
                    top.linkTo(spaceCraftNameConstraint.bottom)
                }) {
            Chip(text = status)
            Chip(text = text2)
            Chip(text = text3)
        }
    }
}

@Preview
@Composable
fun sampleSpacecraftItemWidget() {
    SpacecraftItemWidget(
        imageUrl = "",
        name = "Mercury 7",
        status = "Active",
        text2 = "Text 2",
        text3 = "Text 3"
    )

}