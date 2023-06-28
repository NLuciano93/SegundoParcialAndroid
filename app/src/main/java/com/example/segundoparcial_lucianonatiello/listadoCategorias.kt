package com.example.segundoparcial_lucianonatiello

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class listadoCategorias : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoriasAdapter
    private var categoriasList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_categorias)

        recyclerView =  findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        getCategoriasListado()

        adapter = CategoriasAdapter(categoriasList)
        recyclerView.adapter = adapter

        adapter.onItemClickListener = { categoria ->
            val intent = Intent(this, DetalleCategoria::class.java)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
        }



    }

    private fun getCategoriasListado(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit2().create(ApiService::class.java).getCategories("categories")
            val response= call.body()

            runOnUiThread{
                if (call.isSuccessful) {
                    val list = response?.toMutableList()
                    if (list != null) {
                        for (cat in list)
                            categoriasList.add(cat)

                    }
                    adapter.notifyDataSetChanged()
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "fallo en la llamada", Toast.LENGTH_SHORT).show()
    }

    private fun getRetrofit2(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}