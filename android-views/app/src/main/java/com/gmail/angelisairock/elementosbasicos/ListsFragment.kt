package com.gmail.angelisairock.elementosbasicos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ListsFragment : Fragment() {

    // Datos persistentes para simular la recarga (Pull to refresh)
    private val datosOriginales = listOf(
        "Algoritmos Genéticos", "Optimización PSO", "Transformación Sigmoidal",
        "Ecualización de Histograma", "Sockets TCP", "Imágenes DICOM",
        "ATmega8535 (Ensamblador)", "Motor a Pasos", "Servomotor",
        "Enrutamiento Estático", "RIP v2", "Contenedores Docker",
        "Máquina Virtual Azure", "Stardew Valley", "Celeste"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

        // Configurar el adaptador del ViewPager (Las 3 pestañas)
        viewPager.adapter = PagerAdapter(this)

        // Vincular pestañas con el contenido deslizable
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Vertical"
                1 -> "Cuadrícula"
                else -> "Encabezados"
            }
        }.attach()
    }

    // --- ADAPTADOR DEL VIEWPAGER (Controla las 3 pantallas) ---
    inner class PagerAdapter(fragment: Fragment) : RecyclerView.Adapter<PagerAdapter.PageViewHolder>() {

        inner class PageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
            val swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
            val emptyStateLayout: LinearLayout = view.findViewById(R.id.emptyStateLayout)
        }

        // Forzar la creación de una vista nueva por pestaña para evitar conflictos de LayoutManager
        override fun getItemViewType(position: Int): Int = position

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_pagina_lista, parent, false)
            return PageViewHolder(view)
        }

        override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
            val datosActuales = datosOriginales.toMutableList()

            when (position) {
                0 -> configurarListaVertical(holder, datosActuales)
                1 -> configurarCuadricula(holder, datosActuales)
                2 -> configurarListaEncabezados(holder)
            }
        }

        override fun getItemCount(): Int = 3

        private fun configurarListaVertical(holder: PageViewHolder, datos: MutableList<String>) {
            val adapter = ElementosAdapter(datos) { item ->
                Toast.makeText(requireContext(), "Detalle de: $item", Toast.LENGTH_SHORT).show()
            }
            holder.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            holder.recyclerView.adapter = adapter

            // Swipe to Delete (Deslizar para eliminar)
            val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(r: RecyclerView, v: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val pos = viewHolder.adapterPosition
                    datos.removeAt(pos)
                    adapter.notifyItemRemoved(pos)
                    holder.emptyStateLayout.visibility = if (datos.isEmpty()) View.VISIBLE else View.GONE
                }
            })
            itemTouchHelper.attachToRecyclerView(holder.recyclerView)

            // Pull to Refresh (Arrastrar para actualizar)
            holder.swipeRefresh.setOnRefreshListener {
                datos.clear()
                datos.addAll(datosOriginales) // Restaura los 15 elementos
                adapter.notifyDataSetChanged()
                holder.emptyStateLayout.visibility = View.GONE
                holder.swipeRefresh.isRefreshing = false
                Toast.makeText(requireContext(), "Lista actualizada", Toast.LENGTH_SHORT).show()
            }
        }

        private fun configurarCuadricula(holder: PageViewHolder, datos: MutableList<String>) {
            holder.swipeRefresh.isEnabled = false // Desactivamos el refresh en la cuadrícula
            holder.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // 2 columnas
            holder.recyclerView.adapter = ElementosAdapter(datos) { item ->
                Toast.makeText(requireContext(), "Seleccionado: $item", Toast.LENGTH_SHORT).show()
            }
        }

        private fun configurarListaEncabezados(holder: PageViewHolder) {
            holder.swipeRefresh.isEnabled = false
            holder.recyclerView.layoutManager = LinearLayoutManager(requireContext())

            // Estructura combinando encabezados e items
            val listaMixta = listOf(
                ItemMixto.Header("Visión por Computadora"),
                ItemMixto.Item("Ecualización de Histograma"),
                ItemMixto.Item("Transformación Sigmoidal"),
                ItemMixto.Header("Videojuegos"),
                ItemMixto.Item("Stardew Valley"),
                ItemMixto.Item("Celeste")
            )
            holder.recyclerView.adapter = EncabezadosAdapter(listaMixta)
        }
    }

    // --- ADAPTADOR PARA LISTA SIMPLE Y CUADRÍCULA ---
    inner class ElementosAdapter(private val lista: List<String>, private val onClick: (String) -> Unit) : RecyclerView.Adapter<ElementosAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvText: TextView = view.findViewById(R.id.tvItemText)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lista, parent, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = lista[position]
            holder.tvText.text = item
            holder.itemView.setOnClickListener { onClick(item) }
        }
        override fun getItemCount() = lista.size
    }

    // --- ESTRUCTURA Y ADAPTADOR PARA LISTA CON ENCABEZADOS ---
    sealed class ItemMixto {
        data class Header(val titulo: String) : ItemMixto()
        data class Item(val texto: String) : ItemMixto()
    }

    inner class EncabezadosAdapter(private val lista: List<ItemMixto>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun getItemViewType(position: Int): Int = if (lista[position] is ItemMixto.Header) 0 else 1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return if (viewType == 0) {
                val view = inflater.inflate(R.layout.item_encabezado, parent, false)
                object : RecyclerView.ViewHolder(view) {}
            } else {
                val view = inflater.inflate(R.layout.item_lista, parent, false)
                object : RecyclerView.ViewHolder(view) {}
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (val elemento = lista[position]) {
                is ItemMixto.Header -> holder.itemView.findViewById<TextView>(R.id.tvHeaderText).text = elemento.titulo
                is ItemMixto.Item -> holder.itemView.findViewById<TextView>(R.id.tvItemText).text = elemento.texto
            }
        }
        override fun getItemCount() = lista.size
    }
}