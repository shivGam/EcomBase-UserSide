package com.example.ecombase_userside.viewmodels

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.ecombase_userside.Utils
import com.example.ecombase_userside.models.Users
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

class AuthViewModel: ViewModel(){

    private val _verificationId = MutableStateFlow<String?>(null)
    private val _otpSent = MutableStateFlow<Boolean>(false)
    val otpSent = _otpSent

    private val _isSuccessful = MutableStateFlow<Boolean>(false)
    val isSuccessful = _isSuccessful

//    private val _isCurrentUser = MutableStateFlow<Boolean>(false)
//    val isCurrentUser = _isCurrentUser

//    init {
//        Utils.getFirebaseInstance().currentUser?.uid.let {
//            isCurrentUser.value = true
//        }
//    }
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
    fun signInWithPhoneAuthCredential(otp: String, userNumber: String) {
        val credential = PhoneAuthProvider.getCredential(_verificationId.value.toString(),otp)
        Utils.getFirebaseInstance().signInWithCredential(credential)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val user = Users(uid = Utils.getUserId(),userNumber = userNumber, userAddress = null)
                    FirebaseDatabase.getInstance().getReference("AllUsers").child("Users").child(user.uid!!).setValue(user)
                    isSuccessful.value = true
                }else{
                    isSuccessful.value = false
                }
            }
    }
}