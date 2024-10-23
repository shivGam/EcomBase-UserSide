package com.example.ecombase_userside.auth_fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecombase_userside.R
import com.example.ecombase_userside.Utils
import com.example.ecombase_userside.activity.UserMainActivity
import com.example.ecombase_userside.databinding.FragmentAuthOtpBinding
import com.example.ecombase_userside.models.Users
import com.example.ecombase_userside.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

class AuthOtpFragment : Fragment() {
    private val viewModel : AuthViewModel by viewModels()
    private lateinit var binding : FragmentAuthOtpBinding
    private lateinit var userNumber: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthOtpBinding.inflate(layoutInflater)
        getUserNumber()
        actionBackButton()
        requiredIncrement()
        sendOtp()
        actionLoginButton()
        return binding.root
    }

    private fun actionLoginButton() {
        binding.btnLogin.setOnClickListener {
            Utils.showDialog(requireContext(),"Verifying")
            val editTexts = arrayOf(binding.otp1, binding.otp2, binding.otp3, binding.otp4, binding.otp5, binding.otp6)
            val otpSend = editTexts.joinToString (""){it.text.toString()}
            if(otpSend.length < editTexts.size) {
                Utils.showToast(requireContext(), "Enter correct Otp")
                Utils.hideDialog()
            }
            else{
                editTexts.forEach { it.clearFocus();it.text?.clear() }
                verifyOtp(otpSend)
            }
        }
    }

    private fun verifyOtp(otpSend: String) {
        viewModel.signInWithPhoneAuthCredential(otpSend,userNumber)
        lifecycleScope.launch {
            viewModel.isSuccessful.collect(){
                Log.d("Tag: For false","$it")
                if(it){
                    Utils.hideDialog()
                    Utils.showToast(requireContext(),"Logged in Successfully")
                    startActivity(Intent(requireActivity(),UserMainActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
    }

    private fun sendOtp() {
        Utils.showDialog(requireContext(), "Getting OTP...")
        viewModel.apply {
            sendOtp(userNumber,requireActivity())
            lifecycleScope.launch {
                otpSent.collect(){ otpSent->
                    if(otpSent){
                        Utils.hideDialog()
                        Utils.showToast(requireContext(),"OTP sent")
                    }
                }
            }
        }
    }

    private fun actionBackButton() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_authOtpFragment_to_authFragment)
        }
    }

    private fun requiredIncrement() {
        val editTexts = arrayOf(binding.otp1, binding.otp2, binding.otp3, binding.otp4, binding.otp5, binding.otp6)

        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // No implementation needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // No implementation needed
                }

                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1) {
                        if (i < editTexts.size - 1) {
                            editTexts[i + 1].requestFocus()
                        }
                    } else if (s?.length == 0) {
                        if (i > 0) {
                            editTexts[i - 1].requestFocus()
                        }
                    }
                }
            })
        }
    }




    private fun getUserNumber() {
        val bundle = arguments
        userNumber = bundle?.getString("number").toString()
        binding.tvUserPhoneNumber.text = userNumber
    }

}