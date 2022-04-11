package com.gato.pokemon.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.gato.pokemon.R
import com.gato.pokemon.data.database.entities.PokemonCatchEntity
import com.gato.pokemon.data.database.entities.PokemonFavEntity
import com.gato.pokemon.databinding.FragmentDetailBinding
import com.gato.pokemon.ui.MainActivity
import com.gato.pokemon.viewmodels.MainViewModel
import com.gato.pokemon.viewmodels.PokeViewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()
    private val mainViewModel by viewModels<MainViewModel>()
    private val pokeViewModel by viewModels<PokeViewModel>()

    private var savedCatch = false

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.detail)


        checkFav()
        checkCatch()

        mainViewModel.successPoke.observe(viewLifecycleOwner) {
            val url =
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${it.id}.png"
            binding.apply {
                pokeImageView.load(url)
                pokeNameTextView.text = it.name
                pokeColorTextView.text = "Color: ${it.color.name}"
                pokeBaseHapTextView.text = "Base Happiness: ${it.baseHappiness}"
                pokeCapRateTextView.text = "Capture Rate: ${it.captureRate}"
                pokeHabitatTextView.text = "Habitat: ${it.habitat.name}"
                pokeIsBabyTextView.text = "Is Baby: ${it.isBaby}"
                pokeIsLegendaryTextView.text = "Is Legendary: ${it.isLegendary}"
                pokeIsMythicalTextView.text = "Is Mythical: ${it.isMythical}"
            }

        }

        mainViewModel.errorPoke.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        mainViewModel.loadingPoke.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        mainViewModel.getDetailPoke(args.detail.url)

        binding.addFavoriteButton.setOnClickListener {
            val pokeFavEntity = PokemonFavEntity(0, args.detail)
            this.mainViewModel.insertFavPokemon(pokeFavEntity)
            Toast.makeText(context, "Added to Favorite", Toast.LENGTH_SHORT).show()
        }

        binding.catchPokeButton.setOnClickListener {
            mainViewModel.successPoke.observe(viewLifecycleOwner) {
                if (!savedCatch) {
                    catchPokeWithRate(it.captureRate)
                } else {
                    mainViewModel.deleteCatchPokemon(it.id)
                    binding.catchPokeButton.text = getString(R.string.catch_pokemon)
                    savedCatch = false
                    Log.d("detele", "onCreateView: deleted")
                    Toast.makeText(context, "Realese success", Toast.LENGTH_LONG).show()
                }
            }
        }
        return binding.root
    }

    private fun catchPokeWithRate(rate: Int) {
        val pokeFavEntity = PokemonCatchEntity(mainViewModel.successPoke.value!!.id, args.detail)
        val random = (1..100).random()
        if (random <= rate) {
            mainViewModel.insertCatchPokemon(pokeFavEntity)
            Toast.makeText(context, "Catch Pokemon", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Failed to Catch Pokemon", Toast.LENGTH_LONG).show()

        }
    }


    private fun checkCatch() {
        mainViewModel.readCatchPokemon.observe(viewLifecycleOwner) {
            try {
                for (savedRecipe in it) {
                    if (savedRecipe.result.url == args.detail.url) {
                        binding.catchPokeButton.text = getString(R.string.release)
                        savedCatch = true
                    } else {
                        binding.catchPokeButton.text = getString(R.string.catch_pokemon)
                        savedCatch = false
                    }
                }
            } catch (e: Exception) {
                Log.e("DetailsActivity", e.message.toString())
            }
        }
    }

    private fun checkFav() {
        mainViewModel.readFavPokemon.observe(viewLifecycleOwner) { pokemonFav ->
            try {
                for (savedRecipe in pokemonFav) {
                    if (savedRecipe.result.name == args.detail.name) {
                        binding.addFavoriteButton.visibility = View.GONE
                        Log.d(
                            "saved",
                            "checkFav: saved ${args.detail.name} ${savedRecipe.result.name}"
                        )
                    } else {
                        binding.addFavoriteButton.visibility = View.VISIBLE
                        Log.d(
                            "saveddd",
                            "checkFav: saved ${args.detail.name} ${savedRecipe.result.name}"
                        )

                    }
                }
            } catch (e: Exception) {
                Log.e("DetailsActivity", e.message.toString())
            }
        }
    }
    override fun onResume() {
        super.onResume()
        Log.d("onResume", "onResume: ${pokeViewModel.listPoke.value?.size}")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}