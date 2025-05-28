package com.fuchsia.mymedicaladmin.di

import com.fuchsia.mymedicaladmin.api.ApiBuilder
import com.fuchsia.mymedicaladmin.repo.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class HiltModule {

    @Provides
    @Singleton

    fun provideApiService(): ApiBuilder {
        return ApiBuilder

    }

    @Provides
    @Singleton
    fun apiService(apiService: ApiBuilder): Repo {
        return Repo(apiService)

    }


}