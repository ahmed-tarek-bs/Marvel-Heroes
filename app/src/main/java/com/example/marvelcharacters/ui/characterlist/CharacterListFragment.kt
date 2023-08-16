package com.example.marvelcharacters.ui.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.marvelcharacters.core.base.BaseFragment
import com.example.marvelcharacters.databinding.FragmentCharacterListBinding
import com.example.marvelcharacters.domain.model.MarvelCharacter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : BaseFragment<FragmentCharacterListBinding>() {

    private val charactersAdapter: CharactersAdapter by lazy {
        CharactersAdapter(onCharacterClicked = this::onCharacterClick)
    }

    private val vmHeroList by viewModels<CharacterListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = getView(FragmentCharacterListBinding.inflate(layoutInflater, container, false))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmHeroList.getHeroList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        observeState()
    }

    private fun setRecyclerView() {
        binding.recyclerCharacters.apply {
            adapter = charactersAdapter
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    vmHeroList.heroListState.collect {
                        charactersAdapter.submitList(it)
                    }
                }

            }
        }
    }

    private fun onCharacterClick(character: MarvelCharacter) {
        Toast.makeText(requireContext(), character.name, Toast.LENGTH_SHORT).show()
    }

}