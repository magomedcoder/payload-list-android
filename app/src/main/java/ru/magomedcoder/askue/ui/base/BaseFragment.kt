package ru.magomedcoder.askue.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<binding : ViewBinding> : Fragment() {

    private var _binding: binding? = null
    protected val binding get() = _binding!!
    protected abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = initBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}