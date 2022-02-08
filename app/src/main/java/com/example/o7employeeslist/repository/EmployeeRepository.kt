package com.example.o7employeeslist.repository

import com.example.o7employeeslist.data.OperationCallback
import com.example.o7employeeslist.model.AnalyticsModel
import com.example.o7employeeslist.model.EmployeeModel
import com.example.o7employeeslist.model.GoogleSearchListModel

interface EmployeeRepository {
    fun saveNewEmployee(employee: EmployeeModel, callback: OperationCallback<EmployeeModel>)
    fun getAllEmployees(callback: OperationCallback<List<EmployeeModel>>)
    fun getAnalytics(callback: OperationCallback<AnalyticsModel>)
    fun getProfileInformation(name: String, callback: OperationCallback<GoogleSearchListModel>)
}
