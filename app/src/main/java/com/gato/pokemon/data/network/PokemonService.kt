package com.gato.pokemon.data.network

import com.gato.pokemon.models.PokeApiResById
import com.gato.pokemon.models.PokemonApiResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url




interface PokemonService {

    @GET("api/v2/pokemon-species/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int, @Query("offset") offset: Int
    ): PokemonApiResponse

    @GET
    suspend fun getPokemonListLoadMore(@Url url: String?): PokemonApiResponse

    @GET
    suspend fun getPokemonInfo(@Url url: String?): PokeApiResById
}