package com.example.o7employeeslist.ui.addemployee

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.o7employeeslist.R
import com.example.o7employeeslist.databinding.ActivityNewEmployeeBinding
import com.example.o7employeeslist.model.EmployeeModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class NewEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewEmployeeBinding

    private val viewModel by viewModels<NewEmployeeViewModel>()

    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
//        overridePendingTransition(R.anim.slide_down, R.anim.hold)
        super.onCreate(savedInstanceState)
        binding = ActivityNewEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupUI()
    }

    private fun setupObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.event.collect { launchEvent(it) }
        }
    }

    private fun setupUI() {
        setBirthdayPicker()
        binding.btCancel.setOnClickListener { onBackPressed() }
        binding.btSave.setOnClickListener {
            if (runValidators()) {
                viewModel.saveEmployee(
                    EmployeeModel(
                        name = binding.etName.text.toString(),
                        salary = binding.etSalary.text.toString().toLong(),
                        gender = if (binding.cbMale.isChecked) "M" else "F",
                        birthday = EmployeeModel.stringDateToTimeStamp(calendarToStringDate(calendar))
                    )
                )
            } else {
                Toast.makeText(this, getString(R.string.form_error_message), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.cbMale.setOnClickListener {
            binding.cbFemale.isChecked = false
        }
        binding.cbFemale.setOnClickListener {
            binding.cbMale.isChecked = false
        }
    }

    private fun setBirthdayPicker() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.etBirthday.setText(calendarToStringDate(calendar))

            }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        binding.etBirthday.setOnClickListener {
            DatePickerDialog(
                this@NewEmployeeActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun calendarToStringDate(cal: Calendar): String {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(cal.time)
    }

    private fun launchEvent(event: NewEmployeeViewModel.Event) {
        when (event) {
            is NewEmployeeViewModel.Event.ProceedToList -> {
                setResult(RESULT_OK)
                finish()
            }
            is NewEmployeeViewModel.Event.Error -> {
                Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun runValidators() =
        binding.etName.text != null && binding.etBirthday.text != null &&
                binding.etSalary.text != null && (binding.cbMale.isChecked || binding.cbFemale.isChecked)
}
