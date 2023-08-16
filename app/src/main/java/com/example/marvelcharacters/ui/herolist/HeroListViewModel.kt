package com.example.marvelcharacters.ui.herolist

import androidx.lifecycle.viewModelScope
import com.example.marvelcharacters.core.base.BaseViewModel
import com.example.marvelcharacters.core.base.update
import com.example.marvelcharacters.core.utils.onError
import com.example.marvelcharacters.core.utils.onResult
import com.example.marvelcharacters.core.utils.onSuccess
import com.example.marvelcharacters.domain.model.MarvelHero
import com.example.marvelcharacters.domain.repository.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val heroRepository: HeroRepository
) : BaseViewModel() {

    private val _heroListFlow = MutableStateFlow<List<MarvelHero>?>(null)
    val heroListState = _heroListFlow.asStateFlow()

    fun getHeroList() {
        viewModelScope.launch {
            showLoading()

            heroRepository.getHeroesList()
                .onResult { hideLoading() }
                .onError {
                    emitErrorUIMessage(it)
                    println(it.toString())
                }
                .onSuccess { _heroListFlow.update(it) }
        }
    }

}