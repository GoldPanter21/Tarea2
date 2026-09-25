package com.gmail.angelisairock.elementosbasicos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class InputFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarValidacionDeError(view)
        configurarMenuDesplegable(view)
    }

    private fun configurarValidacionDeError(view: View) {
        val layoutError = view.findViewById<TextInputLayout>(R.id.layoutError)
        val etError = view.findViewById<TextInputEditText>(R.id.etError)

        etError.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && text.length < 5) {
                layoutError.error = "Faltan caracteres (mínimo 5)"
            } else {
                layoutError.error = null
            }
        }
    }

    private fun configurarMenuDesplegable(view: View) {
        val opciones = arrayOf("Views (XML)", "Jetpack Compose", "Flutter", "Kotlin Native")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, opciones)
        val autoComplete = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteMenu)

        autoComplete.setAdapter(adapter)
    }
}