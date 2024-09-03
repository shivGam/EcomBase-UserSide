package com.example.ecombase_userside

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.ecombase_userside.databinding.LoadingDialogBinding
import com.google.firebase.auth.FirebaseAuth

object Utils {
    private var dialog : AlertDialog ?= null
    fun showDialog(context: Context,message: String){
        val progress = LoadingDialogBinding.inflate(LayoutInflater.from(context))
        progress.tvLoading.text = message
        dialog = AlertDialog.Builder(context).setView(progress.root).setCancelable(false).create()
        dialog!!.show()
    }
    fun hideDialog(){
        dialog?.cancel()
    }
    fun showToast(context :Context , text : String){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
    }

    private var firebaseAuthInstance : FirebaseAuth ?= null
    fun getFirebaseInstance () : FirebaseAuth {
        if(firebaseAuthInstance == null)
            firebaseAuthInstance = FirebaseAuth.getInstance()
        return firebaseAuthInstance!!
    }

    fun getUserId (): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }
}