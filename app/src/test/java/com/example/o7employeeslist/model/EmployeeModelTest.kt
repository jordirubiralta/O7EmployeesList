package com.example.o7employeeslist.model

import org.junit.Assert.*
import org.junit.Test

class EmployeeModelTest {

    @Test
    fun `get timestamp from string date successfully`() {
        val date = "12/23/1996"
        assertEquals(EmployeeModel.stringDateToTimeStamp(date), 851295600000) // Local Spain Time
    }

    @Test
    fun `get string date from timestamp successfully`() {
        val date = 632185200000L
        assertEquals(EmployeeModel.timestampToStringDate(date), "01/13/1990")
    }
}
