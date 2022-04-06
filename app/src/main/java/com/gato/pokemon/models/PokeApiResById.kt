package com.gato.pokemon.models


import com.google.gson.annotations.SerializedName

data class PokeApiResById(
    @SerializedName("color")
    val color: Color,
    @SerializedName("base_happiness")
    val baseHappiness: Int,
    @SerializedName("capture_rate")
    val captureRate: Int,
    @SerializedName("habitat")
    val habitat: Habitat,
    @SerializedName("is_baby")
    val isBaby: Boolean,
    @SerializedName("is_legendary")
    val isLegendary: Boolean,
    @SerializedName("is_mythical")
    val isMythical: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Int,

)

data class Color(
    @SerializedName("name")
    val name: String,
)

data class Habitat(
    @SerializedName("name")
    val name: String,
)
