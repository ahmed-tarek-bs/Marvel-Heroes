package com.example.marvelcharacters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marvelcharacters.core.base.BaseFragment
import com.example.marvelcharacters.databinding.FragmentHeroListBinding

class HeroListFragment : BaseFragment<FragmentHeroListBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = getView(FragmentHeroListBinding.inflate(layoutInflater, container, false))

}