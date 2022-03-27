package com.sample.thespacedevs

import com.sample.thespacedevs.di.DaggerTestApplicationComponent

class TheSpaceDevTestApp : TheSpaceDevApp() {
    override fun createDaggerComponent(): DaggerComponent {
        return DaggerTestApplicationComponent.builder().application(this).context(this).build()
    }
}