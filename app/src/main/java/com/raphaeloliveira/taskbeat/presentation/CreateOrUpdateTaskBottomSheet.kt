package com.raphaeloliveira.taskbeat.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.raphaeloliveira.taskbeat.R
import com.raphaeloliveira.taskbeat.data.CategoryEntity

class CreateOrUpdateTaskBottomSheet(
    private val categoryList: List<CategoryEntity>,
    private val task: TaskUiData? = null,
    private val onCreateClicked: (TaskUiData) -> Unit,
    private val onUpdateClicked: (TaskUiData) -> Unit,
    private val onDeleteClicked: (TaskUiData) -> Unit
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_or_update_task_bottom_sheet, container, false)

        val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        val etTaskName = view.findViewById<TextInputEditText>(R.id.et_task_name)
        val btnCreateOrUpdate = view.findViewById<Button>(R.id.btn_task_create)
        val spinner: Spinner = view.findViewById(R.id.category_spinner)

        val selectACategory = getString(R.string.select_a_category)
        val categoryRequired = getString(R.string.category_required)
        val taskCreated = getString(R.string.task_created)
        val taskUpdated = getString(R.string.task_updated)
        val fieldsRequired = getString(R.string.fields_required)

        var taskCategory : String? = null
        val categoryListTemp = mutableListOf(selectACategory)
        categoryListTemp.addAll(categoryList.map { it.name })

        ArrayAdapter(
            requireActivity().baseContext,
            android.R.layout.simple_spinner_item,
            categoryListTemp.toList()
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                taskCategory = categoryListTemp[position]
                }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                showMessages(categoryRequired)
            }
        }

        if (task == null) {
            tvTitle.setText(R.string.create_task_title)
            btnCreateOrUpdate.setText(R.string.create)
        } else {
            tvTitle.setText(R.string.update_task_title)
            btnCreateOrUpdate.setText(R.string.update)
            etTaskName.setText(task.name)

            val currentCategory = categoryList.first { it.name == task.category }
            val index = categoryList.indexOf(currentCategory)
            spinner.setSelection(index)
        }

        btnCreateOrUpdate.setOnClickListener {
            val name = etTaskName.text.toString().trim()
            if (taskCategory != (selectACategory) && name.isNotEmpty()) {

                if (task == null) {
                    onCreateClicked.invoke(
                        TaskUiData(
                            id = 0,
                            name = name,
                            category = requireNotNull(taskCategory)
                        )
                    )
                    dismiss()
                    showMessages(taskCreated)

                } else {
                    onUpdateClicked.invoke(
                        TaskUiData(
                            id = task.id,
                            name = name,
                            category = requireNotNull(taskCategory)
                        )
                    )
                    dismiss()
                    showMessages(taskUpdated)
                }
            } else {
                showMessages(fieldsRequired)
            }
        }

        return view

    }
    private fun showMessages(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
