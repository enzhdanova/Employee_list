package com.example.task.mainactivity.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task.mainactivity.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortsModalBottomSheet : BottomSheetDialogFragment() {

    companion object {
        val TAG = SortsModalBottomSheet::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.botom_sheet_sorts, container, false)
    }
}