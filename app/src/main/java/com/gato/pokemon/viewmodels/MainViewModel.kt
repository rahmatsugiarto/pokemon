package com.gato.pokemon.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.gato.pokemon.data.database.PokemonDao
import com.gato.pokemon.data.database.PokemonDatabase
import com.gato.pokemon.data.database.entities.PokemonCatchEntity
import com.gato.pokemon.data.database.entities.PokemonFavEntity
import com.gato.pokemon.data.network.ApiClient
import com.gato.pokemon.models.PokeApiResById
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var dao: PokemonDao?
    private var db: PokemonDatabase? = PokemonDatabase.getDatabase(application)

    val loadingPoke = MutableLiveData<Boolean>()
    val successPoke = MutableLiveData<PokeApiResById>()
    val errorPoke = MutableLiveData<String>()

    init {
        dao = db?.pokemonFavDao()
    }

    //Retrofit
    fun getDetailPoke(url: String) {
        loadingPoke.value = true
        viewModelScope.launch {
            launch(Dispatchers.Main) {
                try {
                    val response = ApiClient.pokemonService.getPokemonInfo(url)
                    successPoke.value = response
                } catch (e: Exception) {
                    e.printStackTrace()
                    errorPoke.value = e.message
                }
                loadingPoke.value = false
            }
        }
    }


    //Room
    var readFavPokemon: MutableLiveData<List<PokemonFavEntity>> =
        dao?.readFavPokemon()!!.asLiveData() as MutableLiveData<List<PokemonFavEntity>>
    var readCatchPokemon: LiveData<List<PokemonCatchEntity>> =
        dao?.readCatchPokemon()!!.asLiveData()

    fun insertFavPokemon(pokemonFavEntity: PokemonFavEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao?.insertFavPokemon(pokemonFavEntity)
        }
    }

    fun insertCatchPokemon(pokemonCatchEntity: PokemonCatchEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao?.insertCatchPokemon(pokemonCatchEntity)
        }
    }

    fun deleteFavPokemon(pokemonFavEntity: PokemonFavEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao?.deleteFavPokemon(pokemonFavEntity)
        }
    }

    fun deleteCatchPokemon(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dao?.deleteCatchPokemon(id)
        }
    }

}