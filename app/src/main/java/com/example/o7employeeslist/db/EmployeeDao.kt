package com.example.o7employeeslist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.o7employeeslist.model.EmployeeModel

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employeemodel")
    fun getEmployees(): List<EmployeeModel>

    @Query("SELECT * FROM employeemodel WHERE id == :id")
    fun getEmployeeById(id: Int): EmployeeModel

    @Query("SELECT COUNT(gender) FROM employeemodel WHERE gender == :gender")
    fun getEmployeesNumberByGender(gender: String): Int

    @Query("SELECT AVG(birthday) FROM employeemodel")
    fun getAverageAge(): Long

    @Query("SELECT MAX(salary) FROM employeemodel")
    fun getMaxSalary(): Long

    @Query("SELECT birthday FROM employeemodel ORDER BY birthday DESC")
    fun getListAge(): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployee(employee: EmployeeModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployeeList(employee: List<EmployeeModel>)
}
