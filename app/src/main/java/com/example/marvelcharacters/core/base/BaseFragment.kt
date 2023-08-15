package com.example.marvelcharacters.core.base

import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    protected val binding: T get() = _binding!!

    protected val navController by lazy { findNavController() }

    protected fun getView(viewBinding: T): View {
        _binding = viewBinding
        return binding.root
    }

    protected fun showSnackMessage(@StringRes stringId: Int, duration: Int = Toast.LENGTH_LONG) =
        showSnackMessage(getString(stringId), duration)

    protected fun showSnackMessage(message: String, duration: Int = Toast.LENGTH_LONG) =
        Toast.makeText(requireContext(), message, duration).show()
}