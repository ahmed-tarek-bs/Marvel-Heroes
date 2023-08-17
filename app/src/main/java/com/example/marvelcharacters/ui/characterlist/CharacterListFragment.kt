package com.example.marvelcharacters.ui.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelcharacters.R
import com.example.marvelcharacters.core.base.BaseFragment
import com.example.marvelcharacters.core.utils.GridItemSpacingDecorator
import com.example.marvelcharacters.core.utils.shouldPaginate
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
        vmHeroList.getNextPage()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        observeState()
    }

    private fun setRecyclerView() {
        binding.recyclerCharacters.apply {
            adapter = charactersAdapter
            val spaceDecorator = GridItemSpacingDecorator(
                horizontalSpacing = 16.toDp(),
                bottomSpacing = 16.toDp(),
                isPaginatedList = true
            )
            addItemDecoration(spaceDecorator)
            addOnScrollListener(this@CharacterListFragment.scrollListener)
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    vmHeroList.itemList.collect {
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

    //////////////////////////Pagination Listener/////////////////////////////

    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (recyclerView.shouldPaginate(isScrolling)) {
                vmHeroList.getNextPage()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

}