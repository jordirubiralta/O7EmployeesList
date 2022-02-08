package com.example.o7employeeslist.ui.headers

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.o7employeeslist.data.OperationCallback
import com.example.o7employeeslist.model.GoogleSearchListModel
import com.example.o7employeeslist.model.GoogleSearchModel
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
class EmployeeHeadersViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : ViewModel(), LifecycleObserver {

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    private val _state = MutableStateFlow(EmployeeHeadersState())
    val state: StateFlow<EmployeeHeadersState> = _state.asStateFlow()

    fun getProfileInformation(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            employeeRepository.getProfileInformation(name,
                object : OperationCallback<GoogleSearchListModel> {
                    override fun onError(error: String?) {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                _state.emit(EmployeeHeadersState(error))
                            }
                        }
                    }

                    override fun onSuccess(data: GoogleSearchListModel?) {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                data?.let {
                                    _state.emit(EmployeeHeadersState(headers = it.items))
                                }
                            }
                        }
                    }
                })
        }
    }

    data class EmployeeHeadersState(
        val error: String? = null,
        val headers: List<GoogleSearchModel> = mutableListOf()
    )

    sealed class Event
}
