package com.example.pagingsample

import com.example.paging2sample.MainActivityLauncher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface LauncherModule {

    @Binds
    fun bindsMainActivityLauncher(mainActivityLauncher: MainActivityLauncherImpl): MainActivityLauncher

}