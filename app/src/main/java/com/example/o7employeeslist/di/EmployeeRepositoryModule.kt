package com.example.o7employeeslist.di

import com.example.o7employeeslist.repository.EmployeeRepository
import com.example.o7employeeslist.repository.EmployeeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class EmployeeRepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindPersonajeRepository(
        repositoryImpl: EmployeeRepositoryImpl
    ): EmployeeRepository
}
