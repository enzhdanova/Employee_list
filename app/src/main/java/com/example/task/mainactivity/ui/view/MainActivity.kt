package com.example.task.mainactivity.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task.mainactivity.R
import com.example.task.mainactivity.ui.viewmodel.EmployeesViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel = EmployeesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}