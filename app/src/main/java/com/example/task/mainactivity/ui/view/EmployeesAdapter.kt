package com.example.task.mainactivity.ui.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.mainactivity.R
import com.example.task.mainactivity.databinding.SeparatorBinding
import com.example.task.mainactivity.databinding.UserCardBdBinding
import com.example.task.mainactivity.databinding.UserCardBinding
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
            R.layout.user_card -> UserViewHolder(layoutInflater)
            R.layout.user_card_bd -> UserBDViewHolder(layoutInflater)
            R.layout.separator -> SeparatorViewHolder(layoutInflater)
            R.layout.user_not_found -> NotFoundViewHolder(layoutInflater)
            else -> throw IllegalStateException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is UserViewHolder -> holder.bind(item as UIModel.EmployeeUI)
            is UserBDViewHolder -> holder.bind(item as UIModel.EmployeeUIWithBirthday)
            is SeparatorViewHolder -> holder.bind()
        }

    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is UIModel.EmployeeUI -> R.layout.user_card
        is UIModel.EmployeeUIWithBirthday -> R.layout.user_card_bd
        is UIModel.Separator -> R.layout.separator
        is UIModel.NotFound -> R.layout.user_not_found
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = UserCardBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(data: UIModel.EmployeeUI) = with(binding) {
            with(data.item) {
                val fullUserName = "$firstName $lastName"
                userName.text = fullUserName
                userPosition.text = position
                userNickname.text = userTag
                Glide
                    .with(itemView)
                    .load("https://i.pravatar.cc/128")
                    .into(userPhoto)
            }
        }
    }

    inner class UserBDViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = UserCardBdBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(data: UIModel.EmployeeUIWithBirthday) = with(binding) {
            val formatter = DateTimeFormatter.ofPattern("dd MMM")

            with(data.item) {
                val fullUserName =
                    "$firstName $lastName"

                userName.text = fullUserName
                userPosition.text = position
                userNickname.text = userTag
                userBirthday.text = birthday.format(formatter)
                Glide
                    .with(itemView)
                    .load(Uri.parse("https://i.pravatar.cc/128"))
                    .into(userPhoto)
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