package com.example.ecombase_userside.auth_fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecombase_userside.R
import com.example.ecombase_userside.activity.UserMainActivity
import com.example.ecombase_userside.databinding.FragmentSplashBinding
import com.example.ecombase_userside.viewmodels.AuthViewModel
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                authViewModel.isCurrentUser.collect{
                    if(it){
                        startActivity(Intent(requireActivity(),UserMainActivity::class.java))
                        requireActivity().finish()
                    }
                    else{
                        findNavController().navigate(R.id.action_splashFragment_to_authFragment)
                    }
                }
            }
        },3000)
        return binding.root
    }
}