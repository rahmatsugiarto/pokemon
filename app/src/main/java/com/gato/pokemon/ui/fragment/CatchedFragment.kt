package com.gato.pokemon.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gato.pokemon.R
import com.gato.pokemon.adapter.PokemonCatchAdapter
import com.gato.pokemon.databinding.FragmentCatchedBinding
import com.gato.pokemon.ui.MainActivity
import com.gato.pokemon.viewmodels.MainViewModel


class CatchedFragment : Fragment() {
    private var _binding: FragmentCatchedBinding? = null
    private val binding get() = _binding!!
    private val mAdapter by lazy { PokemonCatchAdapter() }
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatchedBinding.inflate(inflater, container, false)

        (activity as MainActivity).supportActionBar?.title = getString(R.string.my_catched_pokemon)
        setupRecyclerView()

        mainViewModel.readCatchPokemon.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.noDataImageView.visibility = View.INVISIBLE
                binding.noDataTextView.visibility = View.INVISIBLE
                binding.rvCatched.visibility = View.VISIBLE
                mAdapter.setData(it)
            } else {
                binding.noDataImageView.visibility = View.VISIBLE
                binding.noDataTextView.visibility = View.VISIBLE
                binding.rvCatched.visibility = View.INVISIBLE
            }
        }
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvCatched.adapter = mAdapter
        binding.rvCatched.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}