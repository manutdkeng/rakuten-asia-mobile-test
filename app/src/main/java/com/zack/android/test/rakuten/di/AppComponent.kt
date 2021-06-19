package com.zack.android.test.rakuten.di

import android.content.Context
import com.zack.android.test.rakuten.MyApplication
import com.zack.android.test.rakuten.api.di.NetworkModule
import com.zack.android.test.rakuten.ui.repository.RepoActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelBuilderModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context, networkModule: NetworkModule): AppComponent
    }

    fun inject(application: MyApplication)
    fun inject(activity: RepoActivity)
}