package com.a401.artwalk.di

import com.a401.data.repository.RecordRepositoryImpl
import com.a401.data.repository.RouteRepositoryImpl
import com.a401.data.repository.UserRepositoryImpl
import com.a401.domain.repository.RecordRepository
import com.a401.domain.repository.RouteRepository
import com.a401.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindRouteRepository(routeRepository: RouteRepositoryImpl): RouteRepository

    @Binds
    fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository

    @Binds
    fun bindRecordRepository(recordRepository: RecordRepositoryImpl): RecordRepository
}