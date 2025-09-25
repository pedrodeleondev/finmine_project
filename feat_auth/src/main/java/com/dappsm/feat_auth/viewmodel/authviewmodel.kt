package com.dappsm.feat_auth.viewmodel

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class authviewmodel: ViewModel() {
    private val auth: FirebaseAuth= FirebaseAuth.getInstance()
    private val _authState= MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init{
        checkAuthStatus()
    }

    fun checkAuthStatus(){
        if (auth.currentUser==null){
            _authState.value=AuthState.Unauthenticated
        }else{
            _authState.value= AuthState.Authenticated
        }
    }

    fun login(email:String,password:String){
        if(email.isEmpty() || password.isEmpty()){
            _authState.value= AuthState.Error("El correo o contraseña no pueden estar vacíos")
            return
        }

        _authState.value= AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {task->
                if(task.isSuccessful){
                    _authState.value= AuthState.Authenticated
                }else{
                    _authState.value= AuthState.Error(task.exception?.message?:"Algo salió mal")

                }
            }
    }

    fun signup(email:String,password:String){
        if(email.isEmpty() || password.isEmpty()){
            _authState.value= AuthState.Error("El correo o contraseña no pueden estar vacíos")
            return
        }

        _authState.value= AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {task->
                if(task.isSuccessful){
                    _authState.value= AuthState.Authenticated
                }else{
                    _authState.value= AuthState.Error(task.exception?.message?:"Algo salió mal")

                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value= AuthState.Unauthenticated
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        _authState.value = AuthState.Loading
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Error al iniciar con Google")
                }
            }
    }

}
sealed class AuthState{
    object Authenticated:AuthState()
    object Unauthenticated:AuthState()
    object Loading:AuthState()
    data class Error(val message: String): AuthState()


}