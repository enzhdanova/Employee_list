package com.example.task.mainactivity.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.example.task.mainactivity.R
import com.example.task.mainactivity.data.model.User
import com.example.task.mainactivity.databinding.FragmentProfileBinding
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val birthdate = it.getString(ARG_DATE)
            val age = Period.between(LocalDate.parse(birthdate), LocalDate.now()).years
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")

            binding?.name?.text = it.getString(ARG_NAME)
            binding?.nickname?.text = it.getString(ARG_NICKNAME)
            binding?.phone?.text = it.getString(ARG_PHONE)
            binding?.position?.text = it.getString(ARG_POSITION)
            binding?.birthday?.text = LocalDate.parse(birthdate).format(formatter)
            binding?.age?.text = resources.getQuantityString(R.plurals.plular_age, age, age)

            // TODO: аватарки одни и теже, надо что-то придумать
            binding?.avatar?.let { it1 ->
                Glide
                    .with(view)
                    .load("https://i.pravatar.cc/300")
                    .into(it1)
            }
        }

        binding?.back?.setOnClickListener(listenerBack)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private val listenerBack = View.OnClickListener {
        activity?.supportFragmentManager?.commit {
            remove(this@ProfileFragment)
        }
    }

    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName

        private const val ARG_NAME = "param_name"
        private const val ARG_DATE = "param_date"
        private const val ARG_PHONE = "param_phone"
        private const val ARG_NICKNAME = "param_nickname"
        private const val ARG_POSITION = "param_position"
        private const val ARG_PHOTO = "param_photo"

        @JvmStatic
        fun newInstance(user: User) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    val fullName = user.firstName + " " + user.lastName
                    putString(ARG_NAME, fullName)
                    putString(ARG_NICKNAME, user.userTag)
                    putString(ARG_PHONE, user.phone)
                    putString(ARG_DATE, user.birthday.toString())
                    putString(ARG_POSITION, user.position)
                    putString(ARG_PHOTO, user.avatarUrl)
                }
            }
    }
}