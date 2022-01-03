package com.example.to_do_list_dio.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do_list_dio.databinding.ActivityAddTaskBinding
import com.example.to_do_list_dio.datasource.TaskDataSource
import com.example.to_do_list_dio.extensions.format
import com.example.to_do_list_dio.extensions.text
import com.example.to_do_list_dio.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.sql.Time
import java.util.*

class AddTaskActivity: AppCompatActivity() {
    private lateinit var binding:  ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if(intent.hasExtra(TASK_ID)){
//            val taskId = intent.getIntExtra(TASK_ID, 0)
//            TaskDataSource.findById(taskId)?.let {
//                binding.tilTitle.text = it.title
//                binding.tilHora.text = it.hour
//                binding.tilData.text = it.date
//            }
//        }

        insertListernes()
    }

    private fun insertListernes() {
        binding.tilData.editText?.setOnClickListener{
           val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilData.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }
        binding.tilHora.editText?.setOnClickListener {
            val timePicker= MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener{
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if(timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHora.text = "$hour: $minute"
            }
            timePicker.show(supportFragmentManager, null)
        }
        binding.cancelar.setOnClickListener {
            finish()
        }
        binding.btnNewTask.setOnClickListener {
            val task = Task(
                id = intent.getIntExtra(TASK_ID, 0),
                title = binding.tilTitle.text,
                date = binding.tilData.text,
                hour =  binding.tilHora.text
            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
    companion object{
        const val  TASK_ID = "task_id"
    }
}