package com.example.task.mainactivity.di

import com.example.task.mainactivity.BuildConfig
import com.example.task.mainactivity.data.EmployeesRepositoryImpl
import com.example.task.mainactivity.domain.EmployeesRepository
import com.example.task.mainactivity.domain.EmployeesUseCaseImpl
import com.example.task.mainactivity.domain.ProfileUseCaseImpl
import com.example.task.mainactivity.network.EmployeesApi
import com.example.task.mainactivity.ui.EmployeesUseCase
import com.example.task.mainactivity.ui.ProfileUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class EmployeesViewModelModule {

    @Binds
    abstract fun bindRepository(
        employeesRepositoryImpl: EmployeesRepositoryImpl
    ): EmployeesRepository

    @Binds
    abstract fun bindEmployeeUseCase(
        useCaseImpl: EmployeesUseCaseImpl
    ): EmployeesUseCase

    @Binds
    abstract fun bindProfileUseCase(
        useCaseImpl: ProfileUseCaseImpl
    ): ProfileUseCase

}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApi() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideApiService() = provideApi().create(EmployeesApi::class.java)
}