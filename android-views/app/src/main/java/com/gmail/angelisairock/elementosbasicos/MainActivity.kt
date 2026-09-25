package com.gmail.angelisairock.elementosbasicos

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var menuContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        menuContainer = findViewById(R.id.menuContainer)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        // Asignar listeners a los 6 botones del menú
        findViewById<Button>(R.id.btnInput).setOnClickListener { openSection(InputFragment(), "1. Entrada de Texto") }
        findViewById<Button>(R.id.btnButtons).setOnClickListener { openSection(ButtonsFragment(), "2. Botones y Acciones") }
        findViewById<Button>(R.id.btnSelection).setOnClickListener { openSection(SelectionFragment(), "3. Selección") }
        findViewById<Button>(R.id.btnLists).setOnClickListener { openSection(ListsFragment(), "4. Listas y Colecciones") }
        findViewById<Button>(R.id.btnInfo).setOnClickListener { openSection(InfoFragment(), "5. Información") }
        findViewById<Button>(R.id.btnStructure).setOnClickListener { openSection(StructureFragment(), "6. Contenedores") }

        // Escuchar la pila de navegación para restaurar el menú al regresar
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                // Si la pila está vacía, estamos en la pantalla principal
                menuContainer.visibility = View.VISIBLE
                supportActionBar?.title = "Catálogo de UI"
                supportActionBar?.setDisplayHomeAsUpEnabled(false) // Ocultar flecha de regreso
            }
        }
    }

    private fun openSection(fragment: Fragment, title: String) {
        menuContainer.visibility = View.GONE // Ocultar el menú principal
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Mostrar flecha de regreso

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null) // Guardar transacción para poder usar el botón "Atrás"
            .commit()
    }

    // Comportamiento al presionar la flecha de retroceso de la barra superior
    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }
}