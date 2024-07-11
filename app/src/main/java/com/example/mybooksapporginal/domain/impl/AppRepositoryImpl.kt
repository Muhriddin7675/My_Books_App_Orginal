package com.example.mybooksapporginal.domain.impl

import com.example.mybooksapporginal.data.BookData
import com.example.mybooksapporginal.data.CategoryData
import com.example.mybooksapporginal.data.enamdata.UploadBookData
import com.example.mybooksapporginal.data.local.pref.MyShared
import com.example.mybooksapporginal.data.local.room.dao.BooksDao
import com.example.mybooksapporginal.data.local.room.entity.BookEntity
import com.example.mybooksapporginal.data.model.Mapper.toBookData
import com.example.mybooksapporginal.domain.AppRepository
import com.example.mybooksapporginal.util.myLog
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val booksDao: BooksDao
) : AppRepository {

    private val fireStore = Firebase.firestore
    private val storage = Firebase.storage
    private var bookDownloadTask: FileDownloadTask? = null

    private val allContactFlow = MutableSharedFlow<Result<List<CategoryData>>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    private fun requestAll() {
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
                    list.add(CategoryData(it, ls!!))
                }
                allContactFlow.tryEmit(Result.success(list))
            }
            .addOnFailureListener {
                allContactFlow.tryEmit(Result.failure(it))
            }
    }


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
                    list.add(CategoryData(it, ls!!))
                }
                trySend(Result.success(list))
                close()
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
                close()
            }
        awaitClose {
            channel.close()
        }
    }

    override fun requestAllBook(): Flow<Result<List<CategoryData>>> {
        requestAll()
        return allContactFlow
    }

    override fun hasBookFromLocal(docId: String): Flow<Result<Boolean>> = callbackFlow {
        val bool = booksDao.isHasBook(docId)
        trySend(Result.success(bool))
        awaitClose()
    }.flowOn(Dispatchers.IO)

    override fun getAllLocalBook(): Flow<List<BookData>> = callbackFlow<List<BookData>> {
        val ls = booksDao.getAllBooksFromLocal()
        val list = ArrayList<BookData>()
        if (ls.isNotEmpty()) {
            ls.forEach {
                list.add(it.toBookData())
            }
            trySend(list)
        } else {
            val emptyList = ArrayList<BookData>()
            trySend(emptyList)
        }
        awaitClose {
            channel.close()
        }
    }.flowOn(Dispatchers.IO)

    override fun downloadBookPdf(data: BookData): Flow<UploadBookData> = callbackFlow {
        val book = File.createTempFile("Pdf", ".pdf")
        data.bookPath.myLog()
        bookDownloadTask = storage
            .getReferenceFromUrl(data.bookPath)
            .getFile(book)

        bookDownloadTask!!.addOnSuccessListener {
            booksDao.insertBooks(
                BookEntity(
                    0,
                    data.docId,
                    data.bookName,
                    data.bookAuthor,
                    data.bookDescription,
                    data.bookImage,
                    book.absolutePath,
                    data.size,
                    data.category,
                    data.categoryName
                )
            )
            trySend(UploadBookData.SUCCESS(File(book.absolutePath)))
        }
            .addOnFailureListener {
                trySend(UploadBookData.Error(it.message.toString()))
            }
            .addOnProgressListener {
                trySend(UploadBookData.Progress(it.bytesTransferred, it.totalByteCount))
            }
            .addOnPausedListener {
                trySend(UploadBookData.PAUSE)
            }
            .addOnCanceledListener {
                trySend(UploadBookData.CANCEL)
            }
        awaitClose()
    }.flowOn(Dispatchers.IO)

    override fun getDownloadedBook(data: BookData): Flow<File> = callbackFlow<File> {
        val bookLocalPath = booksDao.getBooksByDocID(data.docId).bookPath
        val file = File(bookLocalPath)
        trySend(file)
        awaitClose()
    }.flowOn(Dispatchers.IO)


    override fun pauseBookUploading() {
        bookDownloadTask!!.pause()
    }

    override fun cancelBookUploading() {
        bookDownloadTask!!.cancel()
    }

    override fun resumeBookUploading() {
        bookDownloadTask!!.resume()
    }
}