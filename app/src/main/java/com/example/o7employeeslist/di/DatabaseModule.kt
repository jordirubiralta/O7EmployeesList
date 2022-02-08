package com.example.o7employeeslist.di

import android.content.Context
import androidx.room.Room
import com.example.o7employeeslist.db.AppDataBase
import com.example.o7employeeslist.db.EmployeeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideEmployeeDao(appDataBase: AppDataBase): EmployeeDao {
        return appDataBase.employeeDao()
    }

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDataBase::class.java,
        "employees"
    ).build()
}
