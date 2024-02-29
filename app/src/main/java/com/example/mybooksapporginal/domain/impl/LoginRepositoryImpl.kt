package com.example.mybooksapporginal.domain.impl

import com.example.mybooksapporginal.data.LoginUserFirebaseData
import com.example.mybooksapporginal.data.local.pref.MyShared
import com.example.mybooksapporginal.domain.LoginRepository
import com.example.mybooksapporginal.util.myLog
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val myShared: MyShared
) : LoginRepository {
    private val auth = Firebase.auth
    private val fireStore = Firebase.firestore
    override fun loginEmailInPassword(email: String, password: String): Flow<Boolean> =
        callbackFlow {

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { signInTask ->
                    if (signInTask.isSuccessful) {
                        // Kirish muvaffaqiyatli amalga oshirildi
                        val currentUser = auth.currentUser
                        //currentUser?.uid
                        myShared.setHasIntroPage(1)
                        currentUser?.let {
                            myShared.setUid(currentUser.uid)
                        }
                        trySend(true)
                    } else {
                        trySend(false)
                    }
                }
            awaitClose()
        }



override fun createUserEmailInPassword(
    email: String,
    password: String
): Flow<Result<String>> = callbackFlow {

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.addOnCompleteListener {
                    val user = auth.currentUser
                    user.let {
                        myShared.setUid(user!!.uid)
                        myShared.setHasIntroPage(1)
                        trySend(Result.success(it!!.uid))
                    }
                }
            }
            task.addOnFailureListener {
                trySend(Result.failure(it))
            }
        }
    awaitClose()
}

override fun addUserFirebase(data: LoginUserFirebaseData): Flow<Unit> = callbackFlow {

    data.uid.myLog()

    fireStore.collection("users")
        .document()
        .set(data)
        .addOnSuccessListener {
                "set User sucses ".myLog()

            //Log.d("PPP", it.toString())
        }
        .addOnFailureListener {
                "set User exeption ${it.message} ".myLog()

        }
    awaitClose()
}


}

