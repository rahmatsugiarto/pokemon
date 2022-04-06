package com.gato.pokemon.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gato.pokemon.R
import com.gato.pokemon.adapter.PokemonFavAdapter
import com.gato.pokemon.databinding.FragmentFavoriteBinding
import com.gato.pokemon.ui.MainActivity
import com.gato.pokemon.viewmodels.MainViewModel


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()
    private val mAdapter by lazy { PokemonFavAdapter(requireActivity(), mainViewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.favorite)


        mainViewModel.readFavPokemon.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.noDataImageView.visibility = View.INVISIBLE
                binding.noDataTextView.visibility = View.INVISIBLE
                binding.rvFavorite.visibility = View.VISIBLE
                mAdapter.setData(it)
            } else {
                binding.noDataImageView.visibility = View.VISIBLE
                binding.noDataTextView.visibility = View.VISIBLE
                binding.rvFavorite.visibility = View.INVISIBLE
            }
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
        mAdapter.clearContextualActionMode()
    }
}