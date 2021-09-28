package com.example.mortyapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mortyapp.databinding.ActivityMainBinding
import com.example.mortyapp.network.MortyAPIService
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    val TAG : String = "Debug"

    @Inject
    @Named("useThisString")
    lateinit var testString : String

    @Inject
    lateinit var mortyAPIService: MortyAPIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Log.d("Main Activity", testString)
        binding.tvDisplayText.text = testString

        mortyAPIService.getCharacterById(25).enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.i(TAG, response.toString())

                if (!response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Unsuccessful response !", Toast.LENGTH_SHORT).show()
                    return
                }
                binding.tvDisplayText.text = response.body().toString().substring(15, 20)
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i(TAG, t.message?:"Null message")

            }
        })
    }
}