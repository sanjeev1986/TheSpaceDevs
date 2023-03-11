package com.sample.ds.compose.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sample.ds.R
import com.sample.ds.compose.DSTheme
import kotlinx.coroutines.launch

@Composable
fun ErrorWidget(displayState: Boolean, message: String, onDismiss: () -> Unit) {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = displayState,
        enter = slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            expandFrom = Alignment.Bottom,
        ),

        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(DSTheme.sizes.widgetPadding),
        ) {
            val (
                messageConstraint,
                actionConstraint,
            ) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.ic_close_24),
                contentDescription = "close",
                modifier = Modifier
                    .constrainAs(actionConstraint) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable {
                        scope.launch {
                            onDismiss()
                        }
                    },
            )
            Text(
                text = message,
                modifier = Modifier.constrainAs(messageConstraint) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(actionConstraint.start)
                },
            )
        }
    }
}
