package com.gato.pokemon.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gato.pokemon.R
import com.gato.pokemon.adapter.PokemonAdapter
import com.gato.pokemon.databinding.FragmentPokemonBinding
import com.gato.pokemon.viewmodels.PokeViewModel

class PokemonFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentPokemonBinding? = null
    private val binding get() = _binding!!

    private val pokeViewModel by viewModels<PokeViewModel>()
    private val mAdapter by lazy { PokemonAdapter() }
    private lateinit var layoutManager: LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)
        layoutManager = LinearLayoutManager(context)
        setHasOptionsMenu(true)

        setupRecyclerView()
        binding.rvPokemon.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                    pokeViewModel.getLoadMore()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        binding.swipeRefresh.setOnRefreshListener(this)
        pokeViewModel.listPoke.observe(viewLifecycleOwner) {
            mAdapter.setData(it)
        }

        pokeViewModel.errorPoke.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        pokeViewModel.loadingPoke.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        pokeViewModel.getListPoke()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.pokemon_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.to_favorite) {
            val action = PokemonFragmentDirections.actionPokemonFragmentToFavoriteFragment()
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        binding.rvPokemon.adapter = mAdapter
        binding.rvPokemon.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onRefresh() {
        pokeViewModel.clearData()
        pokeViewModel.getListPoke()
        binding.swipeRefresh.isRefreshing = false
    }

}