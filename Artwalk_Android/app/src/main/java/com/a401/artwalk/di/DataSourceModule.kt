package com.a401.artwalk.di

import com.a401.data.datasource.remote.RouteRemoteDataSource
import com.a401.data.datasource.remote.RouteRemoteDataSourceImpl
import com.a401.data.datasource.remote.UserRemoteDataSource
import com.a401.data.datasource.remote.UserRemoteDataSourceImpl
import com.a401.data.repository.UserRepositoryImpl
import com.a401.domain.repository.UserRepository
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

}