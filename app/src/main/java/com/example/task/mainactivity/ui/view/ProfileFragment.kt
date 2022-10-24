package com.example.task.mainactivity.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.task.mainactivity.R
import com.example.task.mainactivity.databinding.FragmentProfileBinding
import com.example.task.mainactivity.domain.entity.EmployeeItem
import com.example.task.mainactivity.ui.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding(createMethod = CreateMethod.INFLATE)
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            val id = bundle.getString(ARG_ID) ?: ""
            viewModel.getEmployee(id)
        }

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->

            if (uiState.employee != null) {
                setEmployee(uiState.employee)
            }
        }

        with(binding) {
            back.setOnClickListener(listenerBack)

            if (viewModel.uiState.value?.employee?.phone != null) {
                phone.setOnClickListener(listenerDialPhoneNumber)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private val listenerBack = View.OnClickListener {
        activity?.supportFragmentManager?.commit {
            remove(this@ProfileFragment)
        }
    }

    private val listenerDialPhoneNumber = View.OnClickListener {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${binding.phone.text}")
        }
        startActivity(intent)
    }

    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName

        private const val ARG_ID = "param_id"

        fun newInstance(id: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                }
            }
    }

    private fun setEmployee(employee: EmployeeItem) {
        val birthdate = employee.birthday
        val ageInInt = Period.between(birthdate, LocalDate.now()).years
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

        with(binding) {
            val fullname = employee.firstName + " " + employee.lastName
            name.text = fullname
            nickname.text = employee.userTag
            phone.text = employee.phone
            position.text = employee.position
            birthday.text = birthdate.format(formatter)
            age.text = resources.getQuantityString(R.plurals.plular_age, ageInInt, ageInInt)

            avatar.let {
                Glide
                    .with(this@ProfileFragment)
                    .load(employee.avatarUrl)
                    .into(it)
            }
        }
    }
}