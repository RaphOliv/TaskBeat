package com.raphaeloliveira.taskbeat.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raphaeloliveira.taskbeat.R

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

        class TaskViewHolder(private val view: View ) : RecyclerView.ViewHolder(view) {
            private lateinit var task: TaskUiData
            private val tvTaskName: TextView = view.findViewById(R.id.tv_task_name)
            private val tvCategoryName: TextView = view.findViewById(R.id.tv_category_name)

            fun bind(
                task: TaskUiData,
                callBack: (TaskUiData) -> Unit,
            ){
                this.task = task
                tvTaskName.text = task.name
                tvCategoryName.text = task.category

               view.setOnClickListener {
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



