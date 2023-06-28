package com.example.segundoparcial_lucianonatiello

import android.content.Intent
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

class MainActivity : AppCompatActivity() {

    private lateinit var botonChisteRandom : Button
    private lateinit var botonMenuChiste : Button
    private lateinit var chisteTexto : TextView
    private lateinit var imagenChuck : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botonChisteRandom = findViewById(R.id.BotonChiste)
        botonMenuChiste = findViewById(R.id.MenuChiste)
        chisteTexto = findViewById(R.id.Chiste)
        imagenChuck = findViewById(R.id.chuckImage)
        val url = "https://staticassets.dreamfactory.com/chuck_facts-467532-edited.jpg"

        Picasso.get().load(url).into(imagenChuck)
        getJokeRandom()

        botonChisteRandom.setOnClickListener {
            getJokeRandom()
        }

        botonMenuChiste.setOnClickListener {

            val intent = Intent(this, listadoCategorias:: class.java)
            startActivity(intent)
        }
    }

    private fun getJokeRandom(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getJokeRandom("random")
            val response= call.body()

            runOnUiThread{
                if (call.isSuccessful) {
                    val jokeCall = response?.value
                    if (jokeCall != null) {
                        chisteTexto.text = jokeCall

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

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}