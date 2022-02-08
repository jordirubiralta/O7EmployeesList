package com.example.o7employeeslist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.o7employeeslist.model.EmployeeModel

@Database(entities = [EmployeeModel::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}
