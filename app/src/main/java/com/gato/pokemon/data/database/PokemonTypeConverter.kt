package com.gato.pokemon.data.database

import androidx.room.TypeConverter
import com.gato.pokemon.models.PokemonApiResponse
import com.gato.pokemon.models.PokemonResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonTypeConverter {
    var gson = Gson()

//    @TypeConverter
//    fun foodRecipeToString(foodRecipe: PokemonApiResponse): String = gson.toJson(foodRecipe)
//
//    @TypeConverter
//    fun stringToRecipe(data: String): PokemonApiResponse {
//        val listType = object : TypeToken<PokemonApiResponse>() {}.type
//        return gson.fromJson(data, listType)
//    }

    @TypeConverter
    fun resultToString(result: PokemonResult): String = gson.toJson(result)

    @TypeConverter
    fun stringToResult(data: String): PokemonResult {
        val listType = object : TypeToken<PokemonResult>() {}.type
        return gson.fromJson(data, listType)
    }
}