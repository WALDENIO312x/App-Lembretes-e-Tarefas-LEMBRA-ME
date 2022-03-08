package com.example.lembra_me.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lembra_me.R
import com.example.lembra_me.databinding.ItemtaskBinding
import com.example.lembra_me.model.Task


/**
 *
 */
class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback()) {

    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    /**
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemtaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    /**
     *
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     *
     */
    inner class TaskViewHolder
        (private val binding: ItemtaskBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         *
         */
        fun bind(item: Task) {
            binding.primeTitle.text = item.title
            binding.dataHora.text = "${item.date}:${item.hour}"
            binding.treeBottom.setOnClickListener {
                showPopup(item)
            }
        }

        private fun showPopup(item: Task) {
            val treeBottom = binding.treeBottom
            val popupMenu = PopupMenu(treeBottom.context, treeBottom)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

    }

}

/**
 *
 */
class DiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem.id == newItem.id

}