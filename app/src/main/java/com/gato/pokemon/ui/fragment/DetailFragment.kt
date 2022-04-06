package com.gato.pokemon.ui.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.gato.pokemon.data.database.PokemonFavEntity
import com.gato.pokemon.databinding.FragmentDetailBinding
import com.gato.pokemon.viewmodels.PokeDetailViewModel
import com.gato.pokemon.viewmodels.PokeFavViewModel

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()
    private val detailViewModel by viewModels<PokeDetailViewModel>()
    private val favViewModel by viewModels<PokeFavViewModel>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        detailViewModel.suksesPoke.observe(viewLifecycleOwner) {
            val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${it.id}.png"
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

        detailViewModel.errorPoke.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        detailViewModel.loadingPoke.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        detailViewModel.getDetailPoke(args.urlDetail)

        binding.addFavoriteButton.setOnClickListener {
            val pokeFavEntity = PokemonFavEntity(0,args.detail)
            favViewModel.insertFavPokemon(pokeFavEntity)
            Toast.makeText(context, "Added to Favorite", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}