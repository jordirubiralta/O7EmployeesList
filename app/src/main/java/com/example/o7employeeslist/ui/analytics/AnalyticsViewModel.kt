package com.example.o7employeeslist.ui.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.o7employeeslist.data.OperationCallback
import com.example.o7employeeslist.model.AnalyticsModel
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
class AnalyticsViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event.asSharedFlow()

    private val _state = MutableStateFlow(AnalyticsState())
    val state: StateFlow<AnalyticsState> = _state.asStateFlow()

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            employeeRepository.getAnalytics(
                object : OperationCallback<AnalyticsModel> {
                    override fun onError(error: String?) {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                _state.emit(AnalyticsState(error = error))
                            }
                        }
                    }

                    override fun onSuccess(data: AnalyticsModel?) {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                _state.emit(AnalyticsState(analytics = data))
                            }
                        }
                    }
                })
        }
    }

    data class AnalyticsState(
        val analytics: AnalyticsModel? = null,
        val error: String? = null
    )

    sealed class Event
}
