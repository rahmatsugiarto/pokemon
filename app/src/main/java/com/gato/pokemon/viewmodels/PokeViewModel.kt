package com.gato.pokemon.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gato.pokemon.data.network.ApiClient
import com.gato.pokemon.models.PokemonApiResponse
import com.gato.pokemon.models.PokemonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PokeViewModel : ViewModel() {
    val listPoke = MutableLiveData<MutableList<PokemonResult>>()
    private val _listPoke = mutableListOf<PokemonResult>()
    val loadingPoke = MutableLiveData<Boolean>()
    val successPoke = MutableLiveData<PokemonApiResponse?>()
    val errorPoke = MutableLiveData<String>()

    private var offset = 0
    private var limit = 20

    fun clearData() {
        _listPoke.clear()
        listPoke.value = _listPoke
    }

    fun getListPoke() {
        loadingPoke.value = true
        viewModelScope.launch {
            launch(Dispatchers.Main) {
                try {
                    val response = ApiClient.pokemonService.getPokemonList(limit, offset)
                    successPoke.value = response
                    Log.d("getListPoke", "getListPoke: Runn")

                    response.pokemonResults.forEach {
                        _listPoke.add(it)
                    }
                    listPoke.value = _listPoke
                } catch (e: Exception) {
                    e.printStackTrace()
                    errorPoke.value = e.message
                }
                loadingPoke.value = false
            }
        }
    }

    fun getLoadMore() {
        loadingPoke.value = true
        Log.d("banana", "getLoadMore: ${successPoke.value?.next.toString()}")
        viewModelScope.launch {
            delay(1000)
            launch(Dispatchers.Main) {
                try {
                    val response =
                        ApiClient.pokemonService.getPokemonListLoadMore(successPoke.value?.next.toString())
                    successPoke.value = response
                    response.pokemonResults.forEach {
                        _listPoke.add(it)
                    }
                    listPoke.value = _listPoke
                    Log.d("banana", "getLoadMore: ${response.pokemonResults.size}")
                } catch (e: Exception) {
                    e.printStackTrace()
                    errorPoke.value = e.message
                }
                loadingPoke.value = false
            }
        }
    }
}