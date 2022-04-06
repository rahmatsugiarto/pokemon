package com.gato.pokemon.data.network

import com.gato.pokemon.util.Constant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pokemonService: PokemonService = retrofit.create(PokemonService::class.java)
}