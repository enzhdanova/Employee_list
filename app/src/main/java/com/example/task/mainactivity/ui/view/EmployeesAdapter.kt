package com.example.task.mainactivity.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task.mainactivity.R
import com.example.task.mainactivity.data.User
import com.example.task.mainactivity.databinding.UserCardBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EmployeesAdapter(
    private val listener: EmployeesAdapterListener
) : ListAdapter<User, EmployeesAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object  : DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.user_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = UserCardBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(data: User) = with(binding){
            val fullUserName = "${data.firstName} ${data.lastName}"
            val formatter = DateTimeFormatter.ofPattern("dd MMM")
            userName.text = fullUserName
            userPosition.text = data.position
            userNickname.text = data.userTag
            userBirthday.text = data.birthday.format(formatter)
            //TODO: РАзобраться в чем дело, не грузится картинка
           // userPhoto.setImageURI(Uri.parse("https://i.pravatar.cc/300"))
            Glide
                .with(itemView)
                .load(data.avatarUrl)
                .into(userPhoto)
        }
    }

    interface EmployeesAdapterListener {
        fun onItemClick(item: User)
    }
}