package com.example.o7employeeslist.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.o7employeeslist.data.OperationCallback
import com.example.o7employeeslist.model.EmployeeModel
import com.example.o7employeeslist.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EmployeeListViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    private val _state = MutableStateFlow(EmployeeListState())
    val state: StateFlow<EmployeeListState> = _state.asStateFlow()

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            employeeRepository.getAllEmployees(
                object : OperationCallback<List<EmployeeModel>> {
                    override fun onError(error: String?) {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                _state.emit(EmployeeListState(error = error))
                            }
                        }
                    }

                    override fun onSuccess(data: List<EmployeeModel>?) {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                data?.let {
                                    _state.emit(
                                        EmployeeListState(
                                            employees = it
                                        )
                                    )
                                }
                            }
                        }
                    }
                })
        }
    }

    data class EmployeeListState(
        val error: String? = null,
        val employees: List<EmployeeModel> = mutableListOf()
    )

    sealed class Event
}
