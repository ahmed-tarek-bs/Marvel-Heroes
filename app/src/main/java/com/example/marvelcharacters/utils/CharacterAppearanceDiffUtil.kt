package com.example.marvelcharacters.utils

import androidx.recyclerview.widget.DiffUtil.ItemCallback

class CharacterAppearanceDiffUtil : ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}