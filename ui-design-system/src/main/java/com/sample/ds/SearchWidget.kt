package com.sample.ds

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatEditText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource

class SearchWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(ContextThemeWrapper(context, R.style.DS_SearchWidget), attrs, defStyleAttr)

@Composable
fun SearchWidget(callback: (String) -> Unit) {
    var contentText: String by remember { mutableStateOf("") }
    OutlinedTextField(
        value = contentText,
        onValueChange = {
            contentText = it
            callback(it)
        },
        modifier = Modifier
            .background(
                color = platformWhite,
            )
            .padding(horizontal = dimensionResource(id = R.dimen.screen_margin))
            .fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = platformWhite,
            unfocusedBorderColor = platformWhite
        ),
    )
}