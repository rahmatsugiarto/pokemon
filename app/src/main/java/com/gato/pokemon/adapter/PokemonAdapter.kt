package com.gato.pokemon.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gato.pokemon.databinding.PokeRowLayoutBinding
import com.gato.pokemon.models.PokemonResult
import com.gato.pokemon.ui.fragment.PokemonFragmentDirections
import com.gato.pokemon.util.PokemonDiffUtil

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private var listItem = emptyList<PokemonResult>()

    class PokemonViewHolder(private val binding: PokeRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: PokemonResult) {
            binding.itemPokeName.text = result.name
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

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = listItem[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            Log.d("mamat", "onBindViewHolder: $item")
            val action =
                PokemonFragmentDirections.actionPokemonFragmentToDetailFragment(item)
            holder.itemView.findNavController().navigate(action)


        }
    }

    fun setData(listItem: List<PokemonResult>) {
        val diffCallback = PokemonDiffUtil(this.listItem, listItem)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listItem = listItem.toList()
        diffResult.dispatchUpdatesTo(this)
    }
}
