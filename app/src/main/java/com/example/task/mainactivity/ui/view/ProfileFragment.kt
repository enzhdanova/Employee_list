package com.example.task.mainactivity.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.example.task.mainactivity.data.User
import com.example.task.mainactivity.databinding.FragmentProfileBinding

class ProfileFragment() : Fragment() {

    private var binding: FragmentProfileBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            binding?.name?.text = it.getString(ARG_NAME)
            binding?.nickname?.text = it.getString(ARG_NICKNAME)
            binding?.phone?.text = it.getString(ARG_PHONE)
            binding?.position?.text = it.getString(ARG_POSITION)
            binding?.birthday?.text = it.getString(ARG_DATE)
        //    binding?.avatar?.setImageURI(Uri.parse("https://i.pravatar.cc/72"))
        }

        binding?.back?.setOnClickListener(listener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private val listener = View.OnClickListener {
       activity?.supportFragmentManager?.commit {
            remove(this@ProfileFragment)
       }
    }

    companion object {

        private const val ARG_NAME = "param_name"
        private const val ARG_DATE = "param_date"
        private const val ARG_PHONE = "param_phone"
        private const val ARG_NICKNAME = "param_nickname"
        private const val ARG_POSITION = "param_position"

        @JvmStatic
        fun newInstance(user: User) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    val fullName = user.firstName + " " + user.lastName
                    putString(ARG_NAME, fullName)
                    putString(ARG_NICKNAME, user.userTag)
                    putString(ARG_PHONE, user.phone)
                    putString(ARG_DATE, user.birthday)
                    putString(ARG_POSITION, user.position)
                }
            }
    }
}