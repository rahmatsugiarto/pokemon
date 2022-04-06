package com.gato.pokemon.viewmodels

import androidx.lifecycle.*
import com.gato.pokemon.data.database.PokemonFavDao
import com.gato.pokemon.data.database.PokemonFavEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokeFavViewModel() : ViewModel() {

    private var favDao: PokemonFavDao?
    private var favDb: PokemonFavdata? = UserDatabase.getDatabase(application)
    init {
        favDao = userDb?.favoriteUserDao()
    }

    val readFavPokemon: LiveData<List<PokemonFavEntity>> = favDao?.readFavPokemon()!!.asLiveData()

    fun insertFavPokemon(pokemonFavEntity: PokemonFavEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favDao?.insertFavPokemon(pokemonFavEntity)
        }
    }

    fun deleteFavPokemon(pokemonFavEntity: PokemonFavEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favDao?.deleteFavPokemon(pokemonFavEntity)
        }
    }


}