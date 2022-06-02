package ru.magomedcoder.askue.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.magomedcoder.askue.R
import ru.magomedcoder.askue.databinding.FragmentAuthBinding
import ru.magomedcoder.askue.ui.base.BaseFragment

@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.authState.collect { state ->
                when (state) {
                    is AuthState.Success -> {
                        findNavController().navigate(R.id.action_authFragment_to_mainFragment)
                        Toast
                            .makeText(activity, R.string.welcome, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is AuthState.Failure -> {
                        Toast
                            .makeText(activity, R.string.authorization_failed, Toast.LENGTH_SHORT)
                            .show()
                    }
                    is AuthState.Logged -> {
                        if (state.logged) {
                            findNavController().navigate(R.id.action_authFragment_to_mainFragment)
                        }
                    }
                    else -> {
                        Toast
                            .makeText(activity, R.string.please_authorization, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.login(
                    username = binding.etUsername.text.toString(),
                    password = binding.etPassword.text.toString()
                )
            }
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAuthBinding.inflate(inflater, container, false)

}