package com.example.marvelcharacters.ui.characterdetails.appearancescomponent

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.databinding.LayoutCharacterSectionBinding


class CharacterSectionLayout @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val binding: LayoutCharacterSectionBinding

    private val title: TextView
    private val recyclerView: RecyclerView

    private val characterAppearanceAdapter: CharacterAppearanceAdapter by lazy {
        CharacterAppearanceAdapter()
    }

    init {
        binding = LayoutCharacterSectionBinding.inflate(LayoutInflater.from(context), this, true)

        title = binding.titleStories
        recyclerView = binding.recyclerItems.apply {
            adapter = characterAppearanceAdapter
        }
    }

    fun submitList(appearances: List<String>) {
        characterAppearanceAdapter.submitList(appearances)
    }

    fun setTitle(title: String) {
        this.title.text = title
    }

}