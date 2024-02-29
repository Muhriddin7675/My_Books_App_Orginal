package com.example.mybooksapporginal.domain.impl

import com.example.mybooksapporginal.data.BookData
import com.example.mybooksapporginal.data.CategoryData
import com.example.mybooksapporginal.data.local.pref.MyShared
import com.example.mybooksapporginal.domain.AppRepository
import com.example.mybooksapporginal.util.myLog
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val myShared: MyShared,
) : AppRepository {

    private val fireStore = Firebase.firestore
    override fun getAllLibraryBooks(): Flow<Result<List<CategoryData>>> = callbackFlow {
        fireStore.collection("my_book")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val map = HashMap<String, ArrayList<BookData>>()
                val list = ArrayList<CategoryData>()

                querySnapshot.forEach {
                    val category = it.data["category"].toString()
                    if (!map.containsKey(category)) {
                        map[category] = arrayListOf()
                    }
                    val data = BookData(
                        docId = it.id,
                        bookAuthor = it.data["bookAuthor"].toString(),
                        bookDescription = it.data["bookDescription"].toString(),
                        bookImage = it.data["bookImage"].toString(),
                        bookName = it.data["bookName"].toString(),
                        bookPath = it.data["bookPath"].toString(),
                        category = it.data["category"].toString(),
                        categoryName = it.data["categoryName"].toString(),
                        size = it.data["size"].toString().toInt()
                    )
                    map[category]!!.add(data)
                }
                map.keys.forEach {
                    val ls = map[it]
                    ls!!.size.toString().myLog()
                    list.add(CategoryData(it, ls!!))
                }
                trySend(Result.success(list))
                close()
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
                close()
            }
        awaitClose()
    }

}