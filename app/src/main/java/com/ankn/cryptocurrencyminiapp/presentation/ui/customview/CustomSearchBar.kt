package com.ankn.cryptocurrencyminiapp.presentation.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.ankn.cryptocurrencyminiapp.databinding.CustomSearchBarBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard

class CustomSearchBar(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {

    private val binding: CustomSearchBarBinding =
        CustomSearchBarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setupListeners()
    }

    private fun setupListeners() {
        // Show/hide clear button based on text input
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Clear text when clear button is clicked
        binding.clearButton.setOnClickListener {
            binding.searchEditText.text?.clear()
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
    }

    fun setSearchQueryListener(listener: (String) -> Unit) {
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            listener(text.toString())
        }
    }

    fun getSearchText(): String {
        return binding.searchEditText.text.toString()
    }

    fun clearSearchText() {
        binding.searchEditText.text?.clear()
    }
}
