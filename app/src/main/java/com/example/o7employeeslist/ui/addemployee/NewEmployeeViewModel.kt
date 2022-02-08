package com.example.o7employeeslist.ui.addemployee

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ActivityScenario.launch
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
class NewEmployeeViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : ViewModel(), LifecycleObserver {

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    private val _state = MutableStateFlow(NewEmployeeState)
    val state: StateFlow<NewEmployeeState> = _state.asStateFlow()

    fun saveEmployee(employee: EmployeeModel) {
        viewModelScope.launch(Dispatchers.IO) {
            employeeRepository.saveNewEmployee(
                employee,
                object : OperationCallback<EmployeeModel> {
                    override fun onError(error: String?) {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                _event.emit(Event.Error(error))
                            }
                        }
                    }

                    override fun onSuccess(data: EmployeeModel?) {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                data?.let {
                                    _event.emit(Event.ProceedToList(data))
                                }
                            }
                        }
                    }
                })
        }
    }

    object NewEmployeeState

    sealed class Event {
        data class Error(val message: String?) : Event()
        data class ProceedToList(val employee: EmployeeModel) : Event()
    }

}
