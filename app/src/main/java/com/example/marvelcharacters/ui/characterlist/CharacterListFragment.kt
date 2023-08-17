package com.example.marvelcharacters.ui.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.marvelcharacters.R
import com.example.marvelcharacters.core.base.BaseFragment
import com.example.marvelcharacters.core.utils.GridItemSpacingDecorator
import com.example.marvelcharacters.core.utils.toDp
import com.example.marvelcharacters.databinding.FragmentCharacterListBinding
import com.example.marvelcharacters.domain.model.MarvelCharacter
import com.example.marvelcharacters.ui.characterdetails.CharacterDetailsFragment
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
            val spaceDecorator = GridItemSpacingDecorator(
                horizontalSpacing = 16.toDp(),
                bottomSpacing = 16.toDp(),
                isPaginatedList = true
            )
            addItemDecoration(spaceDecorator)
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
        val bundle = bundleOf(CharacterDetailsFragment.CHARACTER_KEY to character)
        navController.navigate(R.id.action_characterList_to_details, bundle)
    }

}