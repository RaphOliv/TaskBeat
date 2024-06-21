package com.raphaeloliveira.taskbeat.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raphaeloliveira.taskbeat.databinding.ItemCategoryBinding

class CategoryListAdapter :
    ListAdapter<CategoryUiData, CategoryListAdapter.CategoryViewHolder>(diffCallback())  {

        private lateinit var onClick: (CategoryUiData) -> Unit
        private lateinit var onLongClick: (CategoryUiData) -> Unit

        fun setOnClickListener(onClick: (CategoryUiData) -> Unit) {
            this.onClick = onClick
        }

        fun setOnLongClickListener(onLongClick: (CategoryUiData) -> Unit) {
            this.onLongClick = onLongClick
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CategoryViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            val category = getItem(position)
            holder.bind(category, onClick, onLongClick)
        }

        class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(
                category: CategoryUiData,
                onClick: (CategoryUiData) -> Unit,
                onLongClick: (CategoryUiData) -> Unit
            ){
                binding.tvCategory.text = category.name
                binding.tvCategory.isSelected = category.isSelected

                binding.root.setOnClickListener {
                    onClick.invoke(category)
                }

                binding.root.setOnLongClickListener {
                    onLongClick.invoke(category)
                    true
                }
            }
        }

        class diffCallback  : DiffUtil.ItemCallback<CategoryUiData>() {
            override fun areItemsTheSame(oldItem: CategoryUiData, newItem: CategoryUiData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CategoryUiData, newItem: CategoryUiData): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }