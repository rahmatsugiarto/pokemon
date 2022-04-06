package com.gato.pokemon.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gato.pokemon.models.PokemonResult
import com.gato.pokemon.util.Constant.FAVORITE_POKE_TABLE

@Entity(tableName = FAVORITE_POKE_TABLE)
class PokemonFavEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: PokemonResult
)

