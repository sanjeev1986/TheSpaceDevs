package com.sample.platform.utils

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData

class LaunchCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
    CountDownTimer(millisInFuture, countDownInterval) {
    val timerLiveData = MutableLiveData<Long>()
    override fun onTick(p0: Long) {
        timerLiveData.value = p0
    }

    override fun onFinish() {}
}