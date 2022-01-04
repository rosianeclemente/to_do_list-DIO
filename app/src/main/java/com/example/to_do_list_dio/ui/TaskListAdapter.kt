package com.example.to_do_list_dio.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list_dio.R
import com.example.to_do_list_dio.databinding.ItemTaskBinding
import com.example.to_do_list_dio.model.Task

class TaskListAdapter: androidx.recyclerview.widget.ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback()){

    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):TaskViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int){
        holder.bind(getItem(position))
    }

    inner class TaskViewHolder(
        private val binding: ItemTaskBinding
        ):RecyclerView.ViewHolder(binding.root){
        fun bind(item: Task) {
            binding.tvTitle.text = item.title
            binding.tvDate.text = "${item.date} ${item.hour}"
            binding.iconMore.setOnClickListener {
                showPopup(item)
            }

        }

        private fun showPopup(item: Task) {
            val iconMore = binding.iconMore
            val popupMenu = PopupMenu(iconMore.context, iconMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }
}
class DiffCallback: DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem


    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id

}
