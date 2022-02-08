package com.example.o7employeeslist.data

interface OperationCallback<T> {
    fun onSuccess(data: T?)
    fun onError(error: String?)
}
