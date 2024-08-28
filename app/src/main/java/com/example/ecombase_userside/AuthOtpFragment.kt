package com.example.ecombase_userside

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ecombase_userside.databinding.FragmentAuthOtpBinding

class AuthOtpFragment : Fragment() {
    private lateinit var binding : FragmentAuthOtpBinding
    private lateinit var userName : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthOtpBinding.inflate(layoutInflater)
        getUserNumber()
        actionBackButton()
        requiredIncrement()
        return binding.root
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
        userName = bundle?.getString("number").toString()
        binding.tvUserPhoneNumber.text = userName
    }

}