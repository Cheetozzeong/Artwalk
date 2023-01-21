package com.a401.artwalk.di

import com.a401.data.repository.RouteRepositoryImpl
import com.a401.domain.repository.RouteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindFakeRouteRepo(routeRepository: RouteRepositoryImpl): RouteRepository

}