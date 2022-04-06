package com.gato.pokemon.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PokemonFavEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PokemonTypeConverter::class)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonFavDao
}