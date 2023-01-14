package com.example.carpooling.ui.login

import androidx.lifecycle.Observer
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.example.carpooling.databinding.FragmentLoginBinding
import com.example.carpooling.data.Result
import com.example.carpooling.R
import com.example.carpooling.viewmodels.UserViewModel
import com.example.carpooling.viewmodels.ViewModelFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory()
    }
    private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle.set(LOGIN_SUCCESSFUL, false)*/

        val emailEditText = binding.email
        val passwordEditText = binding.password
        val loginButton = binding.login
        val rememberMeCheckbox = binding.remember
        userViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.emailError?.let {
                    emailEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                userViewModel.loginDataChanged(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        emailEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    rememberMeCheckbox.isChecked
                )
            }
            false
        }

        loginButton.setOnClickListener {
            login(
                emailEditText.text.toString(),
                passwordEditText.text.toString(),
                rememberMeCheckbox.isChecked
            )
        }
    }

    private fun login(email: String, password: String, rememberMe: Boolean) {
        userViewModel.login(email, password, rememberMe).observe(viewLifecycleOwner
        ) { result ->
            /*if (result.status == Result.Status.SUCCESS) {
                result.data?.let { userViewModel.updateUser(it) }
                savedStateHandle.set(LOGIN_SUCCESSFUL, true)
            } else if (result.status == Result.Status.ERROR) {
                showLoginFailed(R.string.login_failed)
            }
            findNavController().popBackStack()*/
            if (result.status == Result.Status.SUCCESS) {
                result.data?.let { userViewModel.updateUser(it) }
                val action = LoginFragmentDirections.goToSearch()
                findNavController().navigate(action)
            } else if (result.status == Result.Status.ERROR) {
                showLoginFailed(R.string.login_failed)
            }
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }
}