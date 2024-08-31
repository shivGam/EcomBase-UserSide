package com.example.ecombase_userside.auth_fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.ecombase_userside.R
import com.example.ecombase_userside.Utils
import com.example.ecombase_userside.databinding.FragmentAuthBinding

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        getUserNumber()
        actionOnCont()
        return binding.root
    }

    private fun actionOnCont() {
        binding.btnContinue.setOnClickListener{
            val number = binding.etUserNumber.text.toString()
            if(number.isEmpty() || number.length!=10)
                Utils.showToast(requireContext(), "Please Enter a Valid Number")
            else{
                val bundle = Bundle()
                bundle.putString("number",number)
                findNavController().navigate(R.id.action_authFragment_to_authOtpFragment,bundle)
            }
        }
    }

    private fun getUserNumber() {
        binding.etUserNumber.addTextChangedListener( object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val len = s?.length
                if(len==10)
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),
                        R.color.success
                    ))
                else
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),
                        R.color.text_muted
                    ))
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }

        })
    }

}