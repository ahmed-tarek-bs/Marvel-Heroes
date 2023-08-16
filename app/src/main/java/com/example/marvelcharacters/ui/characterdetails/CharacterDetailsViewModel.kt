package com.example.marvelcharacters.ui.characterdetails

import com.example.marvelcharacters.core.base.BaseViewModel
import com.example.marvelcharacters.core.base.update
import com.example.marvelcharacters.domain.model.MarvelCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor() : BaseViewModel() {

    private val _characterFlow = MutableStateFlow<MarvelCharacter?>(null)
    val characterState = _characterFlow.asStateFlow()

    fun setCharacter(character: MarvelCharacter) {
        _characterFlow.update(character)
    }

}