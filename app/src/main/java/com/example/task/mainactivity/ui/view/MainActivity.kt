package com.example.task.mainactivity.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import com.example.task.mainactivity.R
import com.example.task.mainactivity.data.EmployeesRepository
import com.example.task.mainactivity.data.model.User
import com.example.task.mainactivity.databinding.ActivityMainBinding
import com.example.task.mainactivity.domain.EmployeesUseCase
import com.example.task.mainactivity.ui.data.UIModel
import com.example.task.mainactivity.ui.viewmodel.EmployeesViewModel
import com.example.task.mainactivity.utils.Departments
import com.example.task.mainactivity.utils.SortType
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private val viewModel = EmployeesViewModel(EmployeesUseCase(EmployeesRepository()))
    private var binding: ActivityMainBinding? = null
    private val modalSortsBottomSheet = SortsModalBottomSheet()

    private val employeesAdapterListener = object : EmployeesAdapter.EmployeesAdapterListener {
        override fun onItemClick(item: UIModel) {

            val user: User = when (item) {
                is UIModel.Separator -> return
                is UIModel.User ->
                    item.item.toUser()
                is UIModel.UserWithBirthday ->
                    item.item.toUser()
            }

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment, ProfileFragment.newInstance(user), ProfileFragment.TAG)
            }
        }
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener{
        override fun onTabSelected(tab: TabLayout.Tab?) {
            println(tab?.text.toString())
            if (tab != null) {
                viewModel.changeDepartment(Departments.values()[tab.position])
                viewModel.getUserFromDepartment()
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    }

    private val menuClickListener = View.OnClickListener {
        modalSortsBottomSheet.show(
            supportFragmentManager,
            SortsModalBottomSheet.TAG
        )

        modalSortsBottomSheet.setFragmentResultListener(SortsModalBottomSheet.REQUEST_KEY) { _, bundle ->
            val result = bundle.getString(SortsModalBottomSheet.ARG_RESULTSORT) ?: ""
            viewModel.changeSortType(SortType.valueOf(result))
            viewModel.getUserFromDepartment()
        }
    }

    private val changeFilter = object :TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.setFilter(p0.toString())
        }

        override fun afterTextChanged(p0: Editable?) {
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
            if (viewModel.uiState.value?.error != true) {
                uiState.employeeList?.let { data -> employeesAdapter.submitList(data) }
            }
        }
    }

    private fun initView(){
        binding?.recyclerView?.apply {
            adapter = employeesAdapter
            addItemDecoration(EmployeesItemDecoration(context))
        }

        for (tab in Departments.values()){
            binding?.tabs?.apply {
                val newTab = this.newTab().setText(tab.dep)
                addTab(newTab)
            }
        }

        binding?.tabs?.addOnTabSelectedListener(tabSelectedListener)

        binding?.sortsUser?.setOnClickListener(menuClickListener)

        binding?.searchTextview?.addTextChangedListener(changeFilter)
    }
}
