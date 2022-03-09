package com.sample.ds.compose.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.ds.compose.*

@Composable
fun Chip(
    text: String,
    enableToggle: Boolean = false,
    textColor: Color = platformBlack,
    backgroundColor: Color = platformWhite,
    isToggleOn: Boolean = false,
    onToggleListener: (Boolean) -> Unit = {}
) {
    Surface(
        elevation = DSTheme.elevation.raised,
        shape = DSTheme.shape.roundedCornerWidget,
        color = if (enableToggle) DSTheme.colors.accent else backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(DSTheme.sizes.widgetPadding)
                .toggleable(
                    value = isToggleOn,
                    onValueChange = onToggleListener
                )
        ) {
            Text(
                text = text,
                color = textColor,
                style = DSTheme.typography.Widget,
            )
        }
    }
}

@Preview()
@Composable
fun SimpleChip() {
    Chip("Hello")
}