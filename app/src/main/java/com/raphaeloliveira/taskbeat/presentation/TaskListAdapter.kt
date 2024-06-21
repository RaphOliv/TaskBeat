package com.raphaeloliveira.taskbeat.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raphaeloliveira.taskbeat.databinding.ItemTaskBinding

class TaskListAdapter :
    ListAdapter<TaskUiData, TaskListAdapter.TaskViewHolder>(diffCallback()) {

            private lateinit var callBack: (TaskUiData) -> Unit
            fun setOnClickListener(onClick: (TaskUiData) -> Unit) {
                callBack = onClick
            }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TaskViewHolder(binding)
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val category = getItem(position)
            holder.bind(category, callBack)
        }

        class TaskViewHolder(private val binding: ItemTaskBinding ) : RecyclerView.ViewHolder(binding.root) {
            private lateinit var task: TaskUiData
            fun bind(
                task: TaskUiData,
                callBack: (TaskUiData) -> Unit,
            ){
                this.task = task
                binding.tvTaskName.text = task.name
                binding.tvCategoryName.text = task.category

                binding.root.setOnClickListener {
                    callBack.invoke(task)
                }
            }

            fun getTask(): TaskUiData {
                return task
            }
        }

        class diffCallback  : DiffUtil.ItemCallback<TaskUiData>() {
            override fun areItemsTheSame(oldItem: TaskUiData, newItem: TaskUiData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TaskUiData, newItem: TaskUiData): Boolean {
                return oldItem.name == newItem.name
                    && oldItem.category == newItem.category
            }
        }
    }



