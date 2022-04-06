package com.gato.pokemon.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gato.pokemon.models.PokeApiResById
import com.gato.pokemon.data.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PokeDetailViewModel: ViewModel() {


    val loadingPoke = MutableLiveData<Boolean>()
    val suksesPoke = MutableLiveData<PokeApiResById>()
    val errorPoke = MutableLiveData<String>()

    fun getDetailPoke(url: String) {
        loadingPoke.value = true
        viewModelScope.launch {
            delay(2000)
            launch(Dispatchers.Main) {
                try {
                    val response = ApiClient.pokemonService.getPokemonInfo(url)
                    suksesPoke.value = response
                } catch (e: Exception) {
                    e.printStackTrace()
                    errorPoke.value = e.message
                }
                loadingPoke.value = false
            }
        }
    }

}