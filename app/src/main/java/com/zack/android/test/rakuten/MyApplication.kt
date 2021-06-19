package com.zack.android.test.rakuten

import android.app.Application
import com.zack.android.test.rakuten.api.di.NetworkModule
import com.zack.android.test.rakuten.di.AppComponent
import com.zack.android.test.rakuten.di.DaggerAppComponent

class MyApplication : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = initializeAppComponent()
        appComponent.inject(this)
    }

    private fun initializeAppComponent(): AppComponent {
        val baseUrl = getString(R.string.base_url)
        return DaggerAppComponent.factory().create(applicationContext, NetworkModule(baseUrl))
    }
}