package com.example.marvelcharacters.ui.characterlist

import com.example.marvelcharacters.domain.model.MarvelCharacter
import com.example.marvelcharacters.domain.repository.CharacterRepository
import com.example.marvelcharacters.utils.PaginationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : PaginationViewModel<MarvelCharacter>() {

    override val getData = suspend {
        characterRepository.getCharactersList(offset = currentOffset, pageSize = pageSize)
    }

}