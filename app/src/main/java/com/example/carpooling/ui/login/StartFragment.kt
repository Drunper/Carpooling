package com.example.carpooling.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.carpooling.R
import com.example.carpooling.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* In teoria qui metto un controllo per verificare se l'utente è già connesso,
        *  ovvero ho delle credenziali memorizzate. Quindi devo semplicemente settare
        *  l'api service e andare nella schermata di ricerca. Altrimenti vado in quella
        *  di login. Qui, volendo, ripeto, VOLENDO, potrei mettere un pulsante per la
        *  registrazione, ma se non sbaglio non è richiesto.
        */

        val navController = findNavController()

        binding.btnStartLogin.setOnClickListener {
            val action = StartFragmentDirections.goToLogin()
            navController.navigate(action)
        }
    }
}