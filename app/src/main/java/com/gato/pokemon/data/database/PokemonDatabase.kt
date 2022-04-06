package com.gato.pokemon.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gato.pokemon.data.database.entities.PokemonCatchEntity
import com.gato.pokemon.data.database.entities.PokemonFavEntity

@Database(
    entities = [PokemonFavEntity::class, PokemonCatchEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PokemonTypeConverter::class)
abstract class PokemonDatabase : RoomDatabase() {
    companion object {
        var INSTANCE: PokemonDatabase? = null
        fun getDatabase(context: Context): PokemonDatabase? {
            if (INSTANCE == null) {
                synchronized(PokemonDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PokemonDatabase::class.java,
                        "pokemon_database"
                    ).build()
                }
            }
            return INSTANCE
        }

    }

    abstract fun pokemonFavDao(): PokemonDao
}