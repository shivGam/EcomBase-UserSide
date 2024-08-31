package com.example.ecombase_userside.viewmodels

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.ecombase_userside.MainActivity
import com.example.ecombase_userside.Utils
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.internal.Util
import java.util.concurrent.TimeUnit

class AuthViewModel: ViewModel(){

    private val _verificationId = MutableStateFlow<String?>(null)
    private val _otpSent = MutableStateFlow<Boolean>(false)
    val otpSent = _otpSent

    private val _isSuccessful = MutableStateFlow<Boolean>(false)
    val isSuccessful = _isSuccessful
    fun sendOtp (userNumber : String,activity: Activity) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(e: FirebaseException) {

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                _verificationId.value = verificationId
                otpSent.value = true
            }
        }
        val options = PhoneAuthOptions.newBuilder(Utils.getFirebaseInstance())
            .setPhoneNumber("+91$userNumber") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    fun signInWithPhoneAuthCredential(otp : String , userNumber: String) {
        val credential = PhoneAuthProvider.getCredential(_verificationId.value.toString(),otp)
        Utils.getFirebaseInstance().signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    isSuccessful.value = true
                }else{
                    isSuccessful.value = false
                }
            }
    }
}