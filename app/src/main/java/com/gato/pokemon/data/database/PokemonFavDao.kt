package com.gato.pokemon.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.gato.pokemon.util.Constant.FAVORITE_POKE_TABLE
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonFavDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertFavPokemon(recipe: PokemonFavEntity)

    @Delete
    suspend fun deleteFavPokemon(pokemonFav: PokemonFavEntity)

    @Query("SELECT * FROM $FAVORITE_POKE_TABLE ORDER BY id ASC")
    fun readFavPokemon(): Flow<List<PokemonFavEntity>>

}