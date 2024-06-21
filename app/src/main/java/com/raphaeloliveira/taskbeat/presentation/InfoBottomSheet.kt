package com.raphaeloliveira.taskbeat.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raphaeloliveira.taskbeat.databinding.InfoBottomSheetBinding


class InfoBottomSheet(
    private val title: String,
    private val message: String,
    private val action: String,
    private val onActionClicked: () -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: InfoBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InfoBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.tvInfoTitle.text = title
        binding.tvInfoMessage.text = message
        binding.btnInfoDelete.text = action

        binding.btnInfoDelete.setOnClickListener {
            onActionClicked.invoke()
            dismiss()
        }
        return view
    }
}