package com.example.o7employeeslist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random

@Entity(tableName = "employeemodel")
data class EmployeeModel(
    @PrimaryKey(autoGenerate = true) val id: Int = Random().nextInt(),
    val name: String,
    val birthday: Long,
    val salary: Long,
    val gender: String
) {

    companion object {

        fun stringDateToTimeStamp(date: String): Long {
            val df = SimpleDateFormat("MM/dd/yyyy")
            val date: Date = df.parse(date)
            return date.time
        }

        fun timestampToStringDate(date: Long): String {
            val df = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(date)
            return df.format(netDate)
        }
    }

}


