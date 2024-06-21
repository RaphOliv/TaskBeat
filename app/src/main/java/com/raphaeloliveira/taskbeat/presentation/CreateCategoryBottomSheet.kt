package com.raphaeloliveira.taskbeat.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raphaeloliveira.taskbeat.R
import com.raphaeloliveira.taskbeat.databinding.CreateCategoryBottomSheetBinding


class CreateCategoryBottomSheet(
    private val onCreateClicked: (String) -> Unit) :
    BottomSheetDialogFragment() {

        private lateinit var binding: CreateCategoryBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateCategoryBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root

        val categoryRequired = getString(R.string.category_required)
        val categoryCreated = getString(R.string.category_created)

        binding.btnCategoryCreate.setOnClickListener {

            if(binding.etCategoryName.text?.isNotEmpty() == false) {
                showMessages(categoryRequired)

            } else {

                val name = binding.etCategoryName.text.toString().trim()
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