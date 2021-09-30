package com.example.mortyapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mortyapp.databinding.ActivityMainBinding
import com.example.mortyapp.data.remote.dtos.GetCharacterDTO
import com.example.mortyapp.network.MortyAPIService
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    val TAG : String = "Morty_Debug"

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
        binding.nameTextView.text = testString

        val random = (1..500).random() // generated random from 0 to 10 included
        getCharacterById(random)

        binding.refreshBtn.setOnClickListener {
            val rand = (1..500).random() // generated random from 0 to 10 included
            getCharacterById(rand)
        }

    }


    private fun getCharacterById(randomId : Int) {
        Toast.makeText(this@MainActivity, randomId.toString(), Toast.LENGTH_SHORT).show()
        mortyAPIService.getCharacterById(randomId).enqueue(object : Callback<GetCharacterDTO>{
            override fun onResponse(call: Call<GetCharacterDTO>, response: Response<GetCharacterDTO>) {
                Log.i(TAG, response.toString())

                if (!response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Unsuccessful response !", Toast.LENGTH_SHORT).show()
                    return
                }

                val body = response.body()!!

                Picasso.get()
                    .load(body.image)
                    .into(binding.characterImage)

                binding.nameTextView.text = body.name
                binding.aliveTextView.text = body.status
                binding.originTextView.text = body.origin.name
                binding.speciesTextView.text = body.species

                if (body.gender.equals("Male", true)) {
                    binding.genderImage.setImageResource(R.drawable.ic_male_faces_24)
                } else {
                    binding.genderImage.setImageResource(R.drawable.ic_female_face_24)
                }

                binding.genderImage.setOnClickListener{
                    Toast.makeText(this@MainActivity, body.gender, Toast.LENGTH_SHORT).show()
                }


            }

            override fun onFailure(call: Call<GetCharacterDTO>, t: Throwable) {
                Log.i(TAG, t.message?:"Null message")

            }
        })
    }


}