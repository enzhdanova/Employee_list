package com.example.task.mainactivity.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.mainactivity.R
import com.example.task.mainactivity.data.model.Employee
import com.example.task.mainactivity.databinding.ActivityMainBinding
import com.example.task.mainactivity.domain.entity.UIModel
import com.example.task.mainactivity.ui.view.viewUtils.EmployeesAdapter
import com.example.task.mainactivity.ui.view.viewUtils.EmployeesItemDecoration
import com.example.task.mainactivity.ui.viewmodel.EmployeesViewModel
import com.example.task.mainactivity.utils.Department
import com.example.task.mainactivity.utils.SortType
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel by viewModels<EmployeesViewModel>()
    private val binding: ActivityMainBinding by viewBinding()
    private val modalSortsBottomSheet = SortsModalBottomSheet()

    private val employeesAdapterListener = object : EmployeesAdapter.EmployeesAdapterListener {
        override fun onItemClick(item: UIModel) {

            val employee: Employee = when (item) {
                is UIModel.Separator -> return
                is UIModel.EmployeeUI ->
                    item.item.toModel()
                is UIModel.EmployeeUIWithBirthday ->
                    item.item.toModel()
                is UIModel.NotFound -> return
            }

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment, ProfileFragment.newInstance(employee.id), ProfileFragment.TAG)
            }
        }
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (tab != null) {
                viewModel.changeDepartment(Department.values()[tab.position])
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
        override fun onTabReselected(tab: TabLayout.Tab?) = Unit
    }

    private val menuClickListener = View.OnClickListener {
        modalSortsBottomSheet.show(
            supportFragmentManager,
            SortsModalBottomSheet.TAG
        )
        modalSortsBottomSheet.setFragmentResultListener(SortsModalBottomSheet.REQUEST_KEY) { _, bundle ->
            val result = bundle.getString(SortsModalBottomSheet.ARG_RESULTSORT) ?: ""
            viewModel.changeSortType(SortType.valueOf(result))
        }
    }

    private val swipeOnRefreshListener =
        SwipeRefreshLayout.OnRefreshListener { viewModel.fetchEmployees() }

    private val changeFilter = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.setFilter(p0.toString())
        }

        override fun afterTextChanged(p0: Editable?) = Unit
    }

    private val employeesAdapter = EmployeesAdapter(employeesAdapterListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()

        viewModel.uiState.observe(this) { uiState ->
            if (uiState.error) {
                showErrorFragment()
                return@observe
            }

            if (uiState.needUpdateList) {
                viewModel.getCurrentEmployees()
                binding.swipeLayout.isRefreshing = false
                return@observe
            }

            employeesAdapter.submitList(uiState.employeeList)
        }
    }

    private fun initView() {
        binding.swipeLayout.setColorSchemeResources(R.color.purple_rb)

        binding.recyclerView.apply {
            adapter = employeesAdapter
            addItemDecoration(EmployeesItemDecoration(context))
        }

        for (tab in Department.values()) {
            binding.tabs.apply {
                val newTab = this.newTab().setText(tab.dep)
                addTab(newTab)
            }
        }

        binding.tabs.addOnTabSelectedListener(tabSelectedListener)
        binding.sortsEmployee.setOnClickListener(menuClickListener)
        binding.searchTextview.addTextChangedListener(changeFilter)
        binding.swipeLayout.setOnRefreshListener(swipeOnRefreshListener)
    }

    private fun showErrorFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment, ErrorFragment.newInstance(), ErrorFragment.TAG)
        }

        supportFragmentManager.setFragmentResultListener(ErrorFragment.REQUEST_KEY, this){
                _, _ ->
            viewModel.getCurrentEmployees()
        }
    }
}
