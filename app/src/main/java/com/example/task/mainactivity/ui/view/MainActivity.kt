package com.example.task.mainactivity.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.example.task.mainactivity.R
import com.example.task.mainactivity.data.User
import com.example.task.mainactivity.databinding.ActivityMainBinding
import com.example.task.mainactivity.ui.viewmodel.EmployeesViewModel
import com.example.task.mainactivity.utils.Departments
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private val viewModel = EmployeesViewModel()
    private var binding: ActivityMainBinding? = null

    private val employeesAdapterListener = object : EmployeesAdapter.EmployeesAdapterListener {
        override fun onItemClick(item: User) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment, ProfileFragment.newInstance(item), ProfileFragment::class.java.simpleName)
            }
            //TODO: Тут переход в страницу профиля сотрудника
        }
    }

    private val employeesAdapter = EmployeesAdapter(employeesAdapterListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        initView()

        viewModel.uiState.observe(this) {
            uiState ->
            uiState.employeeList?.let { data -> employeesAdapter.submitList(data) }
        }
    }

    private fun initView(){
        binding?.recyclerView?.apply {
            adapter = employeesAdapter
            addItemDecoration(EmployeesItemDecoration(context))
        }

        for (tab in Departments.values()){
            println("MyApp: ${tab.name}  ${tab.dep}")
          //  binding?.tabs?.newTab()?.let { binding?.tabs?.addTab(it.setText(tab.dep)) }
            binding?.tabs?.apply {
                val newTab = this.newTab().setText(tab.dep)
                addTab(newTab)
            }
        }

    }
}
