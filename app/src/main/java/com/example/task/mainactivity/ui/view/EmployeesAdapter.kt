package com.example.task.mainactivity.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task.mainactivity.R
import com.example.task.mainactivity.data.User
import com.example.task.mainactivity.databinding.UserCardBinding

class EmployeesAdapter : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {

    private val items = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.user_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setData(users: List<User>){
        items.clear()
        items.addAll(users)

        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = UserCardBinding.bind(view)

        fun bind(data: User) = with(binding){
            val fullUserName = "${data.firstName} ${data.lastName}"
            userName.text = fullUserName
            userPosition.text = data.position
            userNickname.text = data.userTag
        }
    }
}