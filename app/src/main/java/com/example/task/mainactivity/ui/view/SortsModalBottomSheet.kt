package com.example.task.mainactivity.ui.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.example.task.mainactivity.R
import com.example.task.mainactivity.databinding.BottomSheetSortsBinding
import com.example.task.mainactivity.utils.SortType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortsModalBottomSheet : BottomSheetDialogFragment() {

    companion object {
        val TAG: String = SortsModalBottomSheet::class.java.simpleName
        const val REQUEST_KEY = "requestKey"
        const val ARG_RESULTSORT = "sortType"
    }

    private var binding: BottomSheetSortsBinding? = null
    private var checkedSortType = SortType.ALPHABET

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetSortsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.sortsGroup?.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.alphabet_sort -> checkedSortType = SortType.ALPHABET
                R.id.birthday_sort -> checkedSortType = SortType.DATE_BIRTHDATE
            }

        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        setFragmentResult(REQUEST_KEY, bundleOf(ARG_RESULTSORT to checkedSortType.name))
    }
}