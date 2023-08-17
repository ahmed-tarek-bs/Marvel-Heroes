package com.example.marvelcharacters.ui.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.marvelcharacters.core.base.BaseFragment
import com.example.marvelcharacters.core.utils.parcelable
import com.example.marvelcharacters.databinding.FragmentCharacterDetailsBinding
import com.example.marvelcharacters.domain.model.MarvelCharacter
import com.example.marvelcharacters.ui.characterdetails.appearancescomponent.CharacterSectionLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsFragment : BaseFragment<FragmentCharacterDetailsBinding>() {

    companion object {
        const val CHARACTER_KEY = "MARVEL_CHARACTER"
    }

    private val vmHeroList by viewModels<CharacterDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = getView(FragmentCharacterDetailsBinding.inflate(layoutInflater, container, false))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.parcelable<MarvelCharacter>(CHARACTER_KEY)?.let {
            vmHeroList.setCharacter(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    vmHeroList.characterState.collect {
                        if (it != null) {
                            setCharacterDetails(it)
                        }
                    }
                }

            }
        }
    }

    private fun setCharacterDetails(character: MarvelCharacter) {
        character.let {
            binding.apply {
                imgCharacter.load(it.imgUrl.squareUrl)
                name.text = it.name
                description.text = it.description

                setAppearanceSection("Comics", character.comics, comicsSection)
                setAppearanceSection("Stories", character.stories, storiesSection)
                setAppearanceSection("Events", character.events, eventsSection)
                setAppearanceSection("Series", character.series, seriesSection)
            }
        }
    }

    private fun setAppearanceSection(
        title: String,
        appearancesList: List<String>?,
        sectionLayout: CharacterSectionLayout
    ) {
        sectionLayout.apply {
            if (!appearancesList.isNullOrEmpty()) {
                setTitle("$title (${appearancesList.size})")
                submitList(appearancesList)
            } else {
                visibility = View.GONE
            }
        }
    }

}