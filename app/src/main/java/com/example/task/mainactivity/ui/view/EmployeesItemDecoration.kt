package com.example.task.mainactivity.ui.view

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.task.mainactivity.R

class EmployeesItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val margin16 = context.resources.getDimensionPixelSize(R.dimen.margin16)
    private val margin6 = context.resources.getDimensionPixelSize(R.dimen.margin6)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val newRect = Rect(margin16, margin6, margin16, margin6)

        outRect.apply {
            left = newRect.left
            right = newRect.right
            top = newRect.top
            bottom = newRect.bottom

        }
    }
}