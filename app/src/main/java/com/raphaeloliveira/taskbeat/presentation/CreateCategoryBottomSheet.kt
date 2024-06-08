package com.raphaeloliveira.taskbeat.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.raphaeloliveira.taskbeat.R


class CreateCategoryBottomSheet(
    private val onCreateClicked: (String) -> Unit) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_category_bottom_sheet, container, false)

        val btnCreateCategory = view.findViewById<Button>(R.id.btn_category_create)
        val etCategoryName = view.findViewById<TextInputEditText>(R.id.et_category_name)
        val categoryRequired = getString(R.string.category_required)
        val categoryCreated = getString(R.string.category_created)

        btnCreateCategory.setOnClickListener {

            if(etCategoryName.text?.isNotEmpty() == false) {
                showMessages(categoryRequired)

            } else {

                val name = etCategoryName.text.toString().trim()
                onCreateClicked(name)
                dismiss()
                showMessages(categoryCreated)
            }

        }

        return view

    }
    private fun showMessages(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }


}