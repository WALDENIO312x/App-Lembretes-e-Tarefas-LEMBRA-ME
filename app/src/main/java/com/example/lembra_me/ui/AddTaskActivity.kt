package com.example.lembra_me.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lembra_me.databinding.ActivityAddTaskBinding
import com.example.lembra_me.datasource.TaskDataSource
import com.example.lembra_me.extensions.format
import com.example.lembra_me.extensions.text
import com.example.lembra_me.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

/**
 * Seguindo recomendações
 */
class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    /**
     * Seguindo Recomendações
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.Tarefa.text = it.title
                binding.Data.text = it.date
                binding.Hora.text = it.hour
            }
        }

        insertListeners()
        backMainActivity()

    }

    private fun backMainActivity() {
        binding.Toolbar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun insertListeners() {
        binding.Data.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.Data.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "date.picker.tag")
        }

        binding.Hora.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute =
                    if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute

                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.Hora.text = "$hour:$minute"
            }

            timePicker.show(supportFragmentManager, null)
        }

        binding.Cancelar.setOnClickListener {
            finish()
        }

        binding.Salvar.setOnClickListener {
            val task = Task(
                title = binding.Tarefa.text,
                date = binding.Data.text,
                hour = binding.Hora.text,
                id = intent.getIntExtra(TASK_ID, 0)

            )
            TaskDataSource.insertTask(task)

            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    companion object {
        /**
         *
         */
        const val TASK_ID = "task_id"
    }

}