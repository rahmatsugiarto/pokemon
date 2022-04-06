package com.gato.pokemon.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.pokemon.models.PokemonResult
import com.gato.pokemon.util.Constant.CATCH_POKEMON_TABLE

@Entity(tableName = CATCH_POKEMON_TABLE)
class PokemonCatchEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var result: PokemonResult
)