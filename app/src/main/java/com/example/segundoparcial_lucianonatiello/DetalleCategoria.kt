package com.example.segundoparcial_lucianonatiello

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetalleCategoria : AppCompatActivity() {

    private lateinit var textViewCategoria: TextView
    private lateinit var textViewChisteCategoria: TextView
    private lateinit var imageViewCategoria: ImageView
    private lateinit var botonChisteCategoria: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_categoria)

        textViewCategoria = findViewById(R.id.textViewCategoria)
        textViewChisteCategoria = findViewById(R.id.textViewChisteCategoria)
        imageViewCategoria = findViewById(R.id.imagenCategoria)
        botonChisteCategoria = findViewById(R.id.buttonCategoria)

        val bundle = intent.extras
        val categoriaDetalle = bundle?.getString("categoria", "").toString()

        Picasso.get().load("https://avatars.githubusercontent.com/u/17794049?s=280&v=4").into(imageViewCategoria)
        textViewCategoria.text = "Categoria: $categoriaDetalle"

        getJokeRandomCategory(categoriaDetalle)

        botonChisteCategoria.setOnClickListener {
            getJokeRandomCategory(categoriaDetalle)
        }


    }

    private fun getJokeRandomCategory(categoriaDetalle: String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit3().create(ApiService::class.java).getJokeRandomCategory("random?category=$categoriaDetalle")
            val response= call.body()

            runOnUiThread{
                if (call.isSuccessful) {
                    val jokeCall = response?.value
                    if (jokeCall != null) {
                        textViewChisteCategoria.text = jokeCall

                    }
                } else {
                    showError()
                }
            }
        }
    }



    private fun showError() {
        Toast.makeText(this, "fallo en la llamada", Toast.LENGTH_SHORT).show()
    }

    private fun getRetrofit3(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}