package com.a401.artwalk.di

import com.a401.data.datasource.remote.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindRouteRemoteDataSource(routeRemoteDataSource: RouteRemoteDataSourceImpl): RouteRemoteDataSource

    @Binds
    fun bindUserRemoteDataSource(userRemoteDataSource: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Binds
    fun bindRecordRemoteDataSource(recordRemoteDataSource: RecordRemoteDataSourceImpl): RecordRemoteDataSource
}