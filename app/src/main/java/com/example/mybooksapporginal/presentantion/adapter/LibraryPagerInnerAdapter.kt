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
import com.example.mybooksapporginal.databinding.ItemLibraryBooksBinding


class LibraryPagerInnerAdapter : ListAdapter<BookData, LibraryPagerInnerAdapter.LibraryInnerViewHolder>(LibraryDiffUtil) {
     var time = System.currentTimeMillis()
        lateinit var clickBookItem:(BookData)->Unit

    object LibraryDiffUtil : DiffUtil.ItemCallback<BookData>() {
        override fun areItemsTheSame(oldItem: BookData, newItem: BookData): Boolean =
            oldItem.docId == newItem.docId

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: BookData, newItem: BookData): Boolean =
            oldItem == newItem
    }

    inner class LibraryInnerViewHolder(private val binding: ItemLibraryBooksBinding) :
        ViewHolder(binding.root) {
            init {

                    binding.item.setOnClickListener{
                        if(System.currentTimeMillis() - time > 500){
                        clickBookItem.invoke(getItem(adapterPosition))
                        }
                        time = System.currentTimeMillis()
                    }

            }
        fun bind(data: BookData) {
            binding.bookname.text = data.bookName
            binding.bookAuthor.text = data.bookAuthor
            if(data.bookImage != "null"){
                Glide.with(binding.root)
                    .load(data.bookImage)
                    .into(binding.imageItem)
            }else{
                binding.imageItem.setImageResource(R.drawable.book_image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryInnerViewHolder =
        LibraryInnerViewHolder(
            ItemLibraryBooksBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LibraryInnerViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun  setOnClickBookItem(block:(BookData) ->Unit){
        clickBookItem = block
    }

}