package com.example.segundoparcial_lucianonatiello

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CategoriasAdapter(private val categorias: List<String>) : RecyclerView.Adapter<CategoriasAdapter.ViewHolder>() {

    lateinit var onItemClickListener: (String) -> Unit

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val categoriaText : TextView = view.findViewById(R.id.itemListTextCategoria)

        fun bind (categoria: String) {
            categoriaText.text = ":: " + categoria+ " ::"


            view.setOnClickListener {
                onItemClickListener(categoria)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.bind(categoria)
    }
}