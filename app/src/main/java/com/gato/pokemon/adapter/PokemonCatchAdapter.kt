package com.gato.pokemon.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gato.pokemon.data.database.entities.PokemonCatchEntity
import com.gato.pokemon.databinding.PokeRowLayoutBinding
import com.gato.pokemon.models.PokemonResult
import com.gato.pokemon.ui.fragment.CatchedFragmentDirections
import com.gato.pokemon.ui.fragment.PokemonFragmentDirections
import com.gato.pokemon.util.PokemonDiffUtil

class PokemonCatchAdapter : RecyclerView.Adapter<PokemonCatchAdapter.PokemonViewHolder>() {

    private var listCatchPoke = emptyList<PokemonCatchEntity>()

    class PokemonViewHolder(private val binding: PokeRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: PokemonCatchEntity) {
            binding.itemPokeName.text = result.result.name
        }

        companion object {
            fun from(parent: ViewGroup): PokemonViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PokeRowLayoutBinding.inflate(layoutInflater, parent, false)
                return PokemonViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.from(parent)
    }

    override fun getItemCount(): Int = listCatchPoke.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = listCatchPoke[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            Log.d("mamat", "onBindViewHolder: $item")
            val action =
                CatchedFragmentDirections.actionCatchedFragmentToDetailFragment(item.result)
            holder.itemView.findNavController().navigate(action)


        }
    }


    fun setData(newData: List<PokemonCatchEntity>) {
        val recipesDiffUtil = PokemonDiffUtil(listCatchPoke, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        listCatchPoke = newData
        diffUtilResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
}