package com.gato.pokemon.adapter

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gato.pokemon.R
import com.gato.pokemon.data.database.entities.PokemonFavEntity
import com.gato.pokemon.databinding.FavRowLayoutBinding
import com.gato.pokemon.ui.fragment.FavoriteFragmentDirections
import com.gato.pokemon.util.PokemonDiffUtil
import com.gato.pokemon.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fav_row_layout.view.*

class PokemonFavAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<PokemonFavAdapter.PokemonFavViewHolder>(), ActionMode.Callback {

    private var multiSelection = false

    private lateinit var rootView: View

    private lateinit var mActionMode: ActionMode

    private var selectedPokemon = arrayListOf<PokemonFavEntity>()
    private var myViewHolder = arrayListOf<PokemonFavViewHolder>()
    private var listItem = emptyList<PokemonFavEntity>()

    class PokemonFavViewHolder(private val binding: FavRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: PokemonFavEntity) {
            binding.itemPokeName.text = result.result.name
        }

        companion object {
            fun from(parent: ViewGroup): PokemonFavViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavRowLayoutBinding.inflate(layoutInflater, parent, false)
                return PokemonFavViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonFavViewHolder {
        return PokemonFavViewHolder.from(parent)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holderFav: PokemonFavViewHolder, position: Int) {
        myViewHolder.add(holderFav)
        rootView = holderFav.itemView.rootView
        val item = listItem[position]
        holderFav.bind(item)

        /**
         * Single Click Listener
         * */
        holderFav.itemView.favoritePokemonRowLayout.setOnClickListener {
            if (multiSelection) {
                applySelection(holderFav, item)
            } else {
                val action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(
                        item.result
                    )
                holderFav.itemView.findNavController().navigate(action)
            }
        }

        /**
         * Long Click Listener
         * */
        holderFav.itemView.favoritePokemonRowLayout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holderFav, item)
                true
            } else {
                multiSelection = false
                false
            }

        }
    }
    private fun applySelection(holder: PokemonFavViewHolder, currentRecipes: PokemonFavEntity) {
        if (selectedPokemon.contains(currentRecipes)) {
            selectedPokemon.remove(currentRecipes)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedPokemon.add(currentRecipes)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.purple_500)
            applyActionModeTitle()
        }
    }
    private fun changeRecipeStyle(holder: PokemonFavViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.itemView.fav_poke_cardView.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity,
                backgroundColor
            )
        )
        holder.itemView.fav_poke_cardView.strokeColor = ContextCompat.getColor(
            requireActivity,
            strokeColor
        )
    }
    private fun applyActionModeTitle() {
        when (selectedPokemon.size) {
            0 -> mActionMode.finish()
            1 -> mActionMode.title = "${selectedPokemon.size} item selected"
            else -> mActionMode.title = "${selectedPokemon.size} items selected"
        }
    }

    fun setData(newData: List<PokemonFavEntity>) {
        val recipesDiffUtil = PokemonDiffUtil(listItem, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        listItem = newData
        diffUtilResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        mActionMode = mode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = true

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        if (menu?.itemId == R.id.delete_favorite_pokemon_menu) {
            selectedPokemon.forEach {
                mainViewModel.deleteFavPokemon(it)
            }
            showSnackBar("${selectedPokemon.size} Removed")

            multiSelection = false
            selectedPokemon.clear()
            actionMode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolder.forEach {
            changeRecipeStyle(it, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedPokemon.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }
    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }

}