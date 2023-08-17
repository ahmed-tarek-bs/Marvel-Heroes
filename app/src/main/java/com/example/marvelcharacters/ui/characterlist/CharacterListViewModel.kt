package com.example.marvelcharacters.ui.characterlist

import androidx.lifecycle.viewModelScope
import com.example.marvelcharacters.core.base.BaseViewModel
import com.example.marvelcharacters.core.base.update
import com.example.marvelcharacters.core.utils.onError
import com.example.marvelcharacters.core.utils.onResult
import com.example.marvelcharacters.core.utils.onSuccess
import com.example.marvelcharacters.domain.model.MarvelCharacter
import com.example.marvelcharacters.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : BaseViewModel() {

    private val _heroListFlow = MutableStateFlow<List<MarvelCharacter>?>(null)
    val heroListState = _heroListFlow.asStateFlow()

    fun getHeroList() {
        viewModelScope.launch {
            showLoading()

            characterRepository.getCharactersList()
                .onResult { hideLoading() }
                .onError { emitErrorUIMessage(it) }
                .onSuccess { _heroListFlow.update(it.data) }
        }
    }

}