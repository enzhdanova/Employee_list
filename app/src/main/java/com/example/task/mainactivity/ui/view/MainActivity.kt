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
import com.example.task.mainactivity.R
import com.example.task.mainactivity.data.model.Employees
import com.example.task.mainactivity.databinding.ActivityMainBinding
import com.example.task.mainactivity.domain.entity.UIModel
import com.example.task.mainactivity.ui.viewmodel.EmployeesViewModel
import com.example.task.mainactivity.utils.Departments
import com.example.task.mainactivity.utils.SortType
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<EmployeesViewModel>()
    private var binding: ActivityMainBinding? = null
    private val modalSortsBottomSheet = SortsModalBottomSheet()

    private val employeesAdapterListener = object : EmployeesAdapter.EmployeesAdapterListener {
        override fun onItemClick(item: UIModel) {

            val employee: Employees = when (item) {
                is UIModel.Separator -> return
                is UIModel.User ->
                    item.item.toUser()
                is UIModel.UserWithBirthday ->
                    item.item.toUser()
                is UIModel.NotFound -> return
            }

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment, ProfileFragment.newInstance(employee), ProfileFragment.TAG)
            }
        }
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (tab != null) {
                viewModel.changeDepartment(Departments.values()[tab.position])
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
        }
    }

    private val swipeOnRefreshListener = SwipeRefreshLayout.OnRefreshListener { viewModel.update() }

    private val changeFilter = object : TextWatcher {
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

        viewModel.uiState.observe(this) { uiState ->
            if (uiState.error) {
                showErrorFragment()
                return@observe
            }

            if (uiState.needUpdateList) {
                viewModel.getUserFromDepartment()
                binding?.swipeLayout?.isRefreshing = false
                return@observe
            }

            employeesAdapter.submitList(uiState.employeeList)
        }
    }

    private fun initView() {
        binding?.swipeLayout?.setColorSchemeResources(R.color.purple_rb)

        binding?.recyclerView?.apply {
            adapter = employeesAdapter
            addItemDecoration(EmployeesItemDecoration(context))
        }

        for (tab in Departments.values()) {
            binding?.tabs?.apply {
                val newTab = this.newTab().setText(tab.dep)
                addTab(newTab)
            }
        }

        binding?.tabs?.addOnTabSelectedListener(tabSelectedListener)

        binding?.sortsUser?.setOnClickListener(menuClickListener)

        binding?.searchTextview?.addTextChangedListener(changeFilter)

        binding?.swipeLayout?.setOnRefreshListener(swipeOnRefreshListener)
    }

    private fun showErrorFragment() {
        val errorFragment = ErrorFragment.newInstance()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment, errorFragment, ErrorFragment.TAG)
        }

        errorFragment.setFragmentResultListener(ErrorFragment.REQUEST_KEY) { _, _ ->
            viewModel.getUserFromDepartment()
        }
    }
}
