package com.sample.thespacedevs.utils

import android.os.CountDownTimer

class LaunchCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
    CountDownTimer(millisInFuture, countDownInterval) {
    var onTick: ((Long) -> Unit)? = null
    override fun onTick(p0: Long) {
        onTick(p0)
    }

    override fun onFinish() {
        onTick = null
    }
}