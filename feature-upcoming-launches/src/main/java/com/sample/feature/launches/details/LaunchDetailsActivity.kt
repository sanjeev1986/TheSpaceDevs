package com.sample.feature.launches.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sample.feature.launches.list.LaunchRepository
import dagger.android.AndroidInjection
import javax.inject.Inject

class LaunchDetailsActivity : AppCompatActivity() {

    companion object {
        private val EXTRA_LAUNCH_ID =
            "com.sample.feature.launches.details.LaunchDetailsActivity.EXTRA_LAUNCH_ID"

        fun createLaunchDetailsIntent(id: String, context: Context): Intent {
            return Intent(context, LaunchDetailsActivity::class.java).apply {
                putExtra(
                    EXTRA_LAUNCH_ID,
                    id
                )
            }
        }
    }

    @Inject
    lateinit var repository: LaunchRepository
    private val viewModel by viewModels<LaunchDetailsViewModel> {
        LaunchDetailsViewModel.Factory(
            intent.getStringExtra(EXTRA_LAUNCH_ID)!!,
            repository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContent {
            LaunchDetails(viewModel)
        }
    }
}