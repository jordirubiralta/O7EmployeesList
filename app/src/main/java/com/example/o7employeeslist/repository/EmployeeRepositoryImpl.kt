package com.example.o7employeeslist.repository

import com.example.o7employeeslist.data.OperationCallback
import com.example.o7employeeslist.data.ServicesApiInterface
import com.example.o7employeeslist.db.EmployeeDao
import com.example.o7employeeslist.model.AnalyticsModel
import com.example.o7employeeslist.model.EmployeeModel
import com.example.o7employeeslist.model.GoogleSearchListModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    private val employeeDao: EmployeeDao,
    private val apiClient: ServicesApiInterface
) : EmployeeRepository {


    override fun saveNewEmployee(
        employee: EmployeeModel,
        callback: OperationCallback<EmployeeModel>
    ) {
        employeeDao.insertEmployee(employee)
        callback.onSuccess(employeeDao.getEmployeeById(employee.id))
    }

    override fun getAllEmployees(callback: OperationCallback<List<EmployeeModel>>) {
        var employees = employeeDao.getEmployees()
        if (employees.isEmpty()) {
            employeeDao.insertEmployeeList(getMockData())
            employees = employeeDao.getEmployees()
        }
        callback.onSuccess(employees)
    }

    override fun getAnalytics(callback: OperationCallback<AnalyticsModel>) {
        callback.onSuccess(
            AnalyticsModel(
                getAge(EmployeeModel.timestampToStringDate(employeeDao.getAverageAge())),
                getMedian(employeeDao.getListAge()),
                employeeDao.getMaxSalary(),
                employeeDao.getEmployeesNumberByGender("M"),
                employeeDao.getEmployeesNumberByGender("F")
            )
        )
    }

    override fun getProfileInformation(
        name: String,
        callback: OperationCallback<GoogleSearchListModel>
    ) {
        val call = apiClient.getProfileInformation(name)
        call.enqueue(object : retrofit2.Callback<GoogleSearchListModel> {
            override fun onFailure(call: Call<GoogleSearchListModel>, t: Throwable) {
                CoroutineScope(Dispatchers.IO).launch {
                    callback.onError(t.toString())
                }
            }

            override fun onResponse(
                call: Call<GoogleSearchListModel>,
                response: Response<GoogleSearchListModel>
            ) {
                CoroutineScope(Dispatchers.IO).launch {
                    response.body()?.let {
                        callback.onSuccess(it)
                    }
                }
            }
        })
    }

    private fun getMedian(agesList: List<Long>): Int {
        val date = agesList[(agesList.size / 2)]
        return getAge(EmployeeModel.timestampToStringDate(date))
    }

    private fun getAge(birthday: String): Int {
        var date: Date? = null
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        try {
            date = sdf.parse(birthday)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (date == null) return 0
        val dob: Calendar = Calendar.getInstance()
        val today: Calendar = Calendar.getInstance()
        dob.setTime(date)
        val year: Int = dob.get(Calendar.YEAR)
        val month: Int = dob.get(Calendar.MONTH)
        val day: Int = dob.get(Calendar.DAY_OF_MONTH)
        dob.set(year, month + 1, day)
        var age: Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age
    }

    private fun getMockData() = listOf(
        EmployeeModel(
            id = 1,
            name = "Jordi Rubiralta",
            birthday = EmployeeModel.stringDateToTimeStamp("12/23/1996"),
            salary = 40000,
            gender = "M"
        ),
        EmployeeModel(
            id = 2,
            name = "Lewie James",
            birthday = EmployeeModel.stringDateToTimeStamp("01/10/1990"),
            salary = 10000,
            gender = "M"
        ),
        EmployeeModel(
            id = 3,
            name = "Mitchell Bloom",
            birthday = EmployeeModel.stringDateToTimeStamp("07/30/2000"),
            salary = 35000,
            gender = "M"
        ),
        EmployeeModel(
            id = 4,
            name = "Benny Mercado",
            birthday = EmployeeModel.stringDateToTimeStamp("08/01/1994"),
            salary = 45000,
            gender = "M"
        ),
        EmployeeModel(
            id = 5,
            name = "Katie Bush",
            birthday = EmployeeModel.stringDateToTimeStamp("11/04/1986"),
            salary = 30000,
            gender = "F"
        ),
        EmployeeModel(
            id = 6,
            name = "Adina Broadhurst",
            birthday = EmployeeModel.stringDateToTimeStamp("10/20/1991"),
            salary = 40000,
            gender = "F"
        ),
        EmployeeModel(
            id = 7,
            name = "Mallory Frost",
            birthday = EmployeeModel.stringDateToTimeStamp("09/02/1997"),
            salary = 50000,
            gender = "F"
        ),
    )
}
