package com.gato.pokemon.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.gato.pokemon.data.database.entities.PokemonCatchEntity
import com.gato.pokemon.data.database.entities.PokemonFavEntity
import com.gato.pokemon.util.Constant.CATCH_POKEMON_TABLE
import com.gato.pokemon.util.Constant.FAVORITE_POKE_TABLE
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertFavPokemon(pokemonFav: PokemonFavEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertCatchPokemon(pokemonCatch: PokemonCatchEntity)

    @Delete
    suspend fun deleteFavPokemon(pokemonFav: PokemonFavEntity)

    @Query("SELECT * FROM $FAVORITE_POKE_TABLE ORDER BY id ASC")
    fun readFavPokemon(): Flow<List<PokemonFavEntity>>

    @Query("SELECT * FROM $CATCH_POKEMON_TABLE ORDER BY id ASC")
    fun readCatchPokemon(): Flow<List<PokemonCatchEntity>>

    @Query("DELETE FROM catch_pokemon_table WHERE catch_pokemon_table.id = :id")
    fun deleteCatchPokemon(id: Int): Int

}