package com.sample.ds

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatEditText

class SearchWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(ContextThemeWrapper(context, R.style.DS_SearchWidget), attrs, defStyleAttr)