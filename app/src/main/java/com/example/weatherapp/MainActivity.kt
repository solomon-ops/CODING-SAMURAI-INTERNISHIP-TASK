package com.example.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val apiKey = "b1b15e88fa797225412429c1c50c122a1"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etCity = findViewById<EditText>(R.id.etCity)
        val btnFetch = findViewById<Button>(R.id.btnFetch)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherApi = retrofit.create(WeatherApi::class.java)

        btnFetch.setOnClickListener {
            val city = etCity.text.toString()
            if (city.isNotEmpty()) {
                val call = weatherApi.getWeather(city, apiKey)
                call.enqueue(object : Callback<WeatherResponse> {
                    override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                        if (response.isSuccessful) {
                            val weather = response.body()
                            val info = "City: ${weather?.name}\n" +
                                    "Temp: ${weather?.main?.temp}°C\n" +
                                    "Humidity: ${weather?.main?.humidity}%\n" +
                                    "Condition: ${weather?.weather?.get(0)?.description}"
                            tvResult.text = info
                        } else {
                            tvResult.text = "City not found."
                        }
                    }

                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                        tvResult.text = "Error: ${t.message}"
                    }
                })
            } else {
                Toast.makeText(this, "Enter a city name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
