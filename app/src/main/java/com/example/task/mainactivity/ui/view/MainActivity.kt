package com.example.task.mainactivity.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.task.mainactivity.databinding.ActivityMainBinding
import com.example.task.mainactivity.ui.viewmodel.EmployeesViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel = EmployeesViewModel()
    private val employeesAdapter = EmployeesAdapter()
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        binding?.recyclerView?.apply {
            adapter = employeesAdapter
            addItemDecoration(EmployeesItemDecoration(context))
        }

        viewModel.uiState.observe(this) {
            uiState ->
            uiState.employeeList?.let { data -> employeesAdapter.submitList(data) }
        }


    }
}