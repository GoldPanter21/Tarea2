package com.gmail.angelisairock.elementosbasicos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ButtonsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buttons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Tipos de Botones Básicos
        view.findViewById<MaterialButton>(R.id.btnSolid).setOnClickListener {
            mostrarMensaje("Botón Relleno pulsado")
        }
        view.findViewById<MaterialButton>(R.id.btnOutlined).setOnClickListener {
            mostrarMensaje("Botón con Contorno pulsado")
        }
        view.findViewById<MaterialButton>(R.id.btnText).setOnClickListener {
            mostrarMensaje("Botón de Texto pulsado")
        }

        // 2. Botones con ícono
        view.findViewById<MaterialButton>(R.id.btnIconText).setOnClickListener {
            mostrarMensaje("Enviar (Ícono + Texto) pulsado")
        }
        view.findViewById<MaterialButton>(R.id.btnIconOnly).setOnClickListener {
            mostrarMensaje("Botón de solo ícono pulsado")
        }

        // 3. Botones de Acción Flotante (FAB)
        view.findViewById<FloatingActionButton>(R.id.fabNormal).setOnClickListener {
            mostrarMensaje("FAB Normal pulsado")
        }

        // Animación de extensión/colapso para el FAB Extendido
        val fabExtended = view.findViewById<ExtendedFloatingActionButton>(R.id.fabExtended)
        fabExtended.setOnClickListener {
            if (fabExtended.isExtended) {
                fabExtended.shrink()
                mostrarMensaje("FAB colapsado")
            } else {
                fabExtended.extend()
                mostrarMensaje("FAB extendido")
            }
        }

        // 4. Selector Segmentado (Toggle)
        val toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleGroup)
        toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val seleccion = if (checkedId == R.id.btnOpcion1) "Opción 1" else "Opción 2"
                mostrarMensaje("Seleccionaste: $seleccion")
            }
        }

        // 5. Botón en estado de carga (Simulación)
        val btnLoading = view.findViewById<MaterialButton>(R.id.btnLoading)
        btnLoading.setOnClickListener {
            btnLoading.text = "Cargando..."
            btnLoading.isEnabled = false // Deshabilita el botón temporalmente

            // Simular un proceso de red o cálculo de 2 segundos
            Handler(Looper.getMainLooper()).postDelayed({
                btnLoading.text = "Iniciar Carga"
                btnLoading.isEnabled = true // Vuelve a habilitar el botón
                mostrarMensaje("Carga completada")
            }, 2000)
        }
    }

    // Función auxiliar para no repetir el código del Toast
    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }
}