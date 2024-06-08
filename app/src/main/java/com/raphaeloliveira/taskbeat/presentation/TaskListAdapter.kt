package com.raphaeloliveira.taskbeat.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raphaeloliveira.taskbeat.R
import com.raphaeloliveira.taskbeat.data.TaskUiData

class TaskListAdapter :
    ListAdapter<TaskUiData, TaskListAdapter.TaskViewHolder>(diffCallback()) {

            private lateinit var callBack: (TaskUiData) -> Unit
            fun setOnClickListener(onClick: (TaskUiData) -> Unit) {
                callBack = onClick
            }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
            return TaskViewHolder(view)
        }

        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val category = getItem(position)
            holder.bind(category, callBack)
        }

        class TaskViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            private val tvCategory = view.findViewById<TextView>(R.id.tv_category_name)
            private val tvName = view.findViewById<TextView>(R.id.tv_task_name)
            fun bind(task: TaskUiData, callBack: (TaskUiData) -> Unit) {
                tvName.text = task.name
                tvCategory.text = task.category

                view.setOnClickListener {
                    callBack.invoke(task)
                }
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



