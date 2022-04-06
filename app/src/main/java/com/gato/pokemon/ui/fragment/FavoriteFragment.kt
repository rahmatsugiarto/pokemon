package com.gato.pokemon.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gato.pokemon.adapter.PokemonFavAdapter
import com.gato.pokemon.databinding.FragmentFavoriteBinding
import com.gato.pokemon.viewmodels.PokeFavViewModel


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val pokeFavViewModel by viewModels<PokeFavViewModel>()
    private val mAdapter by lazy { PokemonFavAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        pokeFavViewModel.readFavPokemon.observe(viewLifecycleOwner) {
            mAdapter.setData(it)
        }

        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvFavorite.adapter = mAdapter
        binding.rvFavorite.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}