package com.example.mortyapp.network

import com.example.mortyapp.data.remote.dtos.GetCharacterDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MortyAPIService {

    @GET("character/{charId}")
    fun getCharacterById(
        @Path ("charId") charId : Int
    ) : Call<GetCharacterDTO>
}