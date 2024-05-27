package com.bytedance.android.live.pipo.pbl6_test.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.text.TextUtils
import androidx.annotation.MainThread

/**
 * Created by NatalieWong on 23/5/24
 * @author nat.wong@bytedance.com
 */
@SuppressLint("CI_StaticFieldLeak", "StaticFieldLeak")
object AppContextManager {
    @Volatile
    private lateinit var context: Application

    @MainThread
    fun init(application: Application) {
        this.context = application
    }

    fun getApplicationContext(): Context {
        return context
    }
}