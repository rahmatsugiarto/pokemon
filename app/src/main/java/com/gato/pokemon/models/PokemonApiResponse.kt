package com.gato.pokemon.models


import com.google.gson.annotations.SerializedName

data class PokemonApiResponse(
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("results")
    val pokemonResults: List<PokemonResult>
)