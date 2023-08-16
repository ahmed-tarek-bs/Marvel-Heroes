package com.example.marvelcharacters.utils

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.marvelcharacters.domain.model.MarvelCharacter

class CharacterDiffIUtil : ItemCallback<MarvelCharacter>() {

    override fun areItemsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: MarvelCharacter, newItem: MarvelCharacter): Boolean {
        return newItem == oldItem
    }
}