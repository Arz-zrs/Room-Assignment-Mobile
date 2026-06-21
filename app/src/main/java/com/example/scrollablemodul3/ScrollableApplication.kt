package com.example.scrollablemodul3

import android.app.Application
import com.example.scrollablemodul3.data.AppContainer
import com.example.scrollablemodul3.data.DefaultAppContainer

class ScrollableApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
