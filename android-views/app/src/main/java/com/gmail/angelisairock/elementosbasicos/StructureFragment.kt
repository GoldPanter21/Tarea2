package com.gmail.angelisairock.elementosbasicos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class StructureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_structure, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Configurar acciones de la Barra Superior
        val toolbar = view.findViewById<MaterialToolbar>(R.id.demoToolbar)
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_search -> {
                    Toast.makeText(requireContext(), "Acción: Buscar elemento", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_settings -> {
                    Toast.makeText(requireContext(), "Acción: Abrir Ajustes", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // Agregar un ícono de menú hamburguesa decorativo a la barra
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size)
        toolbar.setNavigationOnClickListener {
            Toast.makeText(requireContext(), "Abriendo menú lateral...", Toast.LENGTH_SHORT).show()
        }

        // 2. Configurar la Barra Inferior
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.demoBottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            Toast.makeText(requireContext(), "Navegando a la sección: ${item.title}", Toast.LENGTH_SHORT).show()
            true
        }
    }
}