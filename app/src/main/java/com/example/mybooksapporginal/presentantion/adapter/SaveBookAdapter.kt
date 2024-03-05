package com.example.mybooksapporginal.presentantion.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.data.BookData
import com.example.mybooksapporginal.databinding.ItemBookByCategoryBinding

class SaveBookAdapter: ListAdapter<BookData, SaveBookAdapter.MyBooksViewHolder>(ItemDiffUtil) {
    private var itemListener:((BookData)->Unit)? = null

    object ItemDiffUtil:DiffUtil.ItemCallback<BookData>(){
        override fun areItemsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return oldItem.docId == newItem.docId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: BookData, newItem: BookData): Boolean {
            return  oldItem == newItem
        }
    }

    inner class MyBooksViewHolder(private val binding:ItemBookByCategoryBinding):ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                itemListener?.invoke(getItem(adapterPosition))
            }
        }
        fun bind() {
            getItem(adapterPosition).apply {
                if(bookImage != "null"){
                    Glide.with(binding.root)
                        .load(bookImage)
                        .into(binding.imgBook)
                }else{
                    binding.imgBook.setImageResource(R.drawable.book_image)
                }
                binding.bookName.text = bookName
                binding.bookAuthor.text = bookAuthor
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBooksViewHolder {
        return MyBooksViewHolder(
            ItemBookByCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyBooksViewHolder, position: Int) {
        holder.bind()
    }

    fun setItemListener(block:(BookData)->Unit){
        itemListener = block
    }
}