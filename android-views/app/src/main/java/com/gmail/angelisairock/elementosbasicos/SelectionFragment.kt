package com.gmail.angelisairock.elementosbasicos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.slider.Slider
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class SelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. CheckBox Indeterminado (Ciclo manual de 3 estados)
        val cbIndeterminate = view.findViewById<MaterialCheckBox>(R.id.cbIndeterminate)
        cbIndeterminate.setOnClickListener {
            cbIndeterminate.checkedState = when (cbIndeterminate.checkedState) {
                MaterialCheckBox.STATE_CHECKED -> MaterialCheckBox.STATE_UNCHECKED
                MaterialCheckBox.STATE_UNCHECKED -> MaterialCheckBox.STATE_INDETERMINATE
                else -> MaterialCheckBox.STATE_CHECKED
            }
        }

        // 2. Slider simple
        val singleSlider = view.findViewById<Slider>(R.id.singleSlider)
        singleSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                // Puedes mostrar el valor en tiempo real si lo deseas
            }
        }

        // 3. Lista desplegable (Spinner)
        val spinner = view.findViewById<Spinner>(R.id.spinnerSelection)
        val juegos = arrayOf("Selecciona un título", "Celeste", "Stardew Valley", "Fortnite")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, juegos)
        spinner.adapter = adapter

        // 4. Selector de Fecha
        view.findViewById<MaterialButton>(R.id.btnDatePicker).setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona una fecha")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                Toast.makeText(requireContext(), "Fecha seleccionada: $selection", Toast.LENGTH_SHORT).show()
            }

            datePicker.show(childFragmentManager, "DATE_PICKER")
        }

        // 5. Selector de Hora
        view.findViewById<MaterialButton>(R.id.btnTimePicker).setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Selecciona una hora")
                .build()

            timePicker.addOnPositiveButtonClickListener {
                Toast.makeText(requireContext(), "Hora: ${timePicker.hour}:${timePicker.minute}", Toast.LENGTH_SHORT).show()
            }

            timePicker.show(childFragmentManager, "TIME_PICKER")
        }
    }
}