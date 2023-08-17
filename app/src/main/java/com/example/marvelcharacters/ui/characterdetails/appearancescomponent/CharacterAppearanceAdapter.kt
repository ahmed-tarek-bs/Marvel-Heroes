package com.example.marvelcharacters.ui.characterdetails.appearancescomponent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.databinding.ItemCharacterAppearanceBinding
import com.example.marvelcharacters.utils.CharacterAppearanceDiffUtil

class CharacterAppearanceAdapter :
    ListAdapter<String, CharacterAppearanceAdapter.CharacterAppearanceViewHolder>(
        CharacterAppearanceDiffUtil()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterAppearanceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = ItemCharacterAppearanceBinding.inflate(inflater, parent, false)

        return CharacterAppearanceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharacterAppearanceViewHolder, position: Int) {
        holder.bindCharacterAppearance(getItem(position))
    }

    inner class CharacterAppearanceViewHolder(
        private val binding: ItemCharacterAppearanceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindCharacterAppearance(appearance: String) {
            binding.appearanceName.text = appearance
        }

    }
}