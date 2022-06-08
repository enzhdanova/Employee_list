package com.example.task.mainactivity.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResult
import com.example.task.mainactivity.databinding.FragmentErrorBinding

class ErrorFragment : Fragment() {

    companion object {
        val TAG: String = ErrorFragment::class.java.simpleName
        const val REQUEST_KEY = "requestKey"

        fun newInstance() = ErrorFragment()
    }

    private var binding: FragmentErrorBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.textviewAgain?.setOnClickListener(listenerBack)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private val listenerBack = View.OnClickListener {
        activity?.supportFragmentManager?.commit {
            remove(this@ErrorFragment)
        }
    }

    override fun onPause() {
        super.onPause()
        setFragmentResult(REQUEST_KEY, bundleOf())
    }
}