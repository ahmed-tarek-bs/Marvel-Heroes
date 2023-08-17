package com.example.marvelcharacters.ui.characterlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.marvelcharacters.databinding.ItemHeroBinding
import com.example.marvelcharacters.domain.model.MarvelCharacter
import com.example.marvelcharacters.utils.CharacterDiffIUtil

class CharactersAdapter(
    private val onCharacterClicked: (MarvelCharacter) -> Unit
) : ListAdapter<MarvelCharacter, CharactersAdapter.CharacterViewHolder>(CharacterDiffIUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = ItemHeroBinding.inflate(inflater, parent, false)

        return CharacterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bindCharacter(getItem(position))
    }

    inner class CharacterViewHolder(
        private val binding: ItemHeroBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onCharacterClicked(getItem(layoutPosition))
            }
        }

        fun bindCharacter(character: MarvelCharacter) {
            binding.apply {
                heroName.text = character.name ?: "--"
                character.imgUrl.portraitUrl?.let { heroImg.load(it) }
            }
        }

    }
}