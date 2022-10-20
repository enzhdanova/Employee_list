package com.example.task.mainactivity.ui.view.viewUtils

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.mainactivity.R
import com.example.task.mainactivity.databinding.EmployeeCardBdBinding
import com.example.task.mainactivity.databinding.EmployeeCardBinding
import com.example.task.mainactivity.databinding.SeparatorBinding
import com.example.task.mainactivity.domain.entity.UIModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EmployeesAdapter(
    private val listener: EmployeesAdapterListener
) : ListAdapter<UIModel, RecyclerView.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<UIModel>() {

            override fun areItemsTheSame(oldItem: UIModel, newItem: UIModel): Boolean {
                val isEmployeeUIItem = oldItem is UIModel.EmployeeUI
                        && newItem is UIModel.EmployeeUI
                        && oldItem.item.id == newItem.item.id

                val isEmployeeBDItem = oldItem is UIModel.EmployeeUIWithBirthday
                        && newItem is UIModel.EmployeeUIWithBirthday
                        && oldItem.item.id == newItem.item.id

                return isEmployeeUIItem || isEmployeeBDItem
            }

            override fun areContentsTheSame(oldItem: UIModel, newItem: UIModel) = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)

        return when (viewType) {
            R.layout.employee_card -> EmployeeViewHolder(layoutInflater)
            R.layout.employee_card_bd -> EmployeeBDViewHolder(layoutInflater)
            R.layout.separator -> SeparatorViewHolder(layoutInflater)
            R.layout.employee_not_found -> NotFoundViewHolder(layoutInflater)
            else -> throw IllegalStateException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is EmployeeViewHolder -> holder.bind(item as UIModel.EmployeeUI)
            is EmployeeBDViewHolder -> holder.bind(item as UIModel.EmployeeUIWithBirthday)
            is SeparatorViewHolder -> holder.bind()
        }

    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is UIModel.EmployeeUI -> R.layout.employee_card
        is UIModel.EmployeeUIWithBirthday -> R.layout.employee_card_bd
        is UIModel.Separator -> R.layout.separator
        is UIModel.NotFound -> R.layout.employee_not_found
    }

    inner class EmployeeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = EmployeeCardBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(data: UIModel.EmployeeUI) = with(binding) {
            with(data.item) {
                val fullEmployeeName = "$firstName $lastName"
                employeeName.text = fullEmployeeName
                employeePosition.text = position
                employeeNickname.text = userTag
                Glide
                    .with(itemView)
                    .load(avatarUrl)
                    .into(employeePhoto)
            }
        }
    }

    inner class EmployeeBDViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = EmployeeCardBdBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(data: UIModel.EmployeeUIWithBirthday) = with(binding) {
            val formatter = DateTimeFormatter.ofPattern("dd MMM")

            with(data.item) {
                val fullEmployeeName =
                    "$firstName $lastName"

                employeeName.text = fullEmployeeName
                employeePosition.text = position
                employeeNickname.text = userTag
                employeeBirthday.text = birthday.format(formatter)
                Glide
                    .with(itemView)
                    .load(Uri.parse(avatarUrl))
                    .into(employeePhoto)
            }
        }
    }

    inner class SeparatorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = SeparatorBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind() = with(binding) {
            year.text = LocalDate.now().year.inc().toString()
        }
    }

    inner class NotFoundViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface EmployeesAdapterListener {
        fun onItemClick(item: UIModel)
    }
}