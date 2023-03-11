package com.sample.ds

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.interpolator.view.animation.FastOutLinearInInterpolator

class ErrorWidget(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    val messageText: TextView
    val actionButton: ImageView
    val animStrategy: AnimStrategy = SlideDownReveal()

    init {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.layout_error_widget, this@ErrorWidget, true)
        messageText = view.findViewById(R.id.error_message)
        actionButton = view.findViewById(R.id.action_button)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ErrorWidget,
            0,
            0,
        ).apply {
            try {
                val message = getString(R.styleable.ErrorWidget_text)
                val drawable = getDrawable(R.styleable.ErrorWidget_icon)
                    ?: resources.getDrawable(R.drawable.ic_close_24, null)
                val colorTint = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        getResourceId(
                            R.styleable.ErrorWidget_actionIconTint,
                            R.color.white,
                        ),
                    ),
                )
                visibility = View.GONE
                messageText.text = message
                messageText.setTextColor(colorTint)
                ImageViewCompat.setImageTintList(actionButton, colorTint)
                actionButton.setImageDrawable(drawable)
                actionButton.setOnClickListener {
                    dismiss()
                }
            } finally {
                recycle()
            }
        }
    }

    fun show() {
        if (visibility != View.VISIBLE) {
            animStrategy.show()
        }
    }

    fun dismiss() {
        if (visibility == View.VISIBLE) {
            animStrategy.dismiss()
        }
    }

    interface AnimStrategy {
        fun show()
        fun dismiss()
    }

    private inner class SlideDownReveal : AnimStrategy {
        override fun show() {
            translationY = -height.toFloat()
            visibility = View.VISIBLE
            animate().translationY(0f).setDuration(300).setListener(null).start()
        }

        override fun dismiss() {
            animate().translationY(-height.toFloat()).setInterpolator(FastOutLinearInInterpolator())
                .setDuration(300).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        visibility = View.GONE
                        translationY = 0f
                    }
                }).start()
        }
    }

    private inner class SlideUpReveal : AnimStrategy {
        override fun show() {
            translationY = height.toFloat()
            visibility = View.VISIBLE
            animate().translationY(0f).setDuration(300).setListener(null).start()
        }

        override fun dismiss() {
            animate().translationY(height.toFloat()).setInterpolator(FastOutLinearInInterpolator())
                .setDuration(300).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        visibility = View.GONE
                        translationY = 0f
                    }
                }).start()
        }
    }

    private inner class AlphaReveal : AnimStrategy {
        override fun show() {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).setDuration(300).setListener(null).start()
        }

        override fun dismiss() {
            animate().alpha(0f).setInterpolator(FastOutLinearInInterpolator()).setDuration(300)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        visibility = View.GONE
                    }
                }).start()
        }
    }
}
