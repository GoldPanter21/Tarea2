package com.gmail.angelisairock.elementosbasicos

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class InfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Cargar imagen desde URL nativamente (Requiere permiso de INTERNET)
        val ivRemote = view.findViewById<ImageView>(R.id.ivRemote)
        cargarImagenRemota(ivRemote, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png")

        // 2. Toast
        view.findViewById<MaterialButton>(R.id.btnToast).setOnClickListener {
            Toast.makeText(requireContext(), "Esto es un Toast breve", Toast.LENGTH_SHORT).show()
        }

        // 3. Snackbar con Acción
        view.findViewById<MaterialButton>(R.id.btnSnackbar).setOnClickListener {
            Snackbar.make(view, "Archivo eliminado", Snackbar.LENGTH_LONG)
                .setAction("DESHACER") {
                    Toast.makeText(requireContext(), "Acción deshecha", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        // 4. Diálogo de confirmación
        view.findViewById<MaterialButton>(R.id.btnDialog).setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Confirmar Acción")
                .setMessage("¿Estás seguro de que deseas continuar? Esta acción no se puede revertir.")
                .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                .setPositiveButton("Aceptar") { dialog, _ ->
                    Toast.makeText(requireContext(), "Aceptado", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        // 5. Hoja Inferior (Bottom Sheet)
        view.findViewById<MaterialButton>(R.id.btnBottomSheet).setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext())

            // Creamos una vista de prueba programáticamente para el BottomSheet
            val sheetView = TextView(requireContext()).apply {
                text = "Contenido de la Hoja Inferior (Bottom Sheet).\n\nPuedes arrastrarme hacia abajo para cerrarme."
                textSize = 18f
                setPadding(48, 48, 48, 48)
            }

            bottomSheetDialog.setContentView(sheetView)
            bottomSheetDialog.show()
        }
    }

    // Función auxiliar para descargar una imagen en un hilo secundario
    private fun cargarImagenRemota(imageView: ImageView, urlString: String) {
        thread {
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val bitmap = BitmapFactory.decodeStream(connection.inputStream)

                // Actualizar la UI en el hilo principal
                requireActivity().runOnUiThread {
                    imageView.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}